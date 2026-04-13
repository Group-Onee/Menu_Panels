# Jenkins + SonarQube Integration Setup Guide

## Prerequisites
- Java 21 installed
- Maven 3.9.14 installed
- Git installed
- Administrator access

---

## Part 1: Install and Configure SonarQube

### Step 1.1: Download SonarQube
```powershell
# Download from https://www.sonsource.com/products/sonarqube/downloads/
# Get the Community Edition (free)
# Extract to C:\sonarqube
```

### Step 1.2: Start SonarQube
```powershell
cd C:\sonarqube\bin\windows-x86-64
StartSonar.bat
```

### Step 1.3: Access SonarQube Web Interface
- Open browser: `http://localhost:9000`
- Default login: `admin` / `admin`
- Change password when prompted

### Step 1.4: Create SonarQube Token
1. Go to **Administration** → **Security** → **Users**
2. Click on your user profile
3. Go to **Security** tab
4. Generate new token (name: `jenkins-token`)
5. **Copy and save the token** - you'll need it later

---

## Part 2: Install and Configure Jenkins

### Step 2.1: Download Jenkins
```powershell
# Download from https://www.jenkins.io/download/
# Get the Windows installer
```

### Step 2.2: Install Jenkins
1. Run the installer
2. Choose installation directory (default is fine)
3. Select "Run service as LocalSystem"
4. Complete installation

### Step 2.3: Start Jenkins
Jenkins should start automatically as a Windows service. If not:
```powershell
# Start manually
java -jar jenkins.war --httpPort=8080
```

### Step 2.4: Unlock Jenkins
1. Open browser: `http://localhost:8080`
2. Get initial admin password from:
   ```
   C:\Program Files\Jenkins\secrets\initialAdminPassword
   ```
3. Paste password and continue

### Step 2.5: Install Jenkins Plugins
1. Select "Install suggested plugins" (recommended)
2. Or manually install these plugins:
   - **Maven Integration Plugin**
   - **SonarQube Scanner for Jenkins**
   - **JUnit Plugin**
   - **Git Plugin**
   - **Pipeline Plugin**

### Step 2.6: Configure Jenkins System Settings

#### Configure Maven
1. Go to **Manage Jenkins** → **Tools**
2. Scroll to **Maven installations**
3. Add Maven:
   - Name: `Maven 3.9.14`
   - MAVEN_HOME: `C:\Users\HP\.maven\maven-3.9.14`

#### Configure JDK
1. In the same **Tools** page
2. Scroll to **JDK installations**
3. Add JDK:
   - Name: `JDK 21`
   - JAVA_HOME: `C:\Program Files\Java\jdk-21`

#### Configure SonarQube Server
1. Go to **Manage Jenkins** → **Configure System**
2. Scroll to **SonarQube servers**
3. Check "Enable injection of SonarQube server configuration as build environment variables"
4. Add SonarQube server:
   - Name: `SonarQube`
   - Server URL: `http://localhost:9000`
   - Server authentication token: (paste the token from Step 1.4)

### Step 2.7: Create Jenkins Job

1. Go to Jenkins home → **New Item**
2. Enter job name: `JMenu_task`
3. Select **Pipeline**
4. Click **OK**

### Step 2.8: Configure Pipeline Job

#### General Settings
- Check "Discard old builds"
- Max # of builds to keep: 10

#### Build Triggers
- Check "Poll SCM"
- Schedule: `H/15 * * * *` (check every 15 minutes)

#### Pipeline Configuration
- Definition: **Pipeline script from SCM**
- SCM: **Git**
- Repository URL: `https://github.com/your-username/JMenu_task.git`
- Credentials: Add your GitHub credentials
- Branch: `*/main`
- Script Path: `Jenkinsfile`

#### Save the job

---

## Part 3: Configure SonarQube Project

### Step 3.1: Create Project in SonarQube
1. Login to SonarQube: `http://localhost:9000`
2. Click **Create new project**
3. Project key: `jmenu-task`
4. Display name: `JMenu Task Project`
5. Click **Set Up**

### Step 3.2: Choose Analysis Method
- Select **With Jenkins**
- Click **Continue**

### Step 3.3: Generate Token (Already Done)
- You already created the token in Step 1.4
- Copy it again if needed

---

## Part 4: Run Your First Build

### Step 4.1: Trigger Build
1. Go to your `JMenu_task` job in Jenkins
2. Click **Build Now**
3. Monitor the build in **Build History** → **Console Output**

### Step 4.2: Verify Results

#### Jenkins Results
- **Test Results**: Click on latest build → **Test Results** tab
- **Console Output**: Check for any errors

#### SonarQube Results
- Open: `http://localhost:9000/dashboard?id=jmenu-task`
- View:
  - Code quality metrics
  - Test coverage
  - Security hotspots
  - Technical debt

---

## Part 5: Troubleshooting

### Common Issues

#### 1. SonarQube Server Not Reachable
**Error**: `SonarQube server [] can not be reached`

**Solution**:
- Ensure SonarQube is running: `http://localhost:9000`
- Check Jenkins SonarQube configuration
- Verify token is correct

#### 2. Maven Build Fails
**Error**: `Failed to execute goal`

**Solution**:
- Check Java version: `java -version` should show Java 21
- Check Maven version: `mvn -version`
- Verify project structure

#### 3. Git Authentication Issues
**Error**: `Failed to connect to repository`

**Solution**:
- Add GitHub credentials in Jenkins
- Use SSH key or personal access token
- Check repository URL

#### 4. GUI Tests Hanging
**Error**: Build hangs during tests

**Solution**:
- Tests are configured for headless mode in `pom.xml`
- If still hanging, add more system properties

#### 5. Port Conflicts
**Error**: `Port 8080 already in use`

**Solution**:
- Change Jenkins port: `java -jar jenkins.war --httpPort=9090`
- Change SonarQube port in `sonar.properties`: `sonar.web.port=9001`

---

## Part 6: Advanced Configuration

### 6.1: Quality Gates
Configure quality gates in SonarQube:
1. Go to **Quality Gates**
2. Create or edit quality gate
3. Set conditions:
   - Coverage: ≥ 80%
   - Duplicated lines: ≤ 3%
   - Blocker issues: 0

### 6.2: Email Notifications
Add email notifications to Jenkins:
1. Install **Email Extension Plugin**
2. Configure SMTP in **Manage Jenkins** → **Configure System**
3. Add post-build actions in job configuration

### 6.3: Scheduled Builds
Set up nightly builds:
- Build Triggers → **Build periodically**
- Schedule: `H 2 * * * *` (2 AM daily)

### 6.4: Webhooks (GitHub Integration)
Trigger Jenkins on GitHub push:
1. In Jenkins job → **Build Triggers** → Check "GitHub hook trigger`
2. In GitHub repo → **Settings** → **Webhooks**
3. Add webhook: `http://your-jenkins:8080/github-webhook/`

---

## Part 7: Command Line Testing

### Test Maven Build Locally
```powershell
cd C:\Users\HP\Documents\GitHub\JMenu_task

# Basic build
mvn clean compile

# Run tests
mvn clean test

# Full build with coverage
mvn clean verify

# SonarQube analysis
mvn clean verify sonar:sonar `
  -Dsonar.projectKey=jmenu-task `
  -Dsonar.host.url=http://localhost:9000 `
  -Dsonar.login=YOUR_SONARQUBE_TOKEN
```

### Test Jenkins Pipeline Locally
```powershell
# Validate Jenkinsfile syntax
# (Requires Jenkins CLI or web interface)
```

---

## Summary

Your CI/CD pipeline is now configured to:
1. ✅ **Checkout** code from GitHub
2. ✅ **Build** with Maven and Java 21
3. ✅ **Test** with JUnit (headless mode)
4. ✅ **Analyze** code quality with SonarQube
5. ✅ **Report** results and metrics

**URLs to monitor:**
- Jenkins: `http://localhost:8080`
- SonarQube: `http://localhost:9000`
- Project Dashboard: `http://localhost:9000/dashboard?id=jmenu-task`

**Next Steps:**
1. Push your code to GitHub
2. Trigger a Jenkins build
3. Review results in both Jenkins and SonarQube
4. Set up quality gates and notifications

Happy building! 🚀