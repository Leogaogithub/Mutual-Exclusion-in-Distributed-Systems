package lamportAlgorithm;

import java.util.HashMap;
import java.util.Map;


public class MessageParser {	
	static MessageParser single = new MessageParser();
	public static MessageParser getSingleton(){
		return single;
	}	
	private MessageParser(){
		
	}	
	
	public Message parser(String msgstr){
		Message msg = null;
		String attributes[] = msgstr.split(";");
		Map<String, String> attributValues = new HashMap<String, String>();
		for(String attri: attributes){
			String keyVal[] = attri.split(":");
			attributValues.put(keyVal[0], keyVal[1]);
		}
		String typeLable = MessageFactory.getSingleton().typeLable;
		if(!attributValues.containsKey(typeLable)) return null;		
		msg = MessageFactory.getSingleton().getMessage(attributValues);
		return msg;		
	}
}
