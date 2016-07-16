package RAAlgorithm;

import java.util.HashMap;
import java.util.Map;

class Message {
	String method=null;
	String content=null;
	Map<String,String> avp =null;
	
	Message(String method){
		this.method=method;
		avp= new HashMap<String,String>();
		
	}
	
	public String toString(){
		StringBuilder res = new StringBuilder();
		res.append("METHOD:"+method+";");
		
		for(String str:avp.keySet()){
			res.append(str);
			res.append(":");
			res.append(avp.get(str));
			res.append(";");
		}
		return res.toString();
	}
	
	public void addAVP(String attribute,String value){
		avp.putIfAbsent(attribute, value);
	}
	
	
}
