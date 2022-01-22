package gui;

import java.util.ArrayList;

public class DataBase {
	ArrayList<Attribute> attributes = new ArrayList<>();

	public DataBase() {
		Temperature temperature = new Temperature();
		Battery battery = new Battery();
		Diagnosis diagnosis = new Diagnosis();
		Voltage voltage = new Voltage();
		Current current = new Current();

		addAttributes(temperature);
		addAttributes(battery);
//      addAttributes(batteryLife);
		addAttributes(diagnosis);
		addAttributes(voltage);
		addAttributes(current);
	}

	public void addAttributes(Attribute attribute) {
		this.attributes.add(attribute);
	}
}

class Attribute {
	String name;
	double attribute;
	int letterSpace;
	int valueSpace;
	int titleLineSpace = 100;
	int valueLineSpace = 155;

	public Attribute(String name) {
		this.name = name;
	}

	public Attribute(String name, double attribute) {
		this.name = name;
		this.attribute = attribute;
	}

	public int getPercent() {
		int percent = normPercent((int)attribute);
		return percent;
	}
	
	public int normPercent(int percent) {
		if(percent < 0) {
			return 0;
		}
		else if (percent > 100) {
			return 100;
		}
		else {
			return percent;
		}
	}

	public int getAngle() {
		return (int) (getPercent() * 3.6);
	}

	public int getDangerLev() {
		int percent = getPercent();
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
}

class Temperature extends Attribute {
	public Temperature() {
		super("Temp");
		this.letterSpace = 60;
		this.valueSpace = 50;
	}
	
	public int getPercent() {
		int percent = (int) (100 * (attribute + 20) / 80);
		percent = normPercent(percent);
		return percent;
	}
	
	public int getDangerLev() {
		int percent = getPercent();
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
}

class Battery extends Attribute {
	public Battery() {
		super("Battery");
		this.letterSpace = 45;
		this.valueSpace = 70;
	}
	
	public int getDangerLev() {
		int percent = getPercent();
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
}

class Diagnosis extends Attribute {
	public Diagnosis() {
		super("Diagnosis");
		this.letterSpace = 20;
		this.valueSpace = 65;
	}
}

class Voltage extends Attribute {
	double volt;

	public Voltage() {
		super("Voltage");
		this.letterSpace = 45;
		this.valueSpace = 60;
	}

	public int getPercent(int charge) {
		int percent = (int) (100 * (attribute - 2.35) / 1.85);
		// discharging
		percent = normPercent(percent);
		return percent;
	}
	
	public int getDangerLev() {
		int percent = getPercent();
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
}

class Current extends Attribute {
	double current;

	public Current() {
		super("Current");
		this.letterSpace = 45;
		this.valueSpace = 60;
	}
}

//class BatteryLife extends Attribute {
//	int actualCapacity;
//	int designCapacity;
//	int chargeTime;
//
//	public BatteryLife() {
//		super("Life");
//		this.letterSpace = 20;
//		this.valueSpace = 65;
//	}
//}