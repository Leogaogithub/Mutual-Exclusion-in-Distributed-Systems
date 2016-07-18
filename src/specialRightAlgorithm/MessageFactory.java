package specialRightAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yifan on 7/18/16.
 */
public class MessageFactory {
    private static final String typeApp = "APP";
    private static final String typeRequest = "REQUEST";
    private static final String typeRelease = "RELEASE";

    private static MessageFactory singleton = new MessageFactory();
    private MessageFactory(){}
    public static MessageFactory getFactory(){
        return singleton;
    }

    public static Message createMessage(String type){
        if(type.equals(typeApp)) return new Message(){{setType(typeApp);}};
        if(type.equals(typeRelease)) return new Message(){{setType(typeRelease);}};
        if(type.equals(typeRequest)) return new Message(){{setType(typeRequest);}};
        return null;
    }

    public static Message createMessage(Map<String, String> attrValues){
        return new Message(){{setAttrValues(new HashMap<>(attrValues));}};
    }
}
