package specialRightAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yifan on 7/18/16.
 */
public class MessageParser {
    private MessageParser(){}
    private static MessageParser singleton= new MessageParser();
    public static MessageParser getParser(){
        return singleton;
    }

    /**
     * Parse a String object to a Message object.
     * @param msg
     * @return
     */
    public static Message parse(String msg){
        String[] attrs = msg.split(";");
        Map<String, String> tmp = new HashMap<>();
        for(String one : attrs){
            String[] pair = one.split(":");
            tmp.put(pair[0], pair[1]);
        }
        return new Message(){{setAttrValues(tmp);}};
    }
}
