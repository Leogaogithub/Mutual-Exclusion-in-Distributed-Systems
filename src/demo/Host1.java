package demo;


import controllerUnit.ControllerRicartDemo;


public class Host1 {
	public static void main(String[] args){
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux
		int nodeId = 0;
		String prefixName = configfile.substring(0, configfile.length()-4)+"-" + String.valueOf(nodeId);

		ControllerRicartDemo controller1 = new ControllerRicartDemo(0,"tcp",configfile);

		controller1.start();
	}

}
