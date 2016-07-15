package lamportAlgorithm;


public class MessageRequest extends Message {		
	public MessageRequest(){
		setType(MessageFactory.getSingleton().typeRequest);
	}
}
