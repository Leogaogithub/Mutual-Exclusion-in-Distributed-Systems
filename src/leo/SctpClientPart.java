package leo;

import java.io.*;

import java.net.*;


import com.sun.nio.sctp.*;
import leo.Message.*;

public class SctpClientPart extends Thread
{	
	NodeInfor serverInfo;
	Node locaNode;
	//int localPort = 5002;	
	String serverAddress = "";
	SctpChannel sctpChannel;
	
	public SctpClientPart(Node locaNode, NodeInfor serInfo){
		this.locaNode = locaNode;
		serverInfo = serInfo;				
		serverAddress = serverInfo.hostName;
		//serverAddress = "127.0.0.1";//delete		
	}
	
	public void run() {
		connectChanel();		
	}	
	
	private void connect(){
		//Create a socket address for  server at net01 at port 5000
		SocketAddress socketAddress = new InetSocketAddress(serverAddress,serverInfo.port);
		//Open a channel. NOT SERVER CHANNEL
		boolean connected = false;
		while(!connected){
			try {
				sctpChannel = SctpChannel.open();
				//Bind the channel's socket to a local port. Again this is not a server bind
				//sctpChannel.bind(new InetSocketAddress(localPort));
				//Connect the channel's socket to  the remote server
				sctpChannel.connect(socketAddress);
				connected = true;
			} catch (IOException e) {			
				e.printStackTrace();
				System.out.println(serverInfo.hostName + " is not online now, SctpClientPart will connect in 1000ms");
				LogWriter.getSingle().write(serverInfo.hostName + " is not online now, SctpClientPart will connect in 1000ms\n");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {					
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void connectChanel(){		
		connect();
		//SharedData.getSingleton().updateIsConnected(serverInfo.nodeId);
		
		SCTPChannel ch = new SCTPChannel(serverInfo.nodeId, sctpChannel);
		Message msg = MessageFactory.getSingleton().getMessageNodeID(locaNode.localInfor.nodeId);
		ch.send(msg.toString());
		ChannelManager.getSingleton().addChannel(ch);	
		LogWriter.getSingle().log("connectChanel() in the SctpClientPart");
		new SctpRecieverThread(ch).start();
	}

}
