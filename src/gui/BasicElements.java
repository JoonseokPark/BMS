package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

interface BasicElements {
    public static final Color background = new Color(28, 29, 73);
    public static final Color borderNorm = new Color(51, 255, 163);
    public static final Color borderWatchout = new Color(197, 90, 17);
    public static final Color borderCaution = new Color(255, 0, 0);
    
    public static final Color letterColor = new Color(221, 220, 220);
    public static final Color letterWatchout = new Color(255, 192, 0);
    public static final Color letterCaution = borderWatchout;

    public static final Color graphBar = new Color(129, 193, 71);
    public static final Color graphYellow = new Color(150, 90, 17);
    public static final Color graphRed = new Color(150, 40, 40);
    public static final Color graphGrid = new Color(221, 220, 220);
    public static final Color graphScale = new Color(150, 150, 150);

    public static final Font clearGothic = new Font("franklin gothic book", Font.PLAIN, 45);    									   
    
    public static final int borderThick = 6;

    public static final Image cautionYellow = Toolkit.getDefaultToolkit().getImage("C:\\Users\\JSPARK\\IntellijProject\\GuiTest\\src\\caution_yellow.png");
    public static final Image cautionRed = Toolkit.getDefaultToolkit().getImage("C:\\Users\\JSPARK\\IntellijProject\\GuiTest\\src\\caution_red.png");
}

