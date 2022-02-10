package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphPanel extends MyPanel implements Runnable{
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	int xAxisLoc;
	int yAxisLoc;
	int xAxisLen;
	int yAxisLen;
	int gridLen;
	
	RoundedButton hisBt;

	public GraphPanel(int attIndex, double xMax, double yMax) {
		this.setPreferredSize(new Dimension(1000, 360));
		this.xMin = 0;
		this.xMax = xMax;
		this.yMin = -20;
		this.yMax = yMax;
		this.xAxisLoc = 450 + 150;
		this.yAxisLoc = 70;
		this.xAxisLen = 700;
		this.yAxisLen = 240;
		this.gridLen = 7;
		this.attIndex = attIndex;
		this.setLayout(null);
		hisBt = new RoundedButton("History");
		hisBt.addActionListener(new ButtonListener());
		this.add(hisBt);
		hisBt.setBounds(400, 10, 60, 30);
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
		drawGraph(g, attIndex, 285, yAxisLoc);
		drawGrid(g, 285, yAxisLoc, xAxisLen, yAxisLen);
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

		for (int i = 0; i <= 5; i++) {
			int xGridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			double tempYGrid = (dataBase.attributes.get(attIndex).yMax - dataBase.attributes.get(attIndex).yMin) * i / 5;
			tempYGrid += dataBase.attributes.get(attIndex).yMin;
			if(attIndex == 0 || attIndex == 1) {
				g.drawString(String.format("%.0f", tempYGrid) + dataBase.attributes.get(attIndex).unit, yAxisLoc - 40, xGridLoc + 4);
			} else if(attIndex == 2 || attIndex == 4) {
				g.drawString(String.format("%.0f", tempYGrid) + dataBase.attributes.get(attIndex).unit, yAxisLoc - 60, xGridLoc + 4);
			}
			else {
				g.drawString(String.format("%.2f", tempYGrid) + dataBase.attributes.get(attIndex).unit, yAxisLoc - 40, xGridLoc + 4);
			}
			g2d.drawLine(yAxisLoc, xGridLoc, yAxisLoc + gridLen, xGridLoc);
		}
		for (int i = 0; i <= 5; i++) {
			int xGridLoc = yAxisLoc + ((xAxisLen * i) / 5);
			double tempXGrid = (xMax * i) / 5;
			g.drawString(String.format("%.0fms", tempXGrid), xGridLoc - 20, xAxisLoc + 30);
			g2d.drawLine(xGridLoc, xAxisLoc, xGridLoc, xAxisLoc + gridLen);
		}
	}
	
	public void drawGraph(Graphics g, int index, int xAxisLoc, int yAxisLoc) {
		Graphics2D g2d = (Graphics2D) g;
		int[] yGraph = new int[100];
		for (int i = 0; i < 100; i++) {
			int j = (yAxisLen * dataBase.attributes.get(index).getPercent(i)) / 100;
			yGraph[i] = xAxisLoc - j;
		}
		for (int i = 0; i < xAxisLen; i+=2) {
			int yVal = smoothGraph(i, yGraph[i / 10], yGraph[(i + 10) / 10]);
			g2d.setColor(getGraphCol(index, i / 10));
			g2d.drawLine(yAxisLoc + i, yVal, yAxisLoc + i, xAxisLoc - 3);
		}
	}

	public int smoothGraph(int i, int a, int b) {
		int res = (int) ((1 - ((double) i % 10 / 10)) * a + ((double) i % 10 / 10) * b);
		return res;
	}
	
	public Color getGraphCol(int attribute, int index) {
		int danger = dataBase.attributes.get(attribute).getDangerLev(0, index);
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
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			HistoryFrame hp = new HistoryFrame(dataBase, attIndex);
		}
	}	
}
