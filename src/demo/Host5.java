package demo;

import controllerUnit.ControllerRicartDemo;


public class Host5 {
	public static void main(String[] args){
		
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux
		ControllerRicartDemo controller5 = new ControllerRicartDemo(4,"tcp",configfile);
		controller5.start();
	}

}
