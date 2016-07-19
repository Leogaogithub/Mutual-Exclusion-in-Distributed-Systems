package channelTranportLayer;

import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import controllerUnit.Node;
import controllerUnit.NodeInfor;
import controllerUnit.UtilityTool;

public class SctpServerPart extends Thread
{
	SctpServerChannel sctpServerChannel = null;
	Node localNode = null;		
	public SctpServerPart(Node myNode){
		localNode = myNode;	
	}
	
	private void init(){
		try
		{			
			sctpServerChannel = SctpServerChannel.open();			
			InetSocketAddress serverAddr = new InetSocketAddress(localNode.localInfor.port);			
			sctpServerChannel.bind(serverAddr);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}		
	}
	
	public void run() {
		connectAllChannelFromSeverToClient();		
	}	
	
	public void connectAllChannelFromSeverToClient(){
		init();
		connectAllClient();
	}	
	
	private void connectAllClient()
	{		
		//LogWriter.getSingle().log("connectAllClient() in SctpServerPart");		
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
					String attributes[] = message.split(";");
					String values[] = attributes[0].split(":");			
					int id = Integer.parseInt(values[1]);					
					if(localNode.neighbors.containsKey(id)){						
						SCTPChannel ch = new SCTPChannel(id, sctpChannel);
						localNode.addChannel(ch);						
						connected = true;
						//LogWriter.getSingle().log("connect nodeID: ("+ String.valueOf(ch.channelID)+") Client in connectAllClient() in SctpServerPart");
						new SctpRecieverThread(ch).start();
					}							
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}		
			}						
		}
		//LogWriter.getSingle().log("finished connectAllClient() in SctpServerPart");
	}	
}
