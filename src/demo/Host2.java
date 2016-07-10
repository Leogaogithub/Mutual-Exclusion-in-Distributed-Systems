package demo;

import leo.Controller;

public class Host2 {
	public static void main(String[] args){
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux
		Controller controller2 = new Controller(1,"tcp",configfile);
		controller2.start();
	}

}
