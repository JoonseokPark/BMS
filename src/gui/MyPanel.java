package gui;

import javax.swing.JPanel;

public class MyPanel extends JPanel implements BasicElements {
	DataBase dataBase;
	int attIndex;

	public void setDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}
}