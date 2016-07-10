package channelTranportLayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPChannel extends Channel {
	
	public TCPChannel(int channelID) {
		super(channelID);
	}

	public int remoteNodeID;
	public Socket socket;
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public void send(String msg){
	
		PrintWriter outToServer=null;
		try {
			outToServer = new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outToServer.println(msg);
	}

	@Override
	public String receive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	
		
	
}
