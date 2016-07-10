package demo;

import leo.Controller;

public class Host3 {
	public static void main(String[] args){
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux
		Controller controller1 = new Controller(2,"tcp",configfile);
		controller1.start();
	}

}
