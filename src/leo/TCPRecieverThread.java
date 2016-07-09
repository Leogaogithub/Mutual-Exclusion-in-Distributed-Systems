package leo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;

import Leo.CLSnapshot;
import Leo.MAP;
import Leo.SharedData;
import MyMessage.Message;
import MyMessage.MessageApplication;
import MyMessage.MessageFinish;
import MyMessage.MessageMarker;
import MyMessage.MessageOk;
import MyMessage.MessageParser;
import MyMessage.MessageState;
import MyUtil.ConfigExpert;
import MyUtil.LogWriter;
import MyUtil.UtilityTool;
import com.sun.nio.sctp.MessageInfo;


public class TCPRecieverThread extends Thread {
	
	public int MESSAGE_SIZE = 0;	
	TCPChannel channel = null;
	//Buffer to hold messages in byte format		

	public TCPRecieverThread(TCPChannel channel){
		this.channel = channel;		
	}
	
	private String getSuffix(){
		String res = " ";
		res += " from "+ String.valueOf(channel.channelID);
		return res;
	}
	
	/**
	 * @param args
	 */
	public void run(){		
		boolean runed = true;
		while(runed){			
			String message;											
			message = channel.receive();					
			//Message msg = MessageParser.getSingleton().parser(message);
				if(message == null){
					System.out.println("loglog------------------\n"); continue;
				}
				handler(message);
			}					
		}
	}	
	
	public void handler(String message){
		
		LogWriter.getSingle().log(msg.toString()+getSuffix());	
		if(msg instanceof MessageOk){
			SharedData.getSingleton().updateIsSendOk(inOutChannel.outId);
			if(SharedData.getSingleton().clientsCnctAllserver 
			    && SharedData.getSingleton().serverCnctAllClient 
				&& SharedData.getSingleton().allServerSentMeOk){
				MAP.getSingleton().sendOktoAllClient();
			}
		}else {
			if(SharedData.getSingleton().clientsCnctAllserver 
				    && SharedData.getSingleton().serverCnctAllClient 
					&& SharedData.getSingleton().allServerSentMeOk)
			{				
				if(msg instanceof MessageApplication){				
					CLSnapshot.getSingleton().handleReciveApplication((MessageApplication) msg, inOutChannel.outId);					
				}else if(msg instanceof MessageMarker){				
					CLSnapshot.getSingleton().handleReciveMarker((MessageMarker) msg, inOutChannel.outId);						
				}else if(msg instanceof MessageState){				
					CLSnapshot.getSingleton().handleReciveState((MessageState) msg, inOutChannel.outId);					
				}else if(msg instanceof MessageFinish){				
					CLSnapshot.getSingleton().handleReciveFinish((MessageFinish) msg);				
				}
				else{
					
				}
			}
			
		}
			
	}
}
