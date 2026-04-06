package MainClassPackage;

import JPanelsPackage.CreatingJPanels;
import JMenusPackage.JMenus_task;
import javax.swing.*;
import java.awt.*;

public class WorkingWithMenusAndJPanels {
    JFrame Border_Frame;
    JPanel mainPanel;
    CardLayout cardLayout;

    CreatingJPanels Project_Panels = new CreatingJPanels();
    JMenus_task Project_Menus = new JMenus_task();

    public WorkingWithMenusAndJPanels() {

        this.Working_With_Menus_And_JPanels();
    }

    public JFrame Working_With_Menus_And_JPanels() {

        Border_Frame = new JFrame("Working with Menus & JPanels");
        Border_Frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Border_Frame.setSize(800, 400);
        Border_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(Project_Panels.Create_JPanel_One(), "Dashboard");
        mainPanel.add(Project_Panels.Create_JPanel_Two(), "Actions");
        mainPanel.add(Project_Panels.Create_JPanel_Three(), "Status");

        Border_Frame.add(mainPanel);

        Border_Frame.setJMenuBar(Project_Menus.CreatingJMenuBar(cardLayout, mainPanel));

        Border_Frame.setVisible(true);
        return Border_Frame;
    }
}
