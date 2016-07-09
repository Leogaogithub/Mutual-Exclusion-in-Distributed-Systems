package leo.Message;

public class MessageOk extends Message {

	public MessageOk(){
		type = MessageParser.getSingleton().typeOk;
	}
	
	public String toString(){
		String res = type;
		return res;
	}

}
