package leo.Message;

import Leo.SharedData;

public class MessageFactory {
	
	static MessageFactory single = new MessageFactory();
	public static MessageFactory getSingleton(){
		return single;
	}
	
	MessageOk okmsg = new MessageOk();
	MessageMarker markerMsg = new MessageMarker();	
	MessageFinish finishMsg = new MessageFinish();

	public Message getMessageOk(){
		return okmsg;
	}
	
	public Message getMessageFinish(){
		return finishMsg;
	}
	
	public Message getMessageMarker(){
		return markerMsg;
	}
	
	public Message getMessageState(boolean isPassive){
		MessageState stmsg = new MessageState(isPassive);
		return stmsg;
	}
	
	public Message getMessageApplication(String timeStamp,String content){
		MessageApplication appmsg = new MessageApplication(timeStamp,content);
		return appmsg;
	}
	
	String appendSuffix(String content, int fromId, int toId){
		content += " from "+ String.valueOf(fromId)+" to "+ String.valueOf(toId);
		return content;
	}
	
	public Message getMessageApplication(String timeStamp, String content, int fromId, int toId){		
		content = appendSuffix(content, fromId, toId);
		MessageApplication appmsg = new MessageApplication(timeStamp ,content);
		return appmsg;
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
