package lamportAlgorithm;


public class MessageReply extends Message {	
	public MessageReply(){
		setType(MessageFactory.getSingleton().typeReply);
	}	
}
