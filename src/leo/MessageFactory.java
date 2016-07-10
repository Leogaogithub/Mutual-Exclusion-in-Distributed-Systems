package leo;

import leo.Message.Message;
import leo.Message.MessageNodeID;
import leo.Message.MessageOk;

public class MessageFactory {
	
	static MessageFactory single = new MessageFactory();
	public static MessageFactory getSingleton(){
		return single;
	}
	
	MessageOk okmsg = new MessageOk();

	public Message getMessageOk(){
		return okmsg;
	}
	
	String appendSuffix(String content, int fromId, int toId){
		content += " from "+ String.valueOf(fromId)+" to "+ String.valueOf(toId);
		return content;
	}
	

	
	public Message getMessageNodeID(int id){
		MessageNodeID msg = new MessageNodeID(id);
		return msg;
	}
	
	public static void main(String[] args) {
		String s = "1";
		System.out.println(s.startsWith("ljljl"));

	}

}
