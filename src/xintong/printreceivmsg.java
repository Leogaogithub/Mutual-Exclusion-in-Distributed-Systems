package xintong;

public class printreceivmsg implements IreceiveMessage{

	@Override
	public void receive(String message, int channel) {
		System.out.println("this object received a message from channel"+channel);
		
	}
	
	

}
