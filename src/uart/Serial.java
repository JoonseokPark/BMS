package uart;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	SerialReader serialReader;
	SerialWriter serialWriter;
	Command command;
    
    public Serial() {
        super();
    }

    public void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                serialReader = new SerialReader(in);
                serialWriter = new SerialWriter(out);
                
                
                
                command = new Command();
                serialReader.SetCommand(command);
                serialWriter.SetCommand(command);
                Thread threadReader = new Thread(serialReader);
                Thread threadWriter = new Thread(serialWriter);
                threadReader.start();
                threadWriter.start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
}

