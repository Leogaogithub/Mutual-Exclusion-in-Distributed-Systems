package controllerUnit;

import java.util.HashMap;
import java.util.Map;

public class MyLogManager {

	private static MyLogManager instance =  new MyLogManager();	
	
	Map<String, MyWriter> logMap = new HashMap<String, MyWriter>();
	public  static MyLogManager getSingle(){
		return instance;
	}
	
	public MyWriter getLog(String name){
		MyWriter log = null;
		if(!logMap.containsKey(name)){
			log = new MyWriter();
			logMap.put(name, log);
			log.open(name);
			log.clear();
			return log;
		}
		return logMap.get(name);		
	}
}
