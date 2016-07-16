package lamportAlgorithm;

import java.util.Map;


public class MessageFactory {
	public String typeApp = "APP";
	public String typeRequest = "REQUEST";
	public String typeReply = "REPLY";
	public String typeRelease = "RELEASE";
	public String typeLable = "TYPE";
	
	static MessageFactory single = new MessageFactory();
	public static MessageFactory getSingleton(){
		return single;
	}
	
	public Message createMessage(String type){
		Message msg = null;
		if(type.equals(typeRequest)) msg = new MessageRequest();
		if(type.equals(typeRelease)) msg = new MessageRelease();
		if(type.equals(typeReply)) msg = new MessageReply();
		return msg;
	}
	
	public Message getMessage(Map<String, String> attributValues){		
		if(!attributValues.containsKey(typeLable)) return null;
		String msgType = attributValues.get(typeLable);
		Message msg  = createMessage(msgType);
		msg.setAttributValues(attributValues);
		return msg;
	}
}
