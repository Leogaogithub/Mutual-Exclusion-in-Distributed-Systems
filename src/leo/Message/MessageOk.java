package leo.Message;

import leo.MessageParser;

public class MessageOk extends Message {

	public MessageOk(){
		type = MessageParser.getSingleton().typeOk;
	}
	
	public String toString(){
		String res = type + ":" + type;		
		return res;
	}

}
