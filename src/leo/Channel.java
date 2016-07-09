package leo;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import xintong.IreceiveMessage;
import xintong.IsendMessage;

public abstract class Channel implements IreceiveMessage,IsendMessage{

	protected int channelID;
	protected Node local;
	protected Node remote;
	protected Socket socket;
	@Override
	public void receive(String message, int channel) {
		
		
	}
	@Override
	public void send(String message, int channelID) {
		
		
	}
	
}
