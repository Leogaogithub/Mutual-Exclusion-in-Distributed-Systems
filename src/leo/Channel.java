package leo;
import java.net.Socket;


public abstract class Channel {

	public Controller controller;
	public int channelID;
	public Node local;
	public Node remote;
	public Socket socket;
	

	public void receive(String message, int channel) {
		
		
	}

	public void send(String message, int channelID) {
		
		
	}
	
}
