package controllerUnit;

import java.util.HashMap;
import java.util.Map;

public class MyLogManager {

	private static MyLogManager instance =  new MyLogManager();
	String curDirectory = "";
	
	Map<String, MyWriter> logMap = new HashMap<String, MyWriter>();
	public  static MyLogManager getSingle(){
		return instance;
	}
	
	public void setDir(String curdir){
		curDirectory = curdir;
	}
	
	public MyWriter getLog(String name){
		MyWriter log = null;
		if(!logMap.containsKey(curDirectory+name)){
			log = new MyWriter();
			logMap.put(curDirectory+name, log);
			log.open(curDirectory+name);
			log.clear();
			return log;
		}
		return logMap.get(curDirectory+name);		
	}
}
