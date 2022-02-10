package uart;
import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable{
    OutputStream out;
    Command command;
    
    public void SetCommand(Command command) {
    	this.command = command;
    }
    
    public SerialWriter(OutputStream out) {
        this.out = out;
    }
    
    public void ffff(char temp ) {
    	
    }
    
    public void writeCommand() {
//    	int command = this.command.command * 16 + this.command.num;
    	try {
//    		this.out.write((byte)0xA5);
//			this.out.write((byte)0x01);
//			this.out.write((byte)0x01);
//			this.out.write((byte)0x00);
//			this.out.write((byte)0xFE);
			this.out.write((byte)0xA5);
			this.out.write(command.command);
			this.out.write(command.num);
			this.out.write((byte)0xFE);
//			System.out.println("write");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void termination() {
		try {
			this.out.write((byte)0xA5);
			this.out.write((byte)0xFF);
			this.out.write((byte)0xFF);
			this.out.write((byte)0xFE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void run() {
    	while(true) {
    		writeCommand();
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}