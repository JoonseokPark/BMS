package uart;
import java.io.IOException;
import java.io.InputStream;

public class SerialReader {
    InputStream in;
    SerialWriter serialWriter;
    Command command;
    
    public void SetCommand(Command command) {
    	this.command = command;
    }

	public SerialReader(InputStream in) {
        this.in = in;
    }
    
    public void readCommand() {
        byte[] buffer = new byte[1024];
        int len = 0;
        int tempLen = -1;
        int frameByte = 8;
        int compareInt;
        
        try {
            while (len < frameByte) {
            	tempLen = this.in.read(buffer);
            	if (tempLen < 0) {
            		break;
            	}
            	
            	if (tempLen > 0) {
            		for(int i = len;i < tempLen + len;++i) {
            			command.inputBytes[i] = buffer[i - len];
            		}
            	}
            	
            	len += tempLen;
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