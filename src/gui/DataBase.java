package gui;

import java.util.ArrayList;

public class DataBase {
    ArrayList<Attribute> attributes = new ArrayList<>();

    public DataBase() { 
      Temperature temperature = new Temperature();
      Battery battery = new Battery();
//      BatteryLife batteryLife = new BatteryLife();
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

    public void addAttributes (Attribute attribute) {
        this.attributes.add(attribute);
    }
}

class Attribute {
    String name;
    double attribute;

    public Attribute(String name) {
        this.name = name;
    }

    public Attribute(String name, double attribute) {
        this.name = name;
        this.attribute = attribute;
    }
}

class Temperature extends Attribute {
    public Temperature() {
        super("Temp");
    }
}

class Battery extends Attribute {
    public Battery() {
        super("Battery");
    }
}

class BatteryLife extends Attribute {
    int actualCapacity;
    int designCapacity;
    int chargeTime;

    public BatteryLife() {
        super("Life");
    }
}

class Voltage extends Attribute {
    double volt;

    public Voltage() {
        super("Voltage");
    }
}

class Current extends Attribute {
    double current;

    public Current() {
        super("Current");
    }
}

class Diagnosis extends Attribute {
	public Diagnosis() {
		super("Diagnosis");
	}
}