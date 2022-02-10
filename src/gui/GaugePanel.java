package gui;

import static gui.BasicElements.background;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class GaugePanel extends MyPanel implements Runnable{
	public GaugePanel() {
		this.setPreferredSize(new Dimension(350, 330));
	}
	
	public GaugePanel(int attIndex) {
		this.attIndex = attIndex;
	}

	public void paint(Graphics g) {
		super.paint(g);
		setBackground(background);
		drawGauge(g, attIndex, dataBase.attributes.get(attIndex).getAngle(), 50, 70, 220);
	}


	public void drawGauge(Graphics g, int attIndex, int angle, int xLoc, int yLoc, int size) {
		if(angle == 100) {
			buzzer();
		}
		int danger = dataBase.attributes.get(attIndex).getDangerLev();
		g.setColor(borderColor(danger));
		g.fillArc(xLoc, yLoc, size, size, 90, angle);
		g.setColor(background);
		g.fillOval(xLoc + borderThick, yLoc + borderThick, size - 2 * borderThick, size - 2 * borderThick);
		g.setFont(clearGothic);
		if(danger == 2) {
			g.drawImage(cautionRed, xLoc + 90, yLoc + 160, this);
			g.setColor(letterCaution);
			
		}
		else if (danger == 1) {
			g.drawImage(cautionYellow, xLoc + 90, yLoc + 160, this);
			g.setColor(letterWatchout);
		}
		else {
			g.setColor(letterColor);
		}
		g.drawString(dataBase.attributes.get(attIndex).title, xLoc + dataBase.attributes.get(attIndex).letterSpace, yLoc + dataBase.attributes.get(attIndex).titleLineSpace);
		g.drawString(dataBase.attributes.get(attIndex).gaugeString(), xLoc + dataBase.attributes.get(attIndex).valueSpace, yLoc + dataBase.attributes.get(attIndex).valueLineSpace);
	}
	
	public static Color borderColor(int lev) {
		if (lev == 0) {
			return borderNorm;
		} else if (lev == 1) {
			return borderWatchout;
		} else if (lev == 2) {
			return borderCaution;
		} else {
			return borderCaution;			
		}
	}
	
	// 안쓰는중
	public Color letterColor(int attIndex) {
		int danger = dataBase.attributes.get(attIndex).getDangerLev();
		if (danger == 0) {
			return letterColor;
		} else if (danger == 1) {
			return letterWatchout;
		} else {
			return letterCaution;
		}
	}
	
	
    public void buzzer() {
        File bgm;
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        
        bgm = new File("C:\\Users\\JSPARK\\eclipse-workspace_Java\\BMS\\sound\\beep-01a.wav"); // 사용시에는 개별 폴더로 변경할 것
        
        Clip clip;
        
        try {
               stream = AudioSystem.getAudioInputStream(bgm);
               format = stream.getFormat();
               info = new DataLine.Info(Clip.class, format);
               clip = (Clip)AudioSystem.getLine(info);
               clip.open(stream);
               clip.start();
               
        } catch (Exception e) {
               System.out.println("err : " + e);
        }
        
    }
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(33);
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}