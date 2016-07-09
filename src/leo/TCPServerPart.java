package leo;

import java.io.*;
import java.net.*;

import Leo.NodeInfo;
import Leo.SharedData;
import MyMessage.Message;
import MyMessage.MessageNodeID;
import MyMessage.MessageParser;
import MyUtil.ConfigExpert;
import MyUtil.LogWriter;
import com.sun.nio.sctp.*;

public class TCPServerPart 
{
	ServerSocket serverSock = null;	
	NodeInfor serverInfo = null;		
	public TCPServerPart(NodeInfor server){
		serverInfo = server;	
	}
	
	private void init(){
		try
		{
			serverSock = new ServerSocket(serverInfo.port);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}		
	}
	
	public void connectAllChannel(){
		init();
		connectAllClient();
	}	
	
	private void connectAllClient()
	{			
		//Server goes into a permanent loop accepting connections from clients
		LogWriter.getSingle().log("connectAllClient() in SctpServerPart");
		//int numAsServer = 0;//ConfigExpert.getSingleton().getLocalNumAsServer();
		int num = 0;
		while(num < numAsServer)
		{		
			try
			{				
				//Listen for a connection to be made to this socket and accept it
				//The method blocks until a connection is made
				//Returns a new SCTPChannel between the server and client
				Socket socket = serverSock.accept();
				socket.getRemoteSocketAddress();				
				String message = socket.recieve(sctpChannel);
				//Finally the actual message
				System.out.println(message);
				Message msg = MessageParser.getSingleton().parser(message);
				if(msg instanceof MessageNodeID){
					int id = ((MessageNodeID) msg).nodeId;
					if(SharedData.getSingleton().neighberStateHash.containsKey(id)){
						TCPChannel channel = new TCPChannel();
						InOutChannelManager.getSingleton().addOutChannel(ch);						
						num++;
						LogWriter.getSingle().log("connect "+ String.valueOf(num)+" Client in connectAllClient() in SctpServerPart");
						new TCPRecieverThread(channel).start();
					}					
				}				
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}				
		}
		LogWriter.getSingle().log("finished connectAllClient() in SctpServerPart");
	}	

	public static void main(String args[])
	{		
		String serverName= "127.0.0.1";
		int serverPort = 5006;
		NodeInfo serInfo = new NodeInfo(1,serverName,serverPort);		
		TCPServerPart SctpServerObj = new TCPServerPart(serInfo);		
		SctpServerObj.connectAllChannel();
	}

}
