package RAAlgorithm;


class MessageFactory {
	
	private static MessageFactory single = new MessageFactory();
	private MessageFactory(){}
	public static MessageFactory getSingleton(){
		return single;
	}
	

	public Message getMessage(String method){
		return new Message(method);
	}
	
	public Message parseMessage(String str){
		Message res=null;
		String[] strArray = str.trim().split(";");
		for(String s:strArray){
			if(s.toLowerCase().startsWith("method:")){
				res = new Message(s.substring(7));
			}
			else if(s.toLowerCase().startsWith("clock:")){
				continue;
			}
			else if(s.toLowerCase().startsWith("scalartime:")){
				continue;
			}
			else if(s.toLowerCase().startsWith("vectorclock:")){
				continue;
			}
			else{
				String[] avp = s.trim().split(":");	
				res.addAVP(avp[0], avp[1]);
			}
		}
		return res;
	}
	
}
