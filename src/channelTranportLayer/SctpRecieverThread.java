package channelTranportLayer;

import controllerUnit.LogWriter;
import shareUtil.MessageReceiveService;

public class SctpRecieverThread extends Thread {
	Channel channel = null;
	public SctpRecieverThread(Channel channel){
		this.channel = channel;		
	}

	public void run() {
		boolean runed = true;
		while(runed){			
			String message = channel.receive();	
			if(!message.equalsIgnoreCase("")){
				MessageReceiveService.getInstance().receive(message, channel.channelID);
			}else{
				runed = false;
				LogWriter.getSingle().log("SctpRecieverThread(" + channel.channelID +")exit");
			}
			//handler(message);					
		}
	}	
	
	public void handler(String msg){
		System.out.println(msg.toString());
		LogWriter.getSingle().log(msg.toString());				
	}
}
