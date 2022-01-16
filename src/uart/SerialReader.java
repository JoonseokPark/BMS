package uart;
import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements Runnable {
    InputStream in;
    SerialWriter serialWriter;
    Command command;
    
    public void SetCommand(Command command) {
    	this.command = command;
    }

	public SerialReader(InputStream in) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        char[] bufferChar = new char[100];
        int len = -1;
        int compareInt;
        
        try {
            while ((len = this.in.read(buffer)) > -1) { 				
            	String temp = new String(buffer, 0, len);
            	
            	if(len > 0)
            		System.out.println("len : "+len);
            	
            	for(int i = 0;i < len;i++) {
            		System.out.println("buffer[i] : " +(int)buffer[i]);
            	}
            	for(int i=0;i<len;i++) {
            		System.out.println(ByteToStrTest.bytesToBinaryString(buffer[i]));            		
            	}
            	
            	if (len > 0) {
            		for(int i = 0;i < len;i++) {
            			if(command.input == false) {
            				command.command = (int)buffer[i];
            				command.input = true;
            			}
            		}
            	}
//            	try {
//            		if(command.input == false) {
//            			compareInt = Integer.parseInt(temp);
//            			command.command = compareInt;            			
//            			command.input = true;
//            		}
//            	} catch (NumberFormatException e) {
//            	}            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ByteToStrTest {
    static String bytesToBinaryString(Byte b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(((0x80 >>> i) & b) == 0 ? '0' : '1');
        }

        return builder.toString();
    }
}