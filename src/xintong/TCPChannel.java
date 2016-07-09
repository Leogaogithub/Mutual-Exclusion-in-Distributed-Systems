package xintong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import leo.Channel;
import leo.Node;

public class TCPChannel extends Channel {
	
	public int remoteNodeID;
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public TCPChannel(Node local,int remoteID){
		super.local=local;
		super.channelID=remote.localInfor.nodeId;
		this.remoteNodeID=remoteID;
	}
	
	public void send(String msg){
		try {
			PrintWriter outToServer = new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
		
	
}
