package xintong;

public class testReceiveApplication implements IreceiveMessage {
	String msg;
	int channel;
	int nodeid;
	public testReceiveApplication(int nodeid,String msg,int channel){
		this.msg=msg;
		this.channel=channel;
		MessageReceiveService.getInstance().register(this);
		this.nodeid=nodeid;
	}
	
	
	

	@Override
	public void receive(String message, int channel) {
		
		System.out.println("node "+nodeid+"receives message "+message+"comes from channel"+channel);
		
	}

}
