package leo;

import leo.Message.Message;

public class MessageParser {
	public String typeOk = "OK";
	public String typeApp = "APP";
	public String typeNodeId = "NODEID";
	
	static MessageParser single = new MessageParser();
	public static MessageParser getSingleton(){
		return single;
	}	
	private MessageParser(){
		
	}	
	
	public Message parser(String msg){
		String attributes[] = msg.split(";");
		if(attributes[0].startsWith(typeOk)){			
			return MessageFactory.getSingleton().getMessageOk();			
		}else if(attributes[0].startsWith(typeNodeId)){
			String values[] = attributes[0].split(":");			
			int id = Integer.parseInt(values[1]);
			return MessageFactory.getSingleton().getMessageNodeID(id);
		}else{
			return null;
		}
	}
}
