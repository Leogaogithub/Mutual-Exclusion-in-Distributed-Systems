package specialRightAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yifan on 7/18/16.
 */
public class Message {
    private Map<String, String> attrValues;

    public Message(){
        attrValues = new HashMap<>();
    }

    public void setAttrValues(Map<String, String> attrValues) {
        this.attrValues = attrValues;
    }

    public String getOneAttr(String attr){
        return attrValues.get(attr);
    }

    public void addOrSetAttr(String attr, String val){
        attrValues.put(attr, val);
    }

    public void setType(String t){
        addOrSetAttr("TYPE", t);
    }
    public void setFrom(int id){
        addOrSetAttr("FROM", String.valueOf(id));
    }
    public void setTo(int id){
        addOrSetAttr("TO", String.valueOf(id));
    }
    public void setBody(String body){
        addOrSetAttr("BODY", body);
    }

    public String getType(){
        return getOneAttr("TYPE");
    }
    public int getFrom(){
        return Integer.parseInt(getOneAttr("FROM"));
    }
    public int getTo(){
        return Integer.parseInt(getOneAttr("TO"));
    }
    public String getBody(){
        return getOneAttr("BODY");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : attrValues.entrySet()){
            sb.append(entry.getKey()).append(':').append(entry.getValue()).append(';');
        }
        return sb.toString();
    }
}
