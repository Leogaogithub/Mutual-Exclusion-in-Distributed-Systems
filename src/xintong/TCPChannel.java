package xintong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import leo.Channel;
import leo.Node;

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
		try {
			PrintWriter outToServer = new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
