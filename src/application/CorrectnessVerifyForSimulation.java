package application;
import controllerUnit.MyLogManager;
// enterCS.start enterCS.systemtime leaveCS.end leaveCS.systemtime 
public class CorrectnessVerifyForSimulation {	
	public static void main(String[] args) {		
		int numNodes = 3;
		String curDirecotry = "./"; 
		int startD = 20;
		int endD = 30;
		int increaseD = 10;		
		int startC = 10;
		int endC = 20;
		int increaseC = 10;
		int times = 1;
		if(args.length > 0){
			numNodes = Integer.parseInt(args[0]);
		}
		if(args.length > 1){
			curDirecotry = args[1];			
			if(!curDirecotry.equals("")&& curDirecotry.endsWith("/"))
				curDirecotry = curDirecotry+"/";
		}		
		if(args.length > 2){
			startD = Integer.parseInt(args[2]);
		}
		if(args.length > 3){
			endD = Integer.parseInt(args[3]);
		}		
		if(args.length > 4){
			increaseD = Integer.parseInt(args[4]);
		}
		
		if(args.length > 5){
			startC = Integer.parseInt(args[5]);
		}
		if(args.length > 6){
			endC = Integer.parseInt(args[6]);
		}		
		if(args.length > 7){
			increaseC = Integer.parseInt(args[7]);
		}		
		if(args.length > 8){
			times = Integer.parseInt(args[8]);
		}
		
		for(int d = startD; d <= endD; d+=increaseD){
			for(int c = startC; c <= endC; c+=increaseC){				 
				for(int i = 0; i < times; i++){						
					String curdir = curDirecotry+"n"+numNodes+"-d"+d+"-c"+c+"/"+i + "/";
					MyLogManager.getSingle().setDir(curdir);
					CorrectnessVerify test = new CorrectnessVerify(curdir+"TimeInterval", numNodes);
					boolean result = test.verifyResult();					
					MyLogManager.getSingle().getLog("verifyResult").log(String.valueOf(result));
					test.caculate();
					System.out.println(curdir + " verifyResult: " + result);
					System.out.println("CorrectnessTest finished");															
				}				
			}			
		}
	}	
}
