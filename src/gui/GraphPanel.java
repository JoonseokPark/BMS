package gui;

import java.awt.*;

public class GraphPanel extends MyPanel implements Runnable {
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	int xAxisLoc;
	int yAxisLoc;
	int xAxisLen;
	int yAxisLen;
	int gridLen;
	int tempPhase = 0;

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

	public void run() {
		while (true) {
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		setBackground(basicElements.background);
		this.setBackground(background);
		g.setColor(new Color(255, 255, 255));
		drawGraph(g, 0);
//		drawTestGraph(g);
		drawGrid(g, xAxisLoc, yAxisLoc, xAxisLen, yAxisLen);
	}

	public void drawGrid(Graphics g, int xAxisLoc, int yAxisLoc, int xAxisLen, int yAxisLen) {
		Graphics2D g2d = (Graphics2D) g;
		int width = 1;
		g2d.setStroke(new BasicStroke(width));
		g2d.setColor(BasicElements.graphScale);
		for (int i = 0; i < 6; i++) {
			int gridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			g2d.drawLine(yAxisLoc, gridLoc, yAxisLoc + gridLen, gridLoc);
			g.drawLine(yAxisLoc, gridLoc, yAxisLoc + xAxisLen, gridLoc);
		}

		g2d.setColor(BasicElements.graphGrid);
		width = 3;
		g2d.setStroke(new BasicStroke(width));
		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc, xAxisLoc - yAxisLen);
		g2d.drawLine(yAxisLoc, xAxisLoc, yAxisLoc + xAxisLen, xAxisLoc);

		for (int i = 1; i <= 5; i++) {
			int xGridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			double tempYGrid = (xMax * i) / 5;
			g.drawString(Double.toString(tempYGrid), yAxisLoc - 40, xGridLoc + 4);
			g2d.drawLine(yAxisLoc, xGridLoc, yAxisLoc + gridLen, xGridLoc);
		}
		for (int i = 0; i <= 5; i++) {
			int xGridLoc = yAxisLoc + ((xAxisLen * i) / 5);
			double tempXGrid = (yMax * i) / 5;
			g.drawString(Double.toString(tempXGrid) + "ms", xGridLoc - 20, xAxisLoc + 30);
			g2d.drawLine(xGridLoc, xAxisLoc, xGridLoc, xAxisLoc + gridLen);
		}
	}

	public void drawTestGraph(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int[] xGraph = new int[1000];
		int[] yGraph = new int[1000];
		for (int i = 0; i < 300; i++) {
			xGraph[i] = yAxisLoc + 20 * i;
			double j = (double) (i + tempPhase) / 10;
			yGraph[i] = xAxisLoc - 100 - (int) (60 * Math.sin(j));
		}
//		for (int i = 0; i < 480; i++) {
		for (int i = 0; i < 480; i+=2) {
			g2d.setColor(BasicElements.graphBar);
//			g2d.setColor(BasicElements.graphYellow);
//			g2d.setColor(BasicElements.graphRed);
//			g2d.drawLine(yAxisLoc + i, (i%10/10)*yGraph[i/10] + (1-(i%10/10))*yGraph[(i+10)/10], yAxisLoc + i, xAxisLoc + 5);
			g2d.drawLine(yAxisLoc + i, smoothGraph(i, yGraph[i / 10], yGraph[(i + 10) / 10]), yAxisLoc + i, xAxisLoc - 3);
		}
		g2d.setColor(BasicElements.borderNorm);
//		g2d.drawPolyline(xGraph, yGraph, xAxisLen / 10);
	}
	
	public void drawGraph(Graphics g, int index) {
		Graphics2D g2d = (Graphics2D) g;
		int[] xGraph = new int[100];
		int[] yGraph = new int[100];
		for (int i = 0; i < 100; i++) {
			xGraph[i] = yAxisLoc + 20 * i;
			int j = (yAxisLen * dataBase.attributes.get(index).getPercent(i)) / 100;
			yGraph[i] = xAxisLoc - j;
		}
//		for (int i = 0; i < xAxisLen; i++) {
		for (int i = 0; i < xAxisLen; i+=2) {
			int yVal = smoothGraph(i, yGraph[i / 10], yGraph[(i + 10) / 10]);
			g2d.setColor(getGraphCol(index, i / 10));
//			g2d.drawLine(yAxisLoc + i, (i%10/10)*yGraph[i/10] + (1-(i%10/10))*yGraph[(i+10)/10], yAxisLoc + i, xAxisLoc + 5);
			g2d.drawLine(yAxisLoc + xAxisLen - i, yVal, yAxisLoc + xAxisLen - i, xAxisLoc - 3);
		}
//		System.out.println("in drawGraph, yGraph[0] : "+ yGraph[0] + "attriutes[0] : " + dataBase.attributes.get(index).getPercent(0));
//		g2d.setColor(BasicElements.borderNorm);
//		g2d.drawPolyline(xGraph, yGraph, xAxisLen / 10);
	}

	public int smoothGraph(int i, int a, int b) {
		int res = (int) ((1 - ((double) i % 10 / 10)) * a + ((double) i % 10 / 10) * b);
		return res;
	}

	public void nextPhase() {
		if (tempPhase < 500)
			tempPhase++;
		else
			tempPhase = 0;
	}
	
	
	public Color getGraphCol(int attribute, int index) {
		int danger = dataBase.attributes.get(attribute).getDangerLev(index);
		if(danger == 0) {
			return graphBar;
		}
		else if (danger == 1) {
			return graphYellow;
		}
		else {
			return graphRed;
		}
	}
	
	public void sleep2() {
		if (dataBase.attributes.get(0).tempPhase < 500)
			dataBase.attributes.get(0).tempPhase++;
		else
			dataBase.attributes.get(0).tempPhase = 0;
		this.repaint();
	}
}
