package gui;

import javax.swing.*;

import static gui.BasicElements.background;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

public class MainFrame extends JFrame implements Runnable{
	DataBase[] dataBase = new DataBase[3];
	MainPanel mainPanel;

	public void setMainPanel(int dataBaseIndex, DataBase dataBase) {
		this.dataBase[dataBaseIndex] = dataBase;
		mainPanel.setDataBase(dataBaseIndex, dataBase);
	}

	public MainFrame(DataBase[] dataBase) {
		addWindowListener(new MyWindowAdapter());
		this.mainPanel = new MainPanel(dataBase);
		this.mainPanel.setPanel();
		this.add(mainPanel);
		this.setVisible(true);
		this.setResizable(false);
		setSize(1020, 600);
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(33);
				for(int i = 0;i < 3;i++) {
					for(int j = 0;j < 5;j++) {
//						dataBase[i].attributes.get(j).tempNextPhase(i * 10 + 20);
					}
				}
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void windowClosing(WindowEvent e) {
		dispose();
	}

	class MyWindowAdapter extends WindowAdapter {
		public MyWindowAdapter() {
			super();
		}

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}

class MainPanel extends JPanel implements BasicElements {
	DataBase[] dataBase = new DataBase[3];
	JButton[] bt = new JButton[5];
	AttributeFrame[] attributeFrame = new AttributeFrame[5];
	
	public void setDataBase(int dataBaseIndex, DataBase dataBase) {
		this.dataBase[dataBaseIndex] = dataBase;
	}

	public MainPanel(DataBase[] dataBase) {
		this.dataBase = dataBase;
	}

	public void setPanel() {
		setSize(1020, 600);
		setLayout(null);
		setVisible(true);
		ButtonListener buttonListener = new ButtonListener();
		for(int i = 0;i < 5;i++) {
			bt[i] = new JButton(" ");
			add(bt[i]);

			bt[i].setBorderPainted(false);
			bt[i].setContentAreaFilled(false);
			bt[i].setFocusPainted(false);
			if (i != 2) {
				bt[i].addActionListener(buttonListener);
			}
//			bt[i].addActionListener(buttonListener);
		}
		bt[0].setBounds(50, 80, 200, 200);
		bt[1].setBounds(400, 80, 200, 200);
		bt[2].setBounds(750, 80, 200, 200);
		bt[3].setBounds(225, 300, 200, 200);
		bt[4].setBounds(575, 300, 200, 200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int gaugeSize = 220;
		setBackground(background);
		// **** 이거 나중에 database[0]말고 평균으로 바꿔야됨 *****
		
		drawGauge(g, 0, dataBase[0].attributes.get(0).getAngle(), 50, 80, gaugeSize);
		drawGauge(g, 1, dataBase[0].attributes.get(1).getAngle(), 400, 80, gaugeSize);
		drawGauge(g, 2, dataBase[0].attributes.get(2).getAngle(), 750, 80, gaugeSize);
		drawGauge(g, 3, dataBase[0].attributes.get(3).getAngle(), 225, 300, gaugeSize);
		drawGauge(g, 4, dataBase[0].attributes.get(4).getAngle(), 575, 300, gaugeSize);
	}
	
	public void drawGauge(Graphics g, int index, int angle, int xLoc, int yLoc, int size) {
		int danger = dataBase[0].attributes.get(index).getDangerLev();
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
		g.drawString(dataBase[0].attributes.get(index).title, xLoc + dataBase[0].attributes.get(index).letterSpace, yLoc + dataBase[0].attributes.get(index).titleLineSpace);
		g.drawString(dataBase[0].attributes.get(index).gaugeString(), xLoc + dataBase[0].attributes.get(index).valueSpace, yLoc + dataBase[0].attributes.get(index).valueLineSpace);
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
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0;i < 5;i++) {
				if(e.getSource() == bt[i] && dataBase[0].attributes.get(i).activating == 0) {
					int num;
					if (i == 0) {
						num = 1;
					}
					else {
						num = 3;
					}
					attributeFrame[i] = new AttributeFrame(i, num);
					for(int j = 0;j < num;j++) {
						//이런 직접참조 다 바꿔야함
						attributeFrame[i].gaugePanel[j].setDataBase(dataBase[j]);
						attributeFrame[i].graphPanel[j].setDataBase(dataBase[j]);
					}
					attributeFrame[i].setDataBase(dataBase[0]);
					dataBase[0].attributes.get(i).activating = 1;
				}
			}
		}
	}
}
