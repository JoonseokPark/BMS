package gui;

import uart.*;

public class Main {
    public static void main(String[] args) {
    	DataBase dataBase = new DataBase();
    	
    	dataBase.attributes.get(0).attribute = 40;
    	System.out.println("getAngle : " + dataBase.attributes.get(0).getAngle());
    	
        // Main Frame
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();
        mainPanel.setPanel();
        mainPanel.setDataBase(dataBase);
        mainFrame.setMainPanel(mainPanel);
        mainFrame.setResizable(false);
        
        
        (new Thread(new TestFrame())).start();
//        try {
//            (new Serial()).connect("COM11");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        while(true) {
        	try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	mainFrame.sleep(100);
        }
    }
}

