package application;

import java.io.File;

import shareUtil.MessageSenderService;

import controllerUnit.Controller;

public class MySimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nodeId = 0;
		String configfile = "config.txt";
		String algorithmName = "ricart";//rouc,lamport, ricart,
		String transport = "tcp";//tcp,sctp		
		int startD = 20;
		int endD = 30;
		int increaseD = 20;		
		int startC = 10;
		int endC = 20;
		int increaseC = 10;
		int times = 1;
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
		
		if(args.length > 4){
			startD = Integer.parseInt(args[4]);
		}
		if(args.length > 5){
			endD = Integer.parseInt(args[5]);
		}		
		if(args.length > 6){
			increaseD = Integer.parseInt(args[6]);
		}
		
		if(args.length > 7){
			startC = Integer.parseInt(args[7]);
		}
		if(args.length > 8){
			endC = Integer.parseInt(args[8]);
		}		
		if(args.length > 9){
			increaseC = Integer.parseInt(args[9]);
		}		
		if(args.length > 10){
			times = Integer.parseInt(args[10]);
		}		
		String curDirecotry = "";
		if(configfile.contains("/")){
			curDirecotry = configfile.substring(0,configfile.lastIndexOf("/")+1);
		}		
		Controller controller = new Controller(nodeId,configfile,algorithmName,transport);				
		controller.init();
		for(int d = startD; d <= endD; d+=increaseD){
			for(int c = startC; c <= endC; c+=increaseC){
				controller.myNode.meanC = c;
				controller.myNode.meanD = d;				 
				for(int i = 0; i < times; i++){	
					if(i == 0){
						createDir(curDirecotry+"n"+controller.myNode.numNodes+"-d"+d+"-c"+c);
					}
					String curdir = curDirecotry+"n"+controller.myNode.numNodes+"-d"+d+"-c"+c+"/"+i;
					createDir(curdir);
					controller.setDir(curdir+"/");
					controller.start();						
					MessageSenderService.getInstance().boardcastControlMessageng("BYE");
					while(!ControlMessageProcess.getInstance().isOk()){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {							
							e.printStackTrace();
						}						
					}										
				}				
			}			
		}
	}
	
	public static void createDir(String path){
		File f = new File(path);
		if (!f.isDirectory()) {
		  boolean success = f.mkdirs();
		  if (success) {
		    System.out.println("Created path: " + f.getPath());
		  } else {
		    System.out.println("Could not create path: " + f.getPath());
		  }
		} else {
		  System.out.println("Path exists: " + f.getPath());
		}
	}

}
