package gui;

import static gui.BasicElements.background;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import gui.AttributeFrame.MyWindowAdapter;


public class HistoryFrame extends Frame {
	public HistoryFrame(DataBase dataBase, int attIndex) {
		this.attIndex = attIndex;
		HistoryPanel panel = new HistoryPanel(dataBase, attIndex, 100, 1000);
		this.setSize(1000, 538);
		setLayout(null);
		JScrollPane tableScrollPane = new JScrollPane(panel);
		panel.setLayout(new GridBagLayout());
		tableScrollPane.setBounds(0, 0, 980, 500);
		panel.setBackground(Color.green);
//		dataBase.attributes.get(attIndex).setHisCount(7);
//		for(int i = 0;i < 7;i++)
//			dataBase.attributes.get(this.attIndex).history[i][0] = 2.4 + 0.1*i;
		
		int xAxisLen = dataBase.attributes.get(attIndex).hisCount - 1;
		if(xAxisLen < 8) {
			xAxisLen = 8;
		}
		xAxisLen *= 100;
        for(int i = 0;i < ((xAxisLen + 50) / 14);i++) {
        	JLabel testlabel2 = new JLabel("     ");
        	panel.add(testlabel2);
        }
		this.add(tableScrollPane);
		this.setVisible(true);
        addWindowListener(new MyWindowAdapter());
	}
	
	class MyWindowAdapter extends WindowAdapter {
		public MyWindowAdapter() {
			super();
		}

		public void windowClosing(WindowEvent e) {
			dispose();
		}
	}
}

class HistoryPanel extends MyPanel {	
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	int xAxisLoc;
	int yAxisLoc;
	int xAxisLen;
	int yAxisLen;
	int gridLen;	

	public HistoryPanel(DataBase dataBase, int attIndex, double xMax, double yMax) {
		this.dataBase = dataBase;
		this.attIndex = attIndex;
		this.xMin = 0;
		this.xMax = xMax;
		this.yMin = -20;
		this.yMax = yMax;
		this.xAxisLoc = 450 + 150;
		
		this.yAxisLoc = 90;
		this.xAxisLen = 700;
		this.yAxisLen = 240;
		this.gridLen = 7;
		this.setLayout(new GridBagLayout());
		setLayout(null);
        initComponents();
        setDoubleBuffered(false);
        setOpaque(false);
        this.setVisible(true);
	}
	
    private void initComponents() {
        // TODO Auto-generated method stub
    	
    }

    public void paint(Graphics g) {
    	super.paint(g);
    	this.setBackground(background);
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setStroke(new BasicStroke(600));
    	g2d.setColor(background);
//		g2d.drawLine(0, 300, xAxisLen + 1000, 300);
    	//나중에 여기 다시 풀어야함
    	drawGrid(g, attIndex, 350, yAxisLoc, 250);
    	drawGraph(g, attIndex, 350, yAxisLoc);
    }

	public void drawGrid(Graphics g, int attIndex, int xAxisLoc, int yAxisLoc, int yAxisLen) {
		Graphics2D g2d = (Graphics2D) g;
		int xAxisLen = dataBase.attributes.get(attIndex).hisCount - 1;
		if(xAxisLen < 8) {
			xAxisLen = 8;
		}
		xAxisLen *= 100;
		
		int width = 600;
		g2d.setStroke(new BasicStroke(width));
		g2d.setColor(background);
		g2d.drawLine(0, 300, xAxisLen + 1000, 300);
//		int width = 1;
		width = 1;
		g2d.setStroke(new BasicStroke(width));
		g2d.setColor(graphScale);
		for (int i = 0; i < 6; i++) {
			int gridLoc = xAxisLoc - ((yAxisLen * i) / 5);
			g2d.drawLine(yAxisLoc, gridLoc, yAxisLoc + gridLen, gridLoc);
			g.drawLine(yAxisLoc, gridLoc, yAxisLoc + xAxisLen, gridLoc);
		}

		g2d.setColor(graphGrid);
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
			} else if(attIndex == 2) {
				g.drawString(String.format("%.0f", tempYGrid) + dataBase.attributes.get(attIndex).unit, yAxisLoc - 60, xGridLoc + 4);
			}
			else {
				g.drawString(String.format("%.2f", tempYGrid) + dataBase.attributes.get(attIndex).unit, yAxisLoc - 40, xGridLoc + 4);
			}
			g2d.drawLine(yAxisLoc, xGridLoc, yAxisLoc + gridLen, xGridLoc);
		}

		for (int i = 0; i <= xAxisLen / 100; i++) {
			int xGridLoc = yAxisLoc + (100 * i);
			g.drawString(xAxisString(attIndex, i), xGridLoc - 20, xAxisLoc + 30);
			g2d.drawLine(xGridLoc, xAxisLoc, xGridLoc, xAxisLoc + gridLen);
		}
	}
	
	public String xAxisString(int attIndex, int hisIndex) {
		if(dataBase.attributes.get(attIndex).hisCount > hisIndex) {
			return String.format("%d:%d:%d", (int)dataBase.attributes.get(attIndex).history[hisIndex][1], (int)dataBase.attributes.get(attIndex).history[hisIndex][2], (int)dataBase.attributes.get(attIndex).history[hisIndex][3]);
		} else {
			return "";
		}
	}
	
	public void drawGraph(Graphics g, int attIndex, int xAxisLoc, int yAxisLoc) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		int[] yGraph = new int[100];
		for (int i = 0; i < 100; i++) {
			int j = (yAxisLen * dataBase.attributes.get(attIndex).getPercent(0, i)) / 100;
			yGraph[i] = xAxisLoc - j;
		}
		int xAxisLen = dataBase.attributes.get(attIndex).hisCount - 1;
		if(xAxisLen < 8) {
			xAxisLen = 8;
		}
		xAxisLen *= 100;
		for (int i = 0; i < 100 * (dataBase.attributes.get(attIndex).hisCount - 1); i+=2) {
			int yVal = smoothGraph(i, yGraph[i / 100], yGraph[(i + 100) / 100]);
			g2d.setColor(getGraphCol(attIndex, i / 100));
			g2d.drawLine(yAxisLoc + i, yVal, yAxisLoc + i, xAxisLoc - 3);
		}
	}

	public int smoothGraph(int i, int a, int b) {
		int res = (int) ((1 - ((double) i % 100 / 100)) * a + ((double) i % 100 / 100) * b);
		return res;
	}
	
	public Color getGraphCol(int attIndex, double val) {
		int danger = dataBase.attributes.get(attIndex).getDangerLev(val);
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
	
	public Color getGraphCol(int attIndex, int index) {
		int danger = dataBase.attributes.get(attIndex).getDangerLev(1, index);
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
	
	public void addLabel() {
		for(int i = 0;i < 3;i++) {
        	JLabel testlabel2 = new JLabel("     ");
        	this.add(testlabel2);
        }
	}
}
