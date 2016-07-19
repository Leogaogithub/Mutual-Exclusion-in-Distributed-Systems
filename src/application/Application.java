package application;

import controllerUnit.MyLogManager;
import controllerUnit.Node;
import shareUtil.AlgorithmFactory;
import shareUtil.IMutualExclusiveStrategy;
import shareUtil.LamportLogicalClockService;

import java.util.Random;

import lamportAlgorithm.TimeInterval;



/**
 * Application: The application is responsible for generating critical section requests and then
executing critical sections on receiving permission from the mutual exclusion service. Model your
application using the following two parameters: inter-request delay, denoted by d, and cs-execution
time, denoted by c. The first parameter d denotes the time elapsed between when a node’s current
request is satisfied and when it generates the next request. The second parameter c denotes the
time a node spends in its critical section. Assume that both d and c are random variables with
exponential probability distribution.
 * @author tongxin
 *
 */
public class Application {
	
	public int interRequestDelay;
	public int csExecutionTimer;
	public Node node;
	public Random rand;
	public IMutualExclusiveStrategy strategy=null;
	public Application(Node node,String algorithmName){
		rand = new Random();
		interRequestDelay=node.meanD;
		csExecutionTimer=node.meanC;	
		this.node=node;
		strategy = AlgorithmFactory.getInstance().getAlgorithm(node, algorithmName);
	}
	
	public void start(){
		
		System.out.println("numofrequest"+node.numRequest);
		for(int i=0;i<node.numRequest;i++){
			int t1=nextInterRequestDelay();
			int t2=nextcsExecutionTimer();
			try {
				
				//System.out.println("nextInterRequestDelay"+t1);
				Thread.sleep(t1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("application enter cs! at "+(i+1)+"times with execution time "+t2+"and interrequest delay"+t1);
			strategy.csEnter();
			
			int enterCSTimeStamp = LamportLogicalClockService.getInstance().getValue();
			long enterCSSystemTime = System.currentTimeMillis();
			try {
				LamportLogicalClockService.getInstance().tick();
				//System.out.println("nextcsExecutionTimer"+t2);			
				Thread.sleep(t2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int leaveCSTimeStamp = LamportLogicalClockService.getInstance().getValue();			
			long leaveCSSystemTime = System.currentTimeMillis();	
			TimeInterval curTimeInterval =  new TimeInterval(enterCSTimeStamp,enterCSSystemTime,leaveCSTimeStamp, leaveCSSystemTime, node.localInfor.nodeId);
			MyLogManager.getSingle().getLog("TimeInterval"+node.localInfor.nodeId).log(curTimeInterval.toString());
			strategy.csLeave();		
			System.out.println("application leaves cs!");
		}		
	}
	
	public int nextInterRequestDelay(){
		
		return (int) (-interRequestDelay*Math.log(rand.nextDouble()));
	}
	
	public int nextcsExecutionTimer(){
		return (int) (-csExecutionTimer*Math.log(rand.nextDouble()));
	}
	

}
