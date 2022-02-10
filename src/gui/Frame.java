package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class Frame extends JFrame implements BasicElements {
	DataBase dataBase;
	int thread = 1;
	int attIndex;
	
	public void setDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}

	public Frame() {
		setSize(1020, 600);
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		getContentPane().setBackground(background);
	}
}

class AttributeFrame extends Frame {
	GraphPanel[] graphPanel = new GraphPanel[3];
	GaugePanel[] gaugePanel = new GaugePanel[3];

	public AttributeFrame(int attIndex, int num) {
		this.attIndex = attIndex;
		JPanel pn = new JPanel();
		this.setLocation(0, 0);
		if (num == 1) {
			this.setSize(1300, 368);
		}
		else {
			this.setSize(1300, 1029);
		}
		addWindowListener(new MyWindowAdapter());
		
        int[] width = {400, 1000};
        int height = 330;
        pn.setLayout(null);
		
		for(int i = 0;i < num;i++) {
			(new Thread(this.gaugePanel[i] = new GaugePanel(attIndex))).start();
			(new Thread(this.graphPanel[i] = new GraphPanel(attIndex, 1000, 60))).start();
		}
		
        for (int i = 0; i < num; i++) {
            gaugePanel[i].setBounds(0, height * i, width[0], height);
            graphPanel[i].setBounds(width[0], height * i, width[1], height);
            pn.add(gaugePanel[i]);
			pn.add(graphPanel[i]);
        }
		this.add(pn);
	}
	
	public void windowClosing(WindowEvent e) {
		dispose();
	}

	class MyWindowAdapter extends WindowAdapter {
		public MyWindowAdapter() {
			super();
		}

		public void windowClosing(WindowEvent e) {
			thread = 0;
			dataBase.attributes.get(attIndex).activating = 0;
			dispose();
		}
	}
}
