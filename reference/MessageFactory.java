
public class MessageFactory {
	
	static MessageFactory single = new MessageFactory();
	public static MessageFactory getSingleton(){
		return single;
	}
	
	/**
	 * get message object which already has the method part.
	 * path needs to be filled before send
	 * content and timestamp is optional
	 * @param str
	 * @return
	 */
	public Message getMessage(String str){
		String input = str.toLowerCase();
		if(input.equals("request")){
			
			Method method = new Request();
			return new Message(method);
		}
		if(input.equals("response")){

			Method method = new Response();
			return new Message(method);
		}
		if(input.equals("release")){

			Method method = new Release();
			return new Message(method);
		}
		return null;
		
	}

}
