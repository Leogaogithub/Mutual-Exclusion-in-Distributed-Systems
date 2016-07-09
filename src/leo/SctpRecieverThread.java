package leo;

public class SctpRecieverThread extends Thread {
	Channel channel = null;
	public SctpRecieverThread(Channel channel){
		this.channel = channel;		
	}
	
	private String getSuffix(){
		String res = " ";
		res += " from "+ String.valueOf(channel.channelID);
		return res;
	}

	public void run() {
		boolean runed = true;
		while(runed){			
			String message = channel.receive();	
			handler(message);					
		}
	}	
	
	public void handler(String msg){		
		LogWriter.getSingle().log(msg.toString()+getSuffix());				
	}
	
	public static void main(String arg[]){
		String mm = "a;b;c";
		String c[] = mm.split(";");
		System.out.println(mm);		
	    
	}
}
