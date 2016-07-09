package leo;

import java.io.*;
import java.net.*;

public class TCPClientPart 
{
	public int MESSAGE_SIZE = 0;
	//Buffer to hold messages in byte format
	NodeInfor serverInfo;	
	String serverAddress = "";
	TCPChannel channel;
	Socket socket;	
	
	public TCPClientPart(NodeInfor serInfo){		
		serverInfo = serInfo;				
		serverAddress = serverInfo.hostName+".utdallas.edu";
		//serverAddress = "127.0.0.1";//delete		
	}
	
	private void connect(){
		//Create a socket address for  server at net01 at port 5000
		SocketAddress socketAddress = new InetSocketAddress(serverAddress,serverInfo.port);
		//Open a channel. NOT SERVER CHANNEL
		boolean connected = false;
		while(!connected){
			try {						    
			    // create a socket with a timeout
			    try
			    {			  
			      // create a socket
			      socket = new Socket();			  
			      // this method will block no more than timeout ms.
			      int timeoutInMs = 2*1000;   // 10 seconds
			      socket.connect(socketAddress, timeoutInMs);			      
			    } 
			    catch (SocketTimeoutException ste) 
			    {
			      System.err.println("Timed out waiting for the socket.");
			      ste.printStackTrace();
			      throw ste;
			    }
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
		channel = new TCPChannel(socket, serverInfo.nodeId);
		//SharedData.getSingleton().updateIsConnected(serverInfo.nodeId);
		LogWriter.getSingle().log("connectChanel() in the SctpClientPart");
		//InOutChannelManager.getSingleton().addOutChannel(ch);
		new TCPRecieverThread(ch).start();
		//int id = ConfigExpert.getSingleton().getLocalNodeId();
		//Message msg = MessageFactory.getSingleton().getMessageNodeID(id);				
		//send(msg.toString());
	}	
	
	public void close(){		
		channel.close();
	}
	
	public static void main(String args[])
	{
		String message = "hello world, this is from sctpclient part";
		String serverName= "127.0.0.1";
		int serverPort = 5006;
		NodeInfor serInfo = new NodeInfor(1,serverName,serverPort);
		SctpClientPart SctpClientObj = new SctpClientPart(serInfo);
		SctpClientObj.connectChanel();	
		int i = 0;
		boolean active = true;
		while(active){			
			message = String.valueOf(i);			
			SctpClientObj.send(message);
			i= i+1;
			System.out.println(i);			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {				
				e.printStackTrace();
				SctpClientObj.close();
				active = false;
			}
		}
		
	}
}
