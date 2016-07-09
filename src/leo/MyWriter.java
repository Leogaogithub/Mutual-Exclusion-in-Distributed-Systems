package leo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyWriter {
	private RandomAccessFile logFile = null;
	String prefix = "";
	
	//private String rootPath = "";
	public void open(String name, int nodeId){
		try {
			logFile = new RandomAccessFile(name, "rw");
			//int id =  ConfigExpert.getSingleton().getLocalNodeId();
			prefix = String.valueOf(nodeId);
			prefix += ":";
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}		
	}	
	
	public void write(String str){	
		try {
			logFile.writeBytes(str);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}	
	
	public void log(String str){
		synchronized(this){
			write(prefix);
			write(str);
			write("\n");
		}
	}
	
	public void clear(){
		try {			
			logFile.setLength(0);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
