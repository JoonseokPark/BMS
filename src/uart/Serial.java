package uart;

import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gui.DataBase;

public class Serial {
	SerialReader serialReader;
	SerialWriter serialWriter;
	Command command;
	DataBase[] dataBase;
    
    public void setDataBase(DataBase[] dataBase) {
		this.dataBase = dataBase;
	}

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
                
                command = new Command(3);
                command.setDataBase(dataBase);
                serialReader.SetCommand(command);
                serialWriter.SetCommand(command);
//                Thread threadReader = new Thread(serialReader);
//                Thread threadWriter = new Thread(serialWriter);
//                threadReader.start();
//                threadWriter.start();
                for(int i = 0;i < 6;i++) {
                	serialWriter.writeCommand();
                	Thread.sleep(100);
                }
                while(true) {
                	serialWriter.writeCommand();
                	serialReader.readCommand();
                	if (command.decodeCommand() == -1) {
                		System.out.println("return -1");
                	}
                	else {
                		//DataBase에 저장
                		command.nextCommand();
//                		System.out.printf("in Serial.java, adcResult : %.3f\n", command.adcResult);
                		// ****** 0203할일 : 일부러 오류내서 값보내보기, History ************
                	}
                	Thread.sleep(1);
                }
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
}

