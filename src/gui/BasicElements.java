package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

interface BasicElements {
    final Color background = new Color(28, 29, 73);
    final Color borderNorm = new Color(51, 255, 163);
    final Color borderWatchout = new Color(197, 90, 17);
    final Color borderCaution = new Color(255, 0, 0);
    final Color letterColor = new Color(221, 220, 220);
    final Color letterWatchout = new Color(255, 192, 0);
    final Color letterCaution = borderWatchout;

    final Font clearGothic = new Font("clear gothic", Font.PLAIN, 30);
	final static int borderThick = 6;

    final Image cautionYellow = Toolkit.getDefaultToolkit().getImage("C:\\Users\\JSPARK\\IntellijProject\\GuiTest\\src\\caution_yellow.png");
    final Image cautionRed = Toolkit.getDefaultToolkit().getImage("C:\\Users\\JSPARK\\IntellijProject\\GuiTest\\src\\caution_red.png");

//    Temperature temperature = new Temperature();
//    Battery battery = new Battery();
//    BatteryLife batteryLife = new BatteryLife();
//    Voltage voltage = new Voltage();
//    Current current = new Current();
//    DataBase dataBase;

//    public void setDataBase(DataBase dataBase) {
//        this.dataBase = dataBase;
//    }
//
//    public BasicElements() {        
//    	dataBase = new DataBase();
//        dataBase.addAttributes(temperature);
//        dataBase.addAttributes(battery);
//        dataBase.addAttributes(batteryLife);
//        dataBase.addAttributes(voltage);
//        dataBase.addAttributes(current);
//        this.setDataBase(dataBase);
//    }
}

