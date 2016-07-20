package demo;

import controllerUnit.ControllerRicartDemo;


public class Host4 {
	public static void main(String[] args){
		
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux

		ControllerRicartDemo controller4 = new ControllerRicartDemo(3,"tcp",configfile);
		controller4.start();
	}

}
