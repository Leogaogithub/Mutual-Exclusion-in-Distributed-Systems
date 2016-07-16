package application;

import lamportAlgorithm.LamportAlgorithm;
import shareUtil.IreceiveMessage;
import shareUtil.MessageReceiveService;

public class LeoTestApplication implements IreceiveMessage {
	int nodeid;
	LamportAlgorithm lock = null;
	public LeoTestApplication(int numOfNode, int nodeid){		
		this.nodeid=nodeid;
		lock = new LamportAlgorithm(numOfNode, nodeid);
	}
	
	public void listen(){
		MessageReceiveService.getInstance().register(lock);
		MessageReceiveService.getInstance().register(this);
		System.out.println("register to messagereceiveservices");
	}

	@Override
	public void receive(String message, int channel) {
		System.out.println("node "+nodeid+"receives message "+message+"comes from channel"+channel+"clock  ");
	}
	
	public void startApplication(){
		for(int i = 0; i < 5; i++){
			System.out.println(nodeid +" is not in cs times :" + i);
			sleep(5000);
			lock.csEnter();
			System.out.println(nodeid +" enter  cs times :" + i);
			sleep(3000);
			System.out.println(nodeid +" leave  cs times :" + i);
			lock.csLeave();			
		}
	}
	
	void  sleep(long t){
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
}
