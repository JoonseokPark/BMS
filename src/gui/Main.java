package gui;

import uart.*;
import java.time.LocalTime;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class Main {
    public static void main(String[] args) {
    	DataBase[] dataBase = new DataBase[3];
    	
    	for (int i = 0;i < 3;i++) {
    		dataBase[i] = new DataBase();
    	}
    	
    	MainFrame mainFrame = new MainFrame(dataBase);
        for(int i = 0;i < 3;i++) {
        	mainFrame.setMainPanel(i, dataBase[i]);
        }
        
        (new Thread(mainFrame)).start();
        
//        HistoryFrame hp = new HistoryFrame(dataBase[0], 3);
//        for (int i = 0;i < 100;i++) {
//        	abc();
//        }

        try {
        	Serial serial = new Serial();
        	serial.setDataBase(dataBase);
            (serial).connect("COM17");
            System.out.println("in main.java, Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

