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

	public static void drawGaugeTest(Graphics g, int percent) {
		int angle = (percent * 360) / 100;
//		int angle = (percent * 360) / 100;
		g.setColor(borderColor(percent));
		g.fillArc(50, 170, 220, 220, 90, angle);
		g.setColor(background);
		g.fillOval(50 + borderThick, 170 + borderThick, 220 - 2 * borderThick, 220 - 2 * borderThick);
		g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
		g.setColor(letterColor);
		g.drawString("test", 120, 280);
	}

	public void drawGauge(Graphics g, int index, int angle, int xLoc, int yLoc, int size) {
		int percent = (angle * 100) / 360;
		g.setColor(borderColor(percent));
		g.fillArc(xLoc, yLoc, size, size, 90, angle);
		g.setColor(background);
		g.fillOval(xLoc + borderThick, yLoc + borderThick, size - 2 * borderThick, size - 2 * borderThick);
		g.setFont(clearGothic);
		if(percent <= 10) {
			g.drawImage(cautionRed, xLoc + 90, yLoc + 160, this);
			g.setColor(letterCaution);
		}
		else if (percent < 30) {
			g.drawImage(cautionYellow, xLoc + 90, yLoc + 160, this);
			g.setColor(letterWatchout);
		}
		else {
			g.setColor(letterColor);
		}
		g.drawString(dataBase.attributes.get(index).name, xLoc + dataBase.attributes.get(index).letterSpace, yLoc + dataBase.attributes.get(index).titleLineSpace);
	}
	
	public static Color borderColor(int percent) {
		if (percent >= 30) {
			return borderNorm;
		}
		else if (percent > 10) {
			return borderWatchout;
		}
		else {
			return borderCaution;
		}
	}
	
	public static Color letterColor(int percent) {
		if (percent >= 30) {
			return letterColor;
		}
		else if (percent >= 10) {
			return letterWatchout;
		}
		else {
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
	}
}
