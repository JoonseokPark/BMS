package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public MainFrame() {}

    public void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch(Exception e) {
            System.out.println(e);
        }

        count +=1;
        if(count%2 == 0) {
            mainPanel.perTemp = 200;
            mainPanel.temp = 30.7;
        }
        else {
            mainPanel.perTemp = 300;
            mainPanel.temp = 33.7;
        }

        this.repaint();
//        SwingUtilities.updateComponentTreeUI(this);
    }
}

class MainPanel extends MyPanel implements ActionListener {
    double temp = 30.0;
    int perTemp = 300;

    public void setPanel() {
        setSize(1020, 600);
//        add(bt);
//        bt.addActionListener(this);
        setVisible(true);
      RoundedButton bt = new RoundedButton("Button");
      add(bt);
      bt.addActionListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        setBackground(background);
        GaugePanel.drawGauge(g,12);
        g.setColor(borderNorm);
        g.fillArc(50, 80, 220, 220, 90, perTemp);
        g.fillArc(400, 80, 220, 220, 90, 358);
        g.fillOval(750, 80, 220, 220);
        g.setColor(borderCaution);
        g.fillArc(225, 300, 220, 220, 90, 5);
        g.setColor(borderWatchout);
        g.fillArc(575, 300, 220, 220, 90, 100);
        g.setColor(background);
        g.fillOval(50 + 6, 80 + 6, 220 - 12, 220 - 12);
        g.fillOval(400 + 6, 80 + 6, 220 - 12, 220 - 12);
        g.fillOval(750 + 6, 80 + 6, 220 - 12, 220 - 12);
        g.fillOval(225 + 6, 300 + 6, 220 - 12, 220 - 12);
        g.fillOval(575 + 6, 300 + 6, 220 - 12, 220 - 12);
        g.setColor(letterColor);
        g.setFont(new Font("franklin gothic book", Font.PLAIN, 45));
        g.drawString(dataBase.attributes.get(0).name,110 , 180);
//        g.drawString("Temp",400 + tempSpace , 180);
        g.drawString(Double.toString(temp) + "¨¬C",100 , 235);
        g.drawString(dataBase.attributes.get(1).name,445 , 180);
//        g.drawString("Battery",50+batterySpace , 180);
        g.drawString("99%",470 , 235);
        g.drawString(dataBase.attributes.get(2).name,770 , 180);
//        g.drawString("Life",400 + lifeSpace , 400);
        g.drawString("Safe", 815, 235);
        g.setColor(letterCaution);
        g.drawString(dataBase.attributes.get(3).name,270, 400);
//        g.drawString("Voltage",575 +voltSpace, 400);
        g.drawString("2.72V",285 , 455);
        g.setColor(letterWatchout);
        g.drawString(dataBase.attributes.get(4).name,620 , 400);
//        g.drawString("Current",50 + currentSpace , 180);
        g.drawString("0.31A",635 , 455);
        g.drawImage(cautionYellow, 670, 460, this);
        g.drawImage(cautionRed, 315, 460, this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new TestFrame();
	}
}
