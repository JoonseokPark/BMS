package gui;

import static gui.BasicElements.background;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class GaugePanel extends MyPanel {
	int xLoc = 50;
	int yLoc = 170;

	public GaugePanel() {
		this.setPreferredSize(new Dimension(350, 600));
	}

	public GaugePanel(int num, int attribute) {
		if (num == 3) {
			this.setPreferredSize(new Dimension(350, 700));
		}
		else if (num == 4) {
			this.setPreferredSize(new Dimension(450, 600));
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		setBackground(background);
//		g.setColor(borderNorm);
//		drawGauge(g, 70);
	}

	public void drawGaugeTest(Graphics g, int percent) {
		int angle = dataBase.attributes.get(0).getAngle();
//		int angle = (percent * 360) / 100;
		drawGauge(g, 0, angle, 50, 170, 220);
		g.setColor(borderColor(dataBase.attributes.get(0).getDangerLev()));
		g.fillArc(50, 170, 220, 220, 90, angle);
		g.setColor(background);
		g.fillOval(50 + borderThick, 170 + borderThick, 220 - 2 * borderThick, 220 - 2 * borderThick);
		g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
		g.setColor(letterColor);
		g.drawString("test", 120, 280);
	}

	public void drawGauge(Graphics g, int index, int angle, int xLoc, int yLoc, int size) {
		int percent = (angle * 100) / 360;
		int danger = dataBase.attributes.get(index).getDangerLev();
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
		g.drawString(dataBase.attributes.get(index).name, xLoc + dataBase.attributes.get(index).letterSpace, yLoc + dataBase.attributes.get(index).titleLineSpace);
		g.drawString(dataBase.attributes.get(index).gaugeString(), xLoc + dataBase.attributes.get(index).valueSpace, yLoc + dataBase.attributes.get(index).valueLineSpace);
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
	public Color letterColor(int index) {
		int danger = dataBase.attributes.get(0).getDangerLev();
		if (danger == 0) {
			return letterColor;
		} else if (danger == 1) {
			return letterWatchout;
		} else {
			return letterCaution;
		}
	}
}

class TestPanel extends GaugePanel implements Runnable{
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(background);
//		this.setBackground(basicElements.background);
		g.setColor(borderNorm);
		drawGaugeTest(g, 70);
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
