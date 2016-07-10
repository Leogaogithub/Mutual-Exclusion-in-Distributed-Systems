package leo;

import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import leo.Message.*;

public class SctpServerPart 
{
	SctpServerChannel sctpServerChannel = null;
	Node localNode = null;		
	public SctpServerPart(Node myNode){
		localNode = myNode;	
	}
	
	private void init(){
		try
		{
			//Open a server channel
			sctpServerChannel = SctpServerChannel.open();
			//Create a socket addess in the current machine at port 5000
			InetSocketAddress serverAddr = new InetSocketAddress(localNode.localInfor.port);
			//Bind the channel's socket to the server in the current machine at port 5000
			sctpServerChannel.bind(serverAddr);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}		
	}
	
	public void connectAllChannelFromSeverToClient(){
		init();
		connectAllClient();
	}	
	
	private void connectAllClient()
	{			
		//Server goes into a permanent loop accepting connections from clients
		LogWriter.getSingle().log("connectAllClient() in SctpServerPart");
		
		for(NodeInfor nb: localNode.neighbors.values()){
			if(UtilityTool.preIsClient(localNode.localInfor.nodeId, nb.nodeId)){				
				continue;
			}
			boolean connected = false;
			while(!connected)
			{		
				try
				{
					SctpChannel sctpChannel = sctpServerChannel.accept();
					String message = SCTPChannel.receive(sctpChannel);				
					System.out.println(message);
					Message msg = MessageParser.getSingleton().parser(message);
					if(msg instanceof MessageNodeID){						
						SCTPChannel ch = new SCTPChannel(((MessageNodeID) msg).nodeId, sctpChannel);
						ChannelManager.getSingleton().addChannel(ch);
						connected = true;
						LogWriter.getSingle().log("connect nodeID: ("+ String.valueOf(ch.channelID)+") Client in connectAllClient() in SctpServerPart");
						new SctpRecieverThread(ch).start();
					}							
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}		
			}						
		}
		LogWriter.getSingle().log("finished connectAllClient() in SctpServerPart");
	}	

	public static void main(String args[])
	{	
		Paser.getSingleton().setLocalNodeId(1);
		Node mynode = Paser.getSingleton().parseFile("config.txt");
		SctpServerPart SctpServerObj = new SctpServerPart(mynode);		
		SctpServerObj.connectAllChannel();
	}

}
