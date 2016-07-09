
public class MethodFactory {
	
	static MethodFactory single = new MethodFactory();
	public static MethodFactory getSingleton(){
		return single;
	}
	public Method getMethod(String str){
		String input = str.toLowerCase();
		if(input.equals("request")){
			
			Method method = new Request();
			return method;
		}
		if(input.equals("response")){

			Method method = new Response();
			return method;
		}
		if(input.equals("release")){

			Method method = new Release();
			return method;
		}
		return null;
		
	}

}
