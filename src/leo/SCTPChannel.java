package leo;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;


public class SCTPChannel extends Channel{	
	SctpChannel channel = null;	
	static int MESSAGE_SIZE = 100;	
	public SCTPChannel(int id, SctpChannel ch){
		super(id);
		channel = ch;		
	}
	
	public void send(String message){
		send(channel, message);
	}
	
	public void close(){
		try {
			channel.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public String receive(){
		return receive(channel);
	}
	
	public static void send(SctpChannel sctpChannel, String message){		
		try
		{
			ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);	
			//Before sending messages add additional information about the message
			MessageInfo messageInfo = MessageInfo.createOutgoing(null,0);			
			//convert the string message into bytes and put it in the byte buffer
			byteBuffer.put(message.getBytes());
			//Reset a pointer to point to the start of buffer 
			byteBuffer.flip();
			sctpChannel.send(byteBuffer,messageInfo);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static String receive(SctpChannel sctpChannel){
		String message = null;		
		try
		{
			ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
			MessageInfo messageInfo = sctpChannel.receive(byteBuffer,null,null);
			message = UtilityTool.byteToString(byteBuffer, MESSAGE_SIZE);			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return message;
	}

}
