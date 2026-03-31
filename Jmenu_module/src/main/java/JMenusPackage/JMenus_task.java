package JMenusPackage;

import javax.swing.*;
import java.awt.*;

public class JMenus_task {
    JMenuItem JMenuItemOne, JMenuItemTwo, JMenuItemThree, JMenuItemFour;
    JMenu Pro_Menu;
    JMenuBar Pro_MenuBar;

    public JMenus_task() {
    }

    public JMenuBar CreatingJMenuBar(CardLayout layout, JPanel panelContainer) {
        Pro_MenuBar = new JMenuBar();
        Pro_MenuBar.add(this.createJMenu(layout, panelContainer));
        return Pro_MenuBar;
    }

    public JMenu createJMenu(CardLayout layout, JPanel panelContainer) {
        Pro_Menu = new JMenu("Menu");

        Pro_Menu.add(CreateJMenuItemOne());
        Pro_Menu.add(CreateJMenuItemTwo());
        Pro_Menu.add(CreateJMenuItemThree());
        Pro_Menu.add(CreateJMenuItemFour());

        JMenuItemOne.addActionListener(e -> layout.show(panelContainer, "Dashboard"));
        JMenuItemTwo.addActionListener(e -> layout.show(panelContainer, "Actions"));
        JMenuItemThree.addActionListener(e -> layout.show(panelContainer, "Status"));
        JMenuItemFour.addActionListener(e -> System.exit(0));

        return Pro_Menu;
    }

    public JMenuItem CreateJMenuItemOne() {
        JMenuItemOne = new JMenuItem("Dashboard");
        return JMenuItemOne;
    }

    public JMenuItem CreateJMenuItemTwo() {
        JMenuItemTwo = new JMenuItem("Actions");
        return JMenuItemTwo;
    }

    public JMenuItem CreateJMenuItemThree() {
        JMenuItemThree = new JMenuItem("Status");
        return JMenuItemThree;
    }

    public JMenuItem CreateJMenuItemFour() {
        JMenuItemFour = new JMenuItem("Exit");
        return JMenuItemFour;
    }
}


