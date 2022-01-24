package gui;

import uart.*;

public class Main {
    public static void main(String[] args) {
    	DataBase dataBase = new DataBase();
    	
    	dataBase.attributes.get(0).attribute = 40;
    	
        // Main Frame
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();
        mainPanel.setPanel();
        mainPanel.setDataBase(dataBase);
        mainFrame.setMainPanel(mainPanel);
        mainFrame.setResizable(false);
        
//        TestFrame testFrame = new TestFrame();
//        testFrame.testPanel.setDataBase(dataBase);
//        testFrame.testPanel2.setDataBase(dataBase);
//        (new Thread(testFrame = new TestFrame())).start();
//        try {
//            (new Serial()).connect("COM11");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        while(true) {
        	try {
				Thread.sleep(33);
				mainFrame.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}

