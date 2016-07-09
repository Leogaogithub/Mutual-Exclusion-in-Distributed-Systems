package leo;

import java.io.*;
import java.net.*;
import java.util.Set;

import Leo.NodeInfo;
import Leo.SharedData;
import MyChannel.InOutChannel;
import MyMessage.Message;
import MyMessage.MessageNodeID;
import MyMessage.MessageParser;
import MyUtil.ConfigExpert;
import MyUtil.LogWriter;
import com.sun.nio.sctp.*;

public class SctpServerPart 
{
	SctpServerChannel sctpServerChannel = null;
	NodeInfor serverInfo = null;		
	public SctpServerPart(NodeInfor server){
		serverInfo = server;	
	}
	
	private void init(){
		try
		{
			//Open a server channel
			sctpServerChannel = SctpServerChannel.open();
			//Create a socket addess in the current machine at port 5000
			InetSocketAddress serverAddr = new InetSocketAddress(serverInfo.port);
			//Bind the channel's socket to the server in the current machine at port 5000
			sctpServerChannel.bind(serverAddr);
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
		int numAsServer = ConfigExpert.getSingleton().getLocalNumAsServer();
		int num = 0;
		while(num < numAsServer)
		{		
			try
			{				
				//Listen for a connection to be made to this socket and accept it
				//The method blocks until a connection is made
				//Returns a new SCTPChannel between the server and client
				SctpChannel sctpChannel = sctpServerChannel.accept();
				String message = SCTPChannel.recieve(sctpChannel);				
				System.out.println(message);
				Message msg = MessageParser.getSingleton().parser(message);	
				sctpChannel.
				if(SharedData.getSingleton().neighberStateHash.containsKey(id)){
						InOutChannel ch = new InOutChannel(id, sctpChannel);
						InOutChannelManager.getSingleton().addOutChannel(ch);
						SharedData.getSingleton().updateIsConnected(id);
						num++;
						LogWriter.getSingle().log("connect "+ String.valueOf(num)+" Client in connectAllClient() in SctpServerPart");
						new SctpRecieverThread(ch).start();
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
		SctpServerPart SctpServerObj = new SctpServerPart(serInfo);		
		SctpServerObj.connectAllChannel();
	}

}
