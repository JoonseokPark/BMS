package gui;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class DataBase {
	public ArrayList<Attribute> attributes = new ArrayList<>();

	public DataBase() {
		Temperature temperature = new Temperature();
		Battery battery = new Battery();
		Diagnosis diagnosis = new Diagnosis();
//		Gas gas= new Gas();
		Voltage voltage = new Voltage();
		Current current = new Current();

		addAttribute(temperature);
		addAttribute(battery);
		addAttribute(diagnosis);
//		addAttribute(gas);
		addAttribute(voltage);
		addAttribute(current);
	}
	
	public void setVal(int AttIndex, double val) {
		attributes.get(AttIndex).setval(val);
//		System.out.println("in DataBase class, setval, val : " + val + "turn : " + turn);
	}

	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}
}

class Attribute {
	String title;
	double val;
	double[] values = new double[100];
	double[][] history = new double[1024][4];
	int letterSpace;
	int valueSpace;
	int titleLineSpace = 100;
	int valueLineSpace = 155;
	int tempPhase = 0;
	int activating = 0;
	int turn = 0;
	int hisCount = 0;
	double yMax;
	double yMin;
	String unit = "";

	public Attribute(String title) {
		this.title = title;
	}

	public Attribute(String title, double val) {
		this.title = title;
		this.val = val;
	}
	
	public void setval(double val) {
		System.out.println("in Attribute class, setval, val : " + val + "turn : " + turn);
		if (turn <= 71) {
			this.val = val;
//			if(turn > 10) {
//				for(int i = turn;i > turn - 10;i--) {
//					this.val += values[i];
//				}
//				this.val += val;
//				this.val /= 11;
//			}
			if(turn % 30 == 0) {
				history[hisCount][0] = val;
		        LocalTime now = LocalTime.now();
		        history[hisCount][1] = now.getHour();
		        history[hisCount][2] = now.getMinute();
		        history[hisCount][3] = now.getSecond(); 
				hisCount++;
			}
			nextPhase(turn);
		}
		else {
//			for(int i = 72;i > 72 - 10;i--) {
//				this.val += values[i];
//			}
//			this.val += val;
//			this.val /= 11;
			if(turn % 30 == 0) {
				history[hisCount][0] = val;
		        LocalTime now = LocalTime.now();
		        history[hisCount][1] = now.getHour();
		        history[hisCount][2] = now.getMinute();
		        history[hisCount][3] = now.getSecond(); 
				hisCount++;
			}
			nextPhase(72);
		}
	}
	
	public void setHisCount(int hisCount) {
		this.hisCount = hisCount;
	}

	public void nextPhase(int index) {
		if (index <= 71) {
			values[index] = val;
		}
		else {
			for(int i=0; i < 71; i++) {
				values[i] = values[i+1];
			}
			values[70] = val;
		}
		turn++;
		if(turn == 100000000) {
			turn = 100;
		}
	}
	
	public int getPercent(double val) {
		return normPercent((int) val);
	}

	public int getPercent() {
		return getPercent(val);
	}

	public int getPercent(int index) {
		return getPercent(values[index]);
	}
	
	public int getPercent(int his, int index) {
		return getPercent(history[index][0]);
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
	
	//getPercentÃ³·³ ±ò²ûÇÏ°Ô ´Ù¹Ù²ã¾ßÇÔ
	public int getDangerLev(int percent) {
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
	
	public int getDangerLev(double val) {
		int percent = getPercent(val);
		return getDangerLev(percent);
	}

	public int getDangerLev() {
		int percent = getPercent();
		return getDangerLev(percent);
	}

	public int getDangerLev(int type, int index) {
		int percent;
		if (type == 0) {
			percent = getPercent(index);
		} else if (type == 1) {
			percent = getPercent(type, index);
		}
		else {
			return 2;
		}		
		return getDangerLev(percent);
	}
	
	public void testSineWave(int tempPhase, int speed) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / speed;
			values[i] = 20 + (10 * Math.sin(j));
		}
	}
		
	//nextPhase·Î ¹Ù²ã¾ßÇÔ
	public void tempNextPhase(int speed) {
		if (tempPhase < 5000)
			tempPhase++;
		else
			tempPhase = 0;
		testSineWave(tempPhase, speed);
	}
	
	public String gaugeString() {
		return String.format("%.1f", val) + unit;
	}
}

class Temperature extends Attribute {
	public Temperature() {
		super("Temp");
		this.letterSpace = 60;
		this.valueSpace = 50;
		this.tempPhase = 0;
		this.unit = "¨¬C";
		this.yMax = 60;
		this.yMin = -20;
		this.val = -20;
		for(int i = 0;i < 100;i++) {
			values[i] = -20;
		}
	}
	
	public int getPercent(double val) {
		int percent = (int) (100 * (val + 20) / 80);
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
	
	public String gaugeString() {
		return String.format("%.1f", val) + unit;
	}

	public void testSineWave(int tempPhase, int speed) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / speed;
			values[i] = 20 + (35 * Math.sin(j));
		}
		val = values[0];
	}
	
//	public void testSineWave(int tempPhase, int speed) {
//		for (int i = 0; i < 100; i++) {
//			double j = (double) (i + tempPhase) / speed;
//			values[i] = (55 + 2 * Math.random());
//		}
//		val = values[0];
//	}

	
	public void tempNextPhase(int speed) {
		if (tempPhase < 5000)
			tempPhase++;
		else
			tempPhase = 0;
		testSineWave(tempPhase, speed);
	}
}

class Battery extends Attribute {
	public Battery() {
		super("Battery");
		this.letterSpace = 45;
		this.valueSpace = 70;
		this.val = 25;
		this.unit = "%";
		this.yMax = 100;
		this.yMin = 0;
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
		return String.format("%.0f", val) + unit;
	}
	
	public void testSineWave(int tempPhase, int speed) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase + 30) / speed;
			values[i] = 50 + (45 * Math.sin(j));
		}
		val = values[0];
//		System.out.println("values[0] : " + values[0]);
	}
}

class Gas extends Attribute {
	public Gas() {
		super("Gas");
		this.letterSpace = 80;
		this.valueSpace = 30;
		this.tempPhase = 0;
		this.unit = "dPPM";
		this.yMax = 100;
		this.yMin = 0;
	}

	public int getDangerLev() {
		int percent = getPercent();
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else if (percent >= 0) {
			return 0;
		} else {
			return 2;
		}
	}
	
	public String gaugeString() {
		return String.format("%d", (int)val) + unit;
	}

	public void testSineWave(int tempPhase, int speed) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / speed;
			values[i] = 50 + (43 * Math.sin(j));
		}
		val = values[0];
//		System.out.println("values[0] : " + values[0]);
	}
	
	public void tempNextPhase(int speed) {
		if (tempPhase < 5000)
			tempPhase++;
		else
			tempPhase = 0;
		testSineWave(tempPhase, speed);
	}
}

class Diagnosis extends Attribute {
	public Diagnosis() {
		super("Diagnosis");
		this.letterSpace = 20;
		this.valueSpace = 65;
		this.val = 100;
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
	
	public int getDangerLev(int input) {
		int percent = getPercent(input);
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
	
	public void testSineWave(int tempPhase, int speed) {
		for (int i = 0; i < 100; i++) {
			double j = (double) (i + tempPhase) / speed;
			values[i] = 90 + (5 * Math.sin(j));
		}
	}
}

class Voltage extends Attribute {
	double volt;

	public Voltage() {
		super("Voltage");
		this.letterSpace = 45;
		this.valueSpace = 60;
		this.val = 1.50;
		this.unit = "V";
		this.yMax = 3.0;
		this.yMin = 1.0;
	}
	
	public int getPercent(double val) {
		int percent = (int) ((100 * (val - this.yMin)) / (yMax- yMin));
		return normPercent(percent);
	}

//	public int getPercent(int charge, double input) {
//		int percent = (int) (100 * (input - 2.35) / 1.85);
//		// discharging
//		percent = normPercent(percent);
//		return percent;
//	}

	public int getDangerLev(int percent) {
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
	
	public String gaugeString() {
		return String.format("%.2f", val) + unit;
	}

	public void testSineWave(int tempPhase, int speed) {
//		for (int i = 0; i < 100; i++) {
//			double j = (double) (i + tempPhase + 30) / 30;
//			values[i] = 3.3 + (0.8 * Math.sin(j));
//		}
//		val = values[48];
////		System.out.println("values[0] : " + values[0]);
	}
}

class Current extends Attribute {
	double current;

	public Current() {
		super("Current");
		this.letterSpace = 45;
		this.valueSpace = 35;
		this.val = 100;
		this.unit = "mA";
		this.yMax = 1000;
		this.yMin = 0;
	}
	
	public void setval(double val) {
		System.out.println("in Attribute class, setval, val : " + val + "turn : " + turn);
		if (turn <= 71) {
			this.val = val * 1000;
			if(turn % 30 == 0) {
				history[hisCount][0] = val * 1000;
		        LocalTime now = LocalTime.now();
		        history[hisCount][1] = now.getHour();
		        history[hisCount][2] = now.getMinute();
		        history[hisCount][3] = now.getSecond(); 
				hisCount++;
			}
			nextPhase(turn);
		}
		else {
			this.val = val * 1000;
			if(turn % 30 == 0) {
				history[hisCount][0] = val * 1000;
		        LocalTime now = LocalTime.now();
		        history[hisCount][1] = now.getHour();
		        history[hisCount][2] = now.getMinute();
		        history[hisCount][3] = now.getSecond(); 
				hisCount++;
			}
			nextPhase(72);
		}
	}
	
	public int getPercent(double val) {
		int percent = (int) ((100 * (val - yMin)) / (yMax - yMin));
		return normPercent(percent);
	}
	
	public int getDangerLev(int percent) {
		if (percent > 90) {
			return 2;
		} else if (percent > 80) {
			return 1;
		} else {
			return 0;
		}
	}

//	public void testSineWave(int tempPhase, int speed) {
//		for (int i = 0; i < 100; i++) {
//			double j = (double) (i + tempPhase + 100) / speed;
//			values[i] = 1.975 + (0.91 * Math.sin(j));
//		}
//		val =  values[0];
//	}
	
	public String gaugeString() {
		return String.format("%3.1f", val) + unit;
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