package JPanelsPackage;

import javax.swing.*;
import javax.swing.border.Border;

public class CreatingJPanels {
    JPanel Panel_One, Panel_Two, Panel_Three, Panel_Four;

    public JPanel Create_JPanel_One() {
        Panel_One = new JPanel();
        Border Panel_One_Border = BorderFactory.createTitledBorder("Panel One");
        Panel_One.setBorder(Panel_One_Border);
        return Panel_One;
    }
    public JPanel Create_JPanel_Two() {
        Panel_Two = new JPanel();
        Border Panel_Two_Border = BorderFactory.createTitledBorder("Panel Two");
        Panel_Two.setBorder(Panel_Two_Border);
        return Panel_Two;
    }
    public JPanel Create_JPanel_Three() {
        Panel_Three = new JPanel();
        Border Panel_Three_Border = BorderFactory.createTitledBorder("Panel Three");
        Panel_Three.setBorder(Panel_Three_Border);
        return Panel_Three;
    }
    public JPanel Create_JPanel_Four() {
        Panel_Four = new JPanel();
        Border Panel_Four_Border = BorderFactory.createTitledBorder("Panel Four");
        Panel_Four.setBorder(Panel_Four_Border);
        return Panel_Four;
    }
}
