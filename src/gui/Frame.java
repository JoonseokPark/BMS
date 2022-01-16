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

public class Frame extends JFrame implements BasicElements{
	final static int borderThick = 6;

    public Frame() {
        setSize(1020, 600);
    	this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        getContentPane().setBackground(background);
    }
    
	public static void drawGauge(Graphics g, int percent) {
		int angle = (percent * 360) / 100;
//		int angle = (percent * 360) / 100;
		g.setColor(new Color(51, 255, 163));
		g.fillArc(50, 80, 220, 220, 90, angle);;
		g.setColor(new Color(28, 29, 73));
		g.fillOval(50 + borderThick, 80 + borderThick, 220 - 2 * borderThick, 220 - 2 * borderThick);
	}
}
 

class TestFrame extends Frame {
	public TestFrame() {
		JPanel pn = new JPanel();
		this.setLocation(0, 350);
		GraphPanel testPanel = new GraphPanel();
		GaugePanel testPanel2 = new GaugePanel();
//		this.add(new GraphPanel());
		
        BorderLayout fl = new BorderLayout();
//        pn.setLayout(null);
        pn.setLayout(fl);
//        testPanel.setBounds(300, 0, 720, 600);
//        testPanel2.setBounds(0, 0, 300, 600);
        pn.add(testPanel, BorderLayout.CENTER);
        pn.add(testPanel2, BorderLayout.WEST);
        this.add(pn);
//        this.setContentPane(testPanel);
//        FlowLayout fl2 = new FlowLayout();
//        fl2.setAlignment(FlowLayout.RIGHT);
//        testPanel2.setLayout(fl2);
//        this.setContentPane(testPanel2);
	}
}

