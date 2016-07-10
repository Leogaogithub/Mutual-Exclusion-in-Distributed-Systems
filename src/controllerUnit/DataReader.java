package controllerUnit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;


public class DataReader{

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static Vector<String> readLines(String name){
		File file = new File(name);
		Vector<String> lines =  new Vector<String>();
	    if(!file.exists()) {
	    	System.out.println("No File Found " + name);
	    }	
	    
	    InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream(file));
		    BufferedReader br = new BufferedReader(isr);
		    String line;
		    while((line = br.readLine()) != null ) {
		    	lines.add(line);
		    }	   
		    br.close();
		    isr.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

	    return lines;
    }
}
