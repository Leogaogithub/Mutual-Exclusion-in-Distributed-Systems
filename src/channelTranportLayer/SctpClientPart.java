package channelTranportLayer;

import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import controllerUnit.Node;
import controllerUnit.NodeInfor;

public class SctpClientPart extends Thread
{	
	NodeInfor serverInfo;
	Node locaNode;
	String serverAddress = "";
	SctpChannel sctpChannel;
	
	public SctpClientPart(Node locaNode, NodeInfor serInfo){
		this.locaNode = locaNode;
		serverInfo = serInfo;				
		serverAddress = serverInfo.hostName;				
	}
	
	public void run() {
		connectChanel();		
	}	
	
	private void connect(){
		SocketAddress socketAddress = new InetSocketAddress(serverAddress,serverInfo.port);
		boolean connected = false;
		while(!connected){
			try {
				sctpChannel = SctpChannel.open();
				sctpChannel.connect(socketAddress);
				connected = true;
			} catch (IOException e) {			
				e.printStackTrace();
				System.out.println(serverInfo.hostName + " is not online now, SctpClientPart will connect in 1000ms");
				//LogWriter.getSingle().write(serverInfo.hostName + " is not online now, SctpClientPart will connect in 1000ms\n");
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
		SCTPChannel ch = new SCTPChannel(serverInfo.nodeId, sctpChannel);
		String msg = "NODEID:"+locaNode.localInfor.nodeId+";";
		ch.send(msg.toString());
		locaNode.addChannel(ch);	
		//LogWriter.getSingle().log("connectChanel() in the SctpClientPart");
		new SctpRecieverThread(ch).start();
	}

}
