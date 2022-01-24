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
	double[] attributes = new double[100];
	int letterSpace;
	int valueSpace;
	int titleLineSpace = 100;
	int valueLineSpace = 155;
	int tempPhase = 0;
	int activating = 0;
	String unit = "";

	public Attribute(String name) {
		this.name = name;
	}

	public Attribute(String name, double attribute) {
		this.name = name;
		this.attribute = attribute;
	}

	public int getPercent() {
		return normPercent((int) attribute);
	}

	public int getPercent(int index) {
		int percent = (int) (100 * (attributes[index] + 20) / 80);
		return normPercent(percent);
	}

	public int normPercent(int percent) {
		if (percent < 0) {
			return 0;
		} else if (percent > 100) {
			return 100;
		} else {
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

	public int getDangerLev(int input) {
		int percent = getPercent(input);
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
	
	public void testSineWave(int tempPhase) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / 30;
			attributes[i] = 20 + (10 * Math.sin(j));
		}
	}
	
	public void tempNextPhase() {
		if (tempPhase < 500)
			tempPhase++;
		else
			tempPhase = 0;
		testSineWave(tempPhase);
	}
	
	public String gaugeString() {
		return String.format("%.1f", attribute) + unit;
	}
}

class Temperature extends Attribute {
	
	
	public Temperature() {
		super("Temp");
		this.letterSpace = 60;
		this.valueSpace = 50;
		this.tempPhase = 0;
		this.unit = "¨¬C";
	}

	public int getPercent() {
		int percent = (int) (100 * (attribute + 20) / 80);
		return normPercent(percent);
	}

	public int getPercent(int index) {
		int percent = (int) (100 * (attributes[index] + 20) / 80);
		return normPercent(percent);
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

	public void testSineWave(int tempPhase) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / 30;
			attributes[i] = 20 + (35 * Math.sin(j));
		}
		attribute = attributes[48];
//		System.out.println("attributes[0] : " + attributes[0]);
	}
	
	public void tempNextPhase() {
		if (tempPhase < 500)
			tempPhase++;
		else
			tempPhase = 0;
		testSineWave(tempPhase);
	}
}

class Battery extends Attribute {
	public Battery() {
		super("Battery");
		this.letterSpace = 45;
		this.valueSpace = 70;
		this.attribute = 99;
		this.unit = "%";
	}

	public int getDangerLev() {
		int percent = getPercent();
		if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public String gaugeString() {
		return String.format("%.0f", attribute) + unit;
	}
}

class Diagnosis extends Attribute {
	public Diagnosis() {
		super("Diagnosis");
		this.letterSpace = 20;
		this.valueSpace = 65;
		this.attribute = 100;
	}
	
	public int getDangerLev() {
		int percent = getPercent();
		if (percent >= 20) {
			return 0;
		} else if (percent >= 10) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public String gaugeString() {
		int danger = getDangerLev();
		if(danger == 0) {			
			return "Safe";
		}
		else {
			return "Danger";
		}
	}
}

class Voltage extends Attribute {
	double volt;

	public Voltage() {
		super("Voltage");
		this.letterSpace = 45;
		this.valueSpace = 60;
		this.attribute = 2.46;
		this.unit = "V";
	}

	public int getPercent(int charge) {
		int percent = (int) (100 * (attribute - 2.35) / 1.85);
		// discharging
		percent = normPercent(percent);
		return percent;
	}

	public int getPercent(int charge, int input) {
		int percent = (int) (100 * (input - 2.35) / 1.85);
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
		this.attribute = 12;
		this.unit = "A";
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