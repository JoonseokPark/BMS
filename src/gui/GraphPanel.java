package gui;

import java.awt.*;

public class GraphPanel extends MyPanel {
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	int xAxisLoc;
	int yAxisLoc;
	int xAxisLen;
	int yAxisLen;
	int gridLen;
	
	public GraphPanel() {
		this.setPreferredSize(new Dimension(670, 600));
		this.xMin = 0;
		this.xMax = 100;
		this.yMin = 0;
		this.yMax = 250;
		this.xAxisLoc = 450;
		this.yAxisLoc = 70;
		this.xAxisLen = 480;
		this.yAxisLen = 250;
		this.gridLen = 7;
		
	}
	
	public GraphPanel(double xMax, double yMax) {
		this.xMax = xMax;
		this.yMax = yMax;
		setVisible(true);
	}
	
	public GraphPanel(double xMin, double yMin, double xMax, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		setVisible(true);
	}
	
	public void paint(Graphics g) {
        super.paint(g);
//		setBackground(basicElements.background);
		this.setBackground(background);
		g.setColor(new Color(255, 255, 255));
		drawGrid(g, xAxisLoc, yAxisLoc, xAxisLen, yAxisLen);
//		Graphics2D g2d = (Graphics2D)g;
//		int width = 2;
//		g2d.setStroke(new BasicStroke(3));
//		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc, xAxisLoc - yAxisLen);
//		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc + xAxisLen, xAxisLoc);
//		
//		for (int i = 0;i < 6;i++) {
//			int gridLoc = xAxisLoc - ((yAxisLen * i) / 5);
//			double tempyGrid = (yMax * i) / 5;
//			g2d.drawLine(yAxisLoc, gridLoc, yAxisLoc + gridLen, gridLoc);
//			g.drawString(Double.toString(tempyGrid), yAxisLoc - 40 , gridLoc + 4);
//		}
//		g2d.setStroke(new BasicStroke(1));
//		g2d.setColor(new Color(200, 200, 200));
//		for (int i = 0;i < 6;i++) {
//			int gridLoc = xAxisLoc - ((yAxisLen * i) / 5);
//			g2d.drawLine(yAxisLoc, gridLoc, yAxisLoc + gridLen, gridLoc);
//			g.drawLine(yAxisLoc, gridLoc, yAxisLoc + xAxisLen, gridLoc);
//		}
	}
	
	public void drawGrid(Graphics g, int xAxisLoc, int yAxisLoc, int xAxisLen, int yAxisLen) {
		Graphics2D g2d = (Graphics2D)g;
		int width = 1;
		g2d.setStroke(new BasicStroke(width));
		g2d.setColor(new Color(150, 150, 150));
		for (int i = 0;i < 6;i++) {
			int gridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			g2d.drawLine(yAxisLoc, gridLoc, yAxisLoc + gridLen, gridLoc);
			g.drawLine(yAxisLoc, gridLoc, yAxisLoc + xAxisLen, gridLoc);
		}
		
		g2d.setColor(letterColor);
		width = 3;
		g2d.setStroke(new BasicStroke(width));
		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc, xAxisLoc - yAxisLen);
		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc + xAxisLen, xAxisLoc);
		
		for (int i = 1;i <= 5;i++) {
			int xGridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			double tempYGrid = (xMax * i) / 5;
			g.drawString(Double.toString(tempYGrid), yAxisLoc - 40 , xGridLoc + 4);
			g2d.drawLine(yAxisLoc, xGridLoc, yAxisLoc + gridLen, xGridLoc);
		}
		for (int i = 0;i <= 5;i++) {
			int xGridLoc = yAxisLoc + ((xAxisLen * i) / 5);
			double tempXGrid = (yMax * i) / 5;
			g.drawString(Double.toString(tempXGrid), xGridLoc - 20 , xAxisLoc + 30);
			g2d.drawLine(xGridLoc, xAxisLoc, xGridLoc, xAxisLoc + gridLen);
		}
		
		
	}
}
