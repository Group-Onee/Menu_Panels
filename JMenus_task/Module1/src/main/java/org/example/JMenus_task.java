package org.example;

import javax.swing.*;

public class JMenus_task {
    JMenuItem JMenuItemOne, JMenuItemTwo, JMenuItemThree, JMenuItemFour;
    public JMenus_task() {
    }
    public JMenuItem CreateJMenuItemOne(){
        JMenuItemOne = new JMenuItem("Menu Item One");
        return JMenuItemOne;
    }

    public JMenuItem CreateJMenuItemTwo() {
        JMenuItemTwo = new JMenuItem("Menu Item Two");
        return JMenuItemTwo;
    }

    public JMenuItem CreateJMenuItemThree() {
        JMenuItemThree = new JMenuItem("Menu Item Three");
        return JMenuItemThree;
    }

    public JMenuItem CreateJMenuItemFour() {
        JMenuItemTwo = new JMenuItem("Menu Item Four");

        return JMenuItemFour;
    }
}


