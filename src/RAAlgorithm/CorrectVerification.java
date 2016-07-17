package RAAlgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CorrectVerification {
	
	
	private RandomAccessFile logRequestCS= null;
	private RandomAccessFile logEnterFile = null;
	
	public CorrectVerification(String name){
	
		try {
			logEnterFile = new RandomAccessFile("enterCS"+name, "rw");
			logRequestCS = new RandomAccessFile("requestCS"+name, "rw");

		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		
	}
	


	
	public void enterCSTS(String str){
		
		try {
			logEnterFile.writeChars(str+";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	public void requestCS(String str){
		
		try {
			logRequestCS.writeChars(str+";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	

}
