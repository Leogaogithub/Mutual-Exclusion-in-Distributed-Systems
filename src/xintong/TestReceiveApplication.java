package xintong;

public class TestReceiveApplication implements IreceiveMessage {

	int nodeid;
	public TestReceiveApplication(int nodeid){

		MessageReceiveService.getInstance().register(this);
		this.nodeid=nodeid;
	}
	
	
	

	@Override
	public void receive(String message, int channel) {
		
		System.out.println("node "+nodeid+"receives message "+message+"comes from channel"+channel);
		
	}



}
