package RAAlgorithm;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import controllerUnit.Node;
import shareUtil.IMutualExclusiveStrategy;
import shareUtil.IreceiveMessageWithClock;
import shareUtil.MessageReceiveService;

public class Ricart implements IMutualExclusiveStrategy,IreceiveMessageWithClock{
	
	public int[] requestQueue;
	public Node node;
	public LamportClock clock;
	public int requestSentClock;
	public Status currentStatus;
	private Queue<Integer> pendingQueue;
	private int numOfOk;
	
	public Ricart(Node node){
		this.node=node;
		requestQueue=new int[node.numNodes];
		this.clock=new LamportClock();
		pendingQueue=new PriorityQueue<>();
		currentStatus=new NotEnter(this);
		MessageReceiveService.getInstance().registerWithClock(this);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public synchronized void addToRequestQueue(int processid){
		this.pendingQueue.add(processid);
	}
	

	@Override
	
	public synchronized void csEnter() {
		this.currentStatus=new EnteringCS(this);
		clock.update();
		requestSentClock=clock.getClock();
		this.numOfOk=0;
		this.currentStatus.execute();
		while(this.getNumOfOk()<(this.node.numNodes-1)){
			System.out.println("current received ok:"+getNumOfOk());
			try {
				this.wait();	//can i do better?
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public Queue<Integer> getQueue(){
		return this.pendingQueue;
	}
	
	@Override
	public void csLeave() {
		this.currentStatus=new NotEnter(this);
		this.currentStatus.execute();
		
	}


	

	@Override
	public void receive(String message, int channel, long milliseconds) {
		Message receivedMsg = MessageFactory.getSingleton().parseMessage(message);

		this.currentStatus.receive(receivedMsg, channel, milliseconds);
	
		
	}




	public int getNumOfOk() {
		return numOfOk;
	}




	public synchronized void addNumOfOk() {
		this.numOfOk ++;
	}



	

}
