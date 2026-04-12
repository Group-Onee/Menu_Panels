package MainClassPackage;

import JMenusPackage.JMenus_task;
import JPanelsPackage.CreatingJPanels;
import org.junit.jupiter.api.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class WorkingWithMenusAndJPanelsTest {

    private WorkingWithMenusAndJPanels workingApp;
    private JFrame frame;
    private CreatingJPanels panels;
    private JMenus_task menus;

    @BeforeEach
    void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            workingApp = new WorkingWithMenusAndJPanels();
            frame = workingApp.Border_Frame;
            panels = new CreatingJPanels();
            menus = new JMenus_task();
        });
    }

    @AfterEach
    void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            if (frame != null) {
                frame.dispose();
            }
        });
    }

    @Test
    void testFrameIsCreated() {
        assertNotNull(frame);
    }

    @Test
    void testFrameTitleIsCorrect() {
        assertEquals("Working with Menus & JPanels", frame.getTitle());
    }

    @Test
    void testFrameDefaultCloseOperation() {
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }

    @Test
    void testMainPanelExists() {
        assertNotNull(workingApp.mainPanel);
    }

    @Test
    void testMainPanelUsesCardLayout() {
        assertTrue(workingApp.mainPanel.getLayout() instanceof CardLayout);
    }

    @Test
    void testMainPanelHasThreePanels() {
        assertEquals(3, workingApp.mainPanel.getComponentCount());
    }

    @Test
    void testMenuBarExists() {
        assertNotNull(frame.getJMenuBar());
    }

    @Test
    void testMenuBarHasOneMenu() {
        assertEquals(1, frame.getJMenuBar().getMenuCount());
    }

    @Test
    void testMenuTitle() {
        JMenu menu = frame.getJMenuBar().getMenu(0);
        assertEquals("Menu", menu.getText());
    }

    @Test
    void testMenuHasFourItems() {
        JMenu menu = frame.getJMenuBar().getMenu(0);
        assertEquals(4, menu.getItemCount());
    }

    @Test
    void testMenuItemTexts() {
        JMenu menu = frame.getJMenuBar().getMenu(0);

        assertEquals("Dashboard", menu.getItem(0).getText());
        assertEquals("Actions", menu.getItem(1).getText());
        assertEquals("Status", menu.getItem(2).getText());
        assertEquals("Exit", menu.getItem(3).getText());
    }

    @Test
    void testCreateJPanelOne() {
        JPanel panel = panels.Create_JPanel_One();

        assertNotNull(panel);
        assertTrue(panel.getBorder() instanceof TitledBorder);
        assertEquals("Panel One", ((TitledBorder) panel.getBorder()).getTitle());
    }

    @Test
    void testCreateJPanelTwo() {
        JPanel panel = panels.Create_JPanel_Two();

        assertNotNull(panel);
        assertTrue(panel.getBorder() instanceof TitledBorder);
        assertEquals("Panel Two", ((TitledBorder) panel.getBorder()).getTitle());
    }

    @Test
    void testCreateJPanelThree() {
        JPanel panel = panels.Create_JPanel_Three();

        assertNotNull(panel);
        assertTrue(panel.getBorder() instanceof TitledBorder);
        assertEquals("Panel Three", ((TitledBorder) panel.getBorder()).getTitle());
    }

    @Test
    void testCreateJPanelFour() {
        JPanel panel = panels.Create_JPanel_Four();

        assertNotNull(panel);
        assertTrue(panel.getBorder() instanceof TitledBorder);
        assertEquals("Panel Four", ((TitledBorder) panel.getBorder()).getTitle());
    }

    @Test
    void testCreatingJMenuBarReturnsMenuBar() {
        JMenuBar menuBar = menus.CreatingJMenuBar(new CardLayout(), new JPanel());
        assertNotNull(menuBar);
    }

    @Test
    void testCreateJMenuReturnsCorrectMenu() {
        JMenu menu = menus.createJMenu(new CardLayout(), new JPanel());
        assertNotNull(menu);
        assertEquals("Menu", menu.getText());
    }

    @BeforeAll
    static void initializeTestSuite() {
        System.out.println("Starting All Tests...");
    }

    @AfterAll
    static void cleanupTestSuite() {
        System.out.println("All Tests Finished Successfully!");
    }
}