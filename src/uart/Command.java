package uart;
public class Command {
	volatile int command = 0;
	volatile boolean input = false;
	
	public Command() {
		
	}
}
