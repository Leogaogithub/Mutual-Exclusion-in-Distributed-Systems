package leo;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import xintong.IreceiveMessage;
import xintong.IsendMessage;

public abstract class Channel implements IreceiveMessage,IsendMessage{

	public int channelID;
	public Node local;
	public Node remote;
	public Socket socket;
	@Override
	public void receive(String message, int channel) {
		
		
	}
	@Override
	public void send(String message, int channelID) {
		
		
	}
	
}
