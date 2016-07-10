package application;

import shareUtil.IreceiveMessage;
import shareUtil.MessageReceiveService;

public class TestReceiveApplication implements IreceiveMessage {

	int nodeid;
	public TestReceiveApplication(int nodeid){

		
		this.nodeid=nodeid;
	}
	
	public void listen(){
		MessageReceiveService.getInstance().register(this);
		System.out.println("register to messagereceiveservices");
	}
	

	@Override
	public void receive(String message, int channel) {
		
		System.out.println("node "+nodeid+"receives message "+message+"comes from channel"+channel);
		
	}



}
