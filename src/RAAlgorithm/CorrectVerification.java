package RAAlgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CorrectVerification {
	
	
	private RandomAccessFile logLeaveCS= null;
	private RandomAccessFile logEnterCS = null;
	
	public CorrectVerification(String name){
	
		try {
			logEnterCS = new RandomAccessFile("enterCS"+name, "rw");
			logLeaveCS = new RandomAccessFile("leaveCS"+name, "rw");

		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
	}
	


	
	public void enterCS(String str){
		
		try {
			logEnterCS.writeChars(str+";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void leaveCS(String str){
		
		try {
			logLeaveCS.writeChars(str+";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	

}
