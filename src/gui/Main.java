package gui;

public class Main {
    public static void main(String[] args) {
    	DataBase dataBase = new DataBase();
        // Main Frame
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();
        mainPanel.setPanel();
        mainPanel.setDataBase(dataBase);
        mainFrame.setMainPanel(mainPanel);
        mainFrame.setResizable(false);

        while(true) {
            mainFrame.sleep(1);
        }

//        try {
//            (new Serial()).connect("COM11");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

