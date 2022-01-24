package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

public class MainFrame extends Frame {
	MainPanel mainPanel;
	int count = 0;

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.add(mainPanel);
		this.setVisible(true);
	}

	public MainFrame() {
	}	

	public void sleep(int milsec) {
//        try {
////            TimeUnit.SECONDS.sleep(sec);
//            Thread.sleep(milsec);
//        } catch(Exception e) {
//            System.out.println(e);
//        }

		count += 1;
		if (count % 100 == 0) {
			mainPanel.perTemp = 200;
			mainPanel.temp = 30.7;
		} else {
			mainPanel.perTemp++;
			mainPanel.temp += 0.02;
		}

		this.repaint();
//        SwingUtilities.updateComponentTreeUI(this);
	}
}

class MainPanel extends GaugePanel implements ActionListener {
	double temp = 30.0;
	int perTemp = 200;
	JButton[] bt = new JButton[5];

	TestFrame testFrame;
	
	public void setPanel() {
		setSize(1020, 600);
		setLayout(null);
		setVisible(true);
		for(int i = 0;i < 5;i++) {
			bt[i] = new JButton(" ");
			add(bt[i]);
			bt[i].addActionListener(this);
			bt[i].setBorderPainted(false);
			bt[i].setContentAreaFilled(false);
			bt[i].setFocusPainted(false);
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
		drawGauge(g, 0, dataBase.attributes.get(0).getAngle(), 50, 80, gaugeSize);
		drawGauge(g, 1, 358, 400, 80, gaugeSize);
		drawGauge(g, 2, 360, 750, 80, gaugeSize);
		drawGauge(g, 3, 5, 225, 300, gaugeSize);
		drawGauge(g, 4, 121, 575, 300, gaugeSize);
		g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
		g.setColor(letterColor);
//		g.drawString(String.format("%.1f", dataBase.attributes.get(0).attribute) + "¨¬C", 100, 235);
//		g.drawString("99%", 470, 235);
//		g.drawString("Safe", 815, 235);
		g.setColor(letterCaution);
//		g.drawString("2.72V", 285, 455);
		g.setColor(letterWatchout);
//		g.drawString("0.31A", 635, 455);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0;i < 5;i++) {
			if(e.getSource() == bt[i] && dataBase.attributes.get(i).activating == 0) {
				testFrame = new TestFrame();
				testFrame.testPanel.setDataBase(dataBase);
				testFrame.testPanel2.setDataBase(dataBase);
				testFrame.setDataBase(dataBase);
				(new Thread(testFrame)).start();
				dataBase.attributes.get(i).activating = 1;
			}
		}
//		testFrame = new TestFrame();
//		testFrame.testPanel.setDataBase(dataBase);
//		testFrame.testPanel2.setDataBase(dataBase);
//		(new Thread(testFrame)).start();
	}
}
