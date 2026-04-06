# Jenkins & SonarQube Setup Guide - JMenu_task

## Prerequisites

- **Java 21** installed
- **Maven 3.9.14+** installed
- **Jenkins** (local or remote)
- **SonarQube** server (v9.x or later)
- **Git** for version control

---

## Part 1: SonarQube Installation & Setup

### 1.1 Install SonarQube Community Edition

#### On Windows:
```powershell
# Download from https://www.sonarqube.org/downloads/
# Extract to C:\sonarqube

cd C:\sonarqube\bin\windows-x86-64
StartSonar.bat
```

SonarQube will start on `http://localhost:9000`

**Default credentials**: 
- Username: `admin`
- Password: `admin` (change on first login)

### 1.2 Create SonarQube Token

1. Login to SonarQube (`http://localhost:9000`)
2. Go to **Admin** → **Security** → **Users**
3. Click your user profile → **Security** tab
4. Generate new token (name it `jenkins-token`)
5. Copy and save the token securely

### 1.3 Configure Quality Gate (Optional but Recommended)

1. Go to **Quality Gates**
2. Select or create a quality gate
3. Set rules for:
   - Code coverage: ≥ 80%
   - Duplicated lines: ≤ 3%
   - Critical issues: 0
   - Security hotspots: 0

---

## Part 2: Jenkins Installation & Configuration

### 2.1 Install Jenkins

#### On Windows:
```powershell
# Download from https://www.jenkins.io/download/

# Run installer or as service
java -jar jenkins.war
```

Jenkins will start on `http://localhost:8080`

### 2.2 Configure Jenkins

1. **Unlock Jenkins** with the initial admin password (check logs/`secrets/initialAdminPassword`)
2. **Install Plugins**:
   - Go to **Manage Jenkins** → **Plugin Manager**
   - Install these plugins:
     - **Maven Integration Plugin**
     - **SonarQube Scanner for Jenkins**
     - **JUnit Plugin**
     - **Code Coverage API**
     - **Git Plugin** (if not already installed)

3. **Configure System**:
   - Go to **Manage Jenkins** → **Configure System**
   
   **Add Maven Installation**:
   - Scroll to "Maven"
   - Add Maven installation:
     - Name: `Maven 3.9.14`
     - MAVEN_HOME: `C:\Users\HP\.maven\maven-3.9.14`
   
   **Add JDK Installation**:
   - Scroll to "JDK"
   - Add JDK:
     - Name: `JDK 21`
     - JAVA_HOME: `C:\Program Files\Java\jdk-21`

4. **Configure SonarQube Server**:
   - Scroll to "SonarQube servers"
   - Check "Enable injection of SonarQube server configuration as build environment variables"
   - Add SonarQube server:
     - Name: `SonarQube`
     - Server URL: `http://localhost:9000`
     - Server authentication token: (paste the token from 1.2)
   - Save

### 2.3 Create Jenkins Pipeline Job

1. Go to Jenkins home → **New Item**
2. Enter job name: `JMenu_task`
3. Select **Pipeline**
4. Configure:
   - **Pipeline script from SCM**
   - **SCM**: Git
   - **Repository URL**: `https://github.com/your-username/JMenu_task.git`
   - **Credentials**: Add GitHub credentials (if private repo)
   - **Branch**: `*/main`
   - **Script Path**: `Jenkinsfile`
5. Save

### 2.4 Add SonarQube Token to Jenkins Credentials

1. Go to **Manage Jenkins** → **Manage Credentials**
2. Click **System** → **Global credentials**
3. **Add Credentials**:
   - Kind: **Secret text**
   - Secret: (paste SonarQube token from 1.2)
   - ID: `sonarqube-token`
4. Save

---

## Part 3: Running the Build with Jenkins

### 3.1 Trigger Manual Build

1. Go to `JMenu_task` job
2. Click **Build Now**
3. Monitor in **Build History** → **Console Output**

### 3.2 View Results

**Jenkins**: After build completes, view **Test Results** tab (if tests exist)

**SonarQube**: Go to `http://localhost:9000/dashboard?id=jmenu-task` to see:
- Code quality metrics
- Code coverage
- Security issues
- Technical debt

---

## Part 4: Advanced Configurations

### 4.1 Enable Code Coverage Reports

Add to parent `pom.xml` (already included in our setup):
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>
```

### 4.2 Configure Jenkins Pipeline with Email Notifications

Edit `Jenkinsfile`:
```groovy
post {
    success {
        emailext(
            subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Build completed successfully. Check SonarQube: http://localhost:9000",
            to: "your-email@example.com"
        )
    }
    failure {
        emailext(
            subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Build failed. Check Jenkins console for details.",
            to: "your-email@example.com"
        )
    }
}
```

### 4.3 GitHub Integration (Webhook)

To trigger Jenkins builds on GitHub push:

1. In Jenkins job → **Configure**
2. **Build Triggers** → Check "GitHub hook trigger for GITScm polling"
3. Push to GitHub:
   - Go to repo → **Settings** → **Webhooks**
   - Click **Add webhook**
   - Payload URL: `http://your-jenkins.com:8080/github-webhook/`
   - Content type: `application/json`
   - Events: `Push events`

### 4.4 Scheduled Builds

To build daily at 2 AM:

In Jenkins job → **Configure**:
```
Build Triggers → Build periodically
Schedule: 0 2 * * *
```

---

## Part 5: Troubleshooting

| Issue | Solution |
| ----- | --------- |
| **Maven not found in Jenkins** | Verify Maven installation path in Jenkins config matches actual path |
| **SonarQube analysis fails** | Check SonarQube token is correct and not expired |
| **Tests not running** | Ensure test classes match naming pattern `*Test.java` or `Test*.java` |
| **Low code coverage** | Add more unit tests to improve coverage metrics |
| **Port 8080 in use** | Change Jenkins port: `java -jar jenkins.war --httpPort=8888` |
| **Port 9000 in use** | Change SonarQube port in `sonar.properties`: `sonar.web.port=9001` |

---

## Part 6: Build & Test Command Reference

### Manual Maven builds:
```bash
# Compile only
mvn clean compile

# Run tests
mvn clean test

# Build with all checks
mvn clean verify

# Generate SonarQube report
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=jmenu-task \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<YOUR_TOKEN>

# Skip tests (development)
mvn clean compile -DskipTests
```

---

## Summary

Your pipeline is now configured to:
1. ✅ **Build** - Compile all modules under Java 21
2. ✅ **Test** - Run JUnit tests and generate coverage
3. ✅ **Analyze** - Run SonarQube analysis
4. ✅ **Report** - Display quality metrics and test results

Files created:
- `Jenkinsfile` - Jenkins pipeline configuration
- `sonar-project.properties` - SonarQube configuration
- `.github/workflows/build.yml` - GitHub Actions alternative
- Updated `pom.xml` with Maven plugins

Next: Push these files to GitHub and trigger your first build! 🚀
