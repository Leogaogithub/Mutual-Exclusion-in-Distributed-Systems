package demo;


import controllerUnit.ControllerRicartDemo;


public class Host1 {
	public static void main(String[] args){
		
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/config.txt";  //linux
		int nodeId = 0;
		String prefixName = configfile.substring(0, configfile.length()-4)+"-" + String.valueOf(nodeId);

		//LogWriter.getSingle().open(prefixName+".log");
		//LogWriter.getSingle().clear();		
		//OutputWriter.getSingle().open(prefixName+".out");
		//OutputWriter.getSingle().clear();		
		ControllerRicartDemo controller1 = new ControllerRicartDemo(0,"sctp",configfile);

		controller1.start();
	}

}
