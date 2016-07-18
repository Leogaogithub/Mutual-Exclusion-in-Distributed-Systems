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
        if(type.equals(typeApp)) return new Message(){{setType(typeApp);setFrom(from);setTo(to);}};
        if(type.equals(typeReply)) return new Message(){{setType(typeReply);setFrom(from);setTo(to);}};
        if(type.equals(typeRequest)) return new Message(){{setType(typeRequest);setFrom(from);setTo(to);}};
        return null;
    }

    public static Message createMessage(Map<String, String> attrValues){
        return new Message(){{setAttrValues(new HashMap<>(attrValues));}};
    }
}
