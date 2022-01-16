package uart;
import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {
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

    public void run() {
        try {
            char temp;
            //
//            int c = 0;
//            while ((c = System.in.read()) > -1) {
//                this.out.write(c);
//            }
            //
            while (true) {
            	if (command.input == true) {
	            	if(command.command == 1) {
	            		temp = (char)'2';
	            		this.out.write(2);
	            		command.input = false;            	
	            		command.command = 0;
	            	}
	            	else if (command.command == 2) {
	            		temp = (char)'3';
	            		this.out.write(3);
	            		command.input = false;
	            		command.command = 0;
	            	}
	            	else if (command.input) {
	            		temp = (char)'0';
	            		this.out.write(0);
	            		command.input = false;
	            	}
	            	command.input = false;
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}