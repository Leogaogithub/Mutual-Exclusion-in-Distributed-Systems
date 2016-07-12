package application;

import shareUtil.IreceiveMessage;
import shareUtil.IreceiveMessageWithClock;
import shareUtil.MessageReceiveService;

public class TestReceiveApplicationWithClock implements IreceiveMessageWithClock {

	int nodeid;
	public TestReceiveApplicationWithClock(int nodeid){

		
		this.nodeid=nodeid;
	}
	
	public void listen(){
		MessageReceiveService.getInstance().registerWithClock(this);
		System.out.println("register to messagereceiveservices");
	}


	@Override
	public void receive(String message, int channel, long milliseconds) {
		System.out.println("node "+nodeid+"receives message "+message+"comes from channel"+channel+"clock"+milliseconds);
		
	}



}
