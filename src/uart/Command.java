package uart;

public class Command {
	volatile boolean input = false;
	volatile int command;
	volatile int num;
	int maxNum;
	int adcResult;
//	byte[] inputBytes = new byte[8];
	byte[] inputBytes = new byte[1024];
	
	public Command() {
		this.command = 0;
		this.num = 0;
	}
	
	public Command(int maxNum) {
		this.maxNum = maxNum;
		this.command = 0;
		this.num = 0;
	}
	
	public int decodeCommand() {
		if(inputBytes[0] == -91 && inputBytes[7] == 90) {
			if(inputBytes[2] != command * 16 + num) {
				System.out.println("not num");
				for(int i = 0;i < 4;i++) {
					System.out.println(inputBytes[i]);
				}
				return -1;
			}
			else {
				System.out.println("Success");
				for(int i = 0;i < 8;i++) {
					System.out.println(inputBytes[i]);
				}
				return adcResult = decodeAdc(inputBytes[4], inputBytes[3]);
			}
		}
		else {
			System.out.println("not a5 5a");
			for(int i = 0;i < 4;i++) {
				System.out.println(inputBytes[i]);
			}
			return -1;
		}
	}
	
	public void nextCommand() {
		num++;
		if (num == maxNum) {
			num = 0;
			command++;
		}
		if (command == 0x4) {
			command = 1;
		}
	}

	public void setCommand(int command) {
		this.command = command;
	}
	
	public int decodeAdc(Byte input1, Byte input2) {
		int output = 256 * Byte.toUnsignedInt(input1) + Byte.toUnsignedInt(input2); 
		return output;
	}
}
