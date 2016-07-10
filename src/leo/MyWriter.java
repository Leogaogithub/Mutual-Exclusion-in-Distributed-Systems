package leo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyWriter {
	private RandomAccessFile logFile = null;
	String prefix = null;
	
	void setPrefix(String prefix){
		this.prefix = prefix;
	}
	//private String rootPath = "";
	public void open(String name){
		try {
			logFile = new RandomAccessFile(name, "rw");						
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
			if(prefix!=null) write(prefix);			
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
