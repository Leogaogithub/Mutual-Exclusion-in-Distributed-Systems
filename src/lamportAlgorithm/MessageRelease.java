package lamportAlgorithm;


public class MessageRelease extends Message {	
	public MessageRelease(){
		setType(MessageFactory.getSingleton().typeRelease);
	}	
}
