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
	
	public void paint(Graphics g) {
        super.paint(g);
		setBackground(background);
//		this.setBackground(basicElements.background);
        g.setColor(borderNorm);
        drawGauge(g, 70);     
	}
	
	public static void drawGauge(Graphics g, int percent) {
		int angle = (percent * 360) / 100;
//		int angle = (percent * 360) / 100;
		g.setColor(borderNorm);
		g.fillArc(50, 170, 220, 220, 90, angle);
		g.setColor(background);
		g.fillOval(50 + borderThick, 170 + borderThick, 220 - 2 * borderThick, 220 - 2 * borderThick);
		g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
		g.setColor(letterColor);
		g.drawString("test", 120, 280);
	}
	
	public static void drawGauge(Graphics g, int percent, int xLoc, int yLoc, int size) {
		int angle = (percent * 360) / 100;
//		int angle = (percent * 360) / 100;
		g.setColor(borderNorm);
		g.fillArc(xLoc, yLoc, size, size, 90, angle);
		g.setColor(background);
		g.fillOval(50 + borderThick, 170 + borderThick, size - 2 * borderThick, size - 2 * borderThick);
		g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
		g.setColor(letterColor);
		g.drawString("test", 120, 280);
	}
}
