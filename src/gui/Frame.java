package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

public class Frame extends JFrame implements BasicElements {
	public Frame() {
		setSize(1020, 600);
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		getContentPane().setBackground(background);
	}
}

class TestFrame extends Frame implements Runnable{
	GraphPanel testPanel;
	TestPanel testPanel2;

	public TestFrame() {
		JPanel pn = new JPanel();
		this.setLocation(0, 350);
		(new Thread(this.testPanel2 = new TestPanel())).start();
		this.testPanel = new GraphPanel();
//		this.testPanel2 = new TestPanel();
		this.setSize(1120, 600);

		BorderLayout fl = new BorderLayout();
//        pn.setLayout(null);
		pn.setLayout(fl);
//        testPanel.setBounds(300, 0, 720, 600);
//        testPanel2.setBounds(0, 0, 300, 600);
		pn.add(testPanel, BorderLayout.CENTER);
		pn.add(testPanel2, BorderLayout.WEST);
		this.add(pn);
//        this.setContentPane(testPanel);
//        this.setContentPane(testPanel2);
		boolean tempbool = true;
	}
	
	public void run() {
		while(true) {
			sleep();
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sleep() {
		if (testPanel.tempPhase < 500)
			testPanel.tempPhase++;
		else
			testPanel.tempPhase = 0;
		this.repaint();
	}
}
