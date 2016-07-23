package demo;


import controllerUnit.Controller;


public class CopyOfHost2 {
	public static void main(String[] args){		
		//String currentDir = System.getProperty("user.dir");
		//System.out.println(currentDir);		
		int nodeId = 2;
		String configfile = "config.txt";
		String algorithmName = "ricart";//rouc,lamport, ricart,
		String transport = "tcp";//tcp,sctp
		if(args.length > 0){
			nodeId = Integer.parseInt(args[0]);
		}
		if(args.length > 1){
			configfile = args[1];
		}		
		if(args.length > 2){
			algorithmName = args[2];
		}
		if(args.length > 3){
			transport = args[3];
		}
		//configfile="/home/013/l/lx/lxg151530/aos/config.txt";
		String curDir = "";
		if(configfile.contains("/")){
			curDir = configfile.substring(0,configfile.lastIndexOf("/")+1);
		}else{
			curDir = "./";
		}
		Controller controller = new Controller(nodeId,configfile,algorithmName,transport);
		controller.setDir(curDir);
		controller.init();
		controller.start();
		System.out.println(nodeId+ ":demo is finished!!!");
	}

}
