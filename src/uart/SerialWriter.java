package uart;
import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter {
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
    	int command = this.command.command * 16 + this.command.num;
    	try {
    		this.out.write((byte)0xA5);
			this.out.write(command);
//			this.out.write((byte)0x00);
			this.out.write((byte)0x5A);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}