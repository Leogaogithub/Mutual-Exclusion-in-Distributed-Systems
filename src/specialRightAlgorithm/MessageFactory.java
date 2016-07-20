package specialRightAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yifan on 7/18/16.
 */
public class MessageFactory {
    private static final String typeApp = "APP";
    private static final String typeRequest = "REQUEST";
    private static final String typeReply = "REPLY";

    public static Message createMessage(String type, int from, int to){
        if(type.equals(typeApp)){
        	Message msg = new Message();
        	msg.setType(typeApp);
        	msg.setFrom(from);
        	msg.setTo(to);        	
        	return msg;
        }
        if(type.equals(typeReply)){
        	Message msg = new Message();
        	msg.setType(typeReply);
        	msg.setFrom(from);
        	msg.setTo(to);        	
        	return msg;
        	
        }
        if(type.equals(typeRequest)){
        	Message msg = new Message();
        	msg.setType(typeRequest);
        	msg.setFrom(from);
        	msg.setTo(to);        	
        	return msg;
        }
        return null;
    }

    public static Message createMessage(Map<String, String> attrValues){
    	Message msg = new Message();
    	msg.setAttrValues(new HashMap<>(attrValues));    	       	
    	return msg;        
    }
}
