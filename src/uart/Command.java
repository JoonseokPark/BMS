package uart;

import java.time.LocalTime;

import gui.DataBase;

public class Command {
	volatile boolean input = false;
	volatile int command;
	volatile int num;
	int maxNum;
	int maxCom;
	double adcResult;
	byte[] inputBytes = new byte[1024];

	DataBase[] dataBase;

	public void setDataBase(DataBase[] dataBase) {
		this.dataBase = dataBase;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public Command() {
		this.command = 1;
		this.num = 1;
	}

	public Command(int maxNum) {
		this.maxNum = maxNum;
		this.command = 1;
		this.num = 1;
	}

	public double decodeCommand() {
//		if(inputBytes[0] == -91 && inputBytes[7] == 90) {
		if (inputBytes[0] == -91 && inputBytes[5] == -2) {
//			if(inputBytes[2] != command * 16 + num) {
			if (inputBytes[1] == command && inputBytes[2] == num) {
//				System.out.println("in Command.java, Success" + LocalTime.now());
				adcResult = decodeAdc(inputBytes[3], inputBytes[4], command);
				dataBase[num - 1].setVal(getOpcode((byte)command), adcResult);
				if(command == 2) {
					double temp = adcResult * 100 / 3;
					dataBase[num - 1].setVal(1, temp);
				}
				return adcResult;
			} else {
				System.out.println("in Command.java, not num" + "com : " + command + ", num : " + num);
				for (int i = 0; i < 6; i++) {
					System.out.println(inputBytes[i]);
				}
				return -1;
			}
		} else {
			System.out.println("in Command.java, not a5 fe");
			for (int i = 0; i < 6; i++) {
				System.out.println(inputBytes[i]);
			}
			return -1;
		}
	}

	public void nextCommand() {
		num++;
		if (num > maxNum) {
			num = 1;
			command++;
		}
		else if (command == 3 && num == 2) {
			command = 1;
			num = 1;
		}
		
//		if (command == 0x5) {
//			command = 1;
//		}
	}

	public double decodeAdc(Byte input1, Byte input2, int command) {
		double output = 256 * Byte.toUnsignedInt(input1) + Byte.toUnsignedInt(input2);
		System.out.println("output : " + output);
		if (command == 3) {
			output /= 100;
			return output;
		} else if (command == 2) {
			output *= 5;
			output /= 1024;
			output = -1 * (output - 2.5) * 5;
			output /= 2;
			return output;
		}
		output /= 1023;
		output *=15;
		return output;
	}

	public byte getOpcode(byte input) {
		switch (input) {
		case 0x01:
			return 3;
		case 0x02:
			return 4;
		case 0x03:
			return 0;
		case 0x04:
			return 2;
		default:
			return -1;
		}
	}
}
