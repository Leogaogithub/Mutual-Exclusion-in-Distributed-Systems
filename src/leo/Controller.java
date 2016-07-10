package leo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Leo.NodeInfo;
import MyChannel.SctpClientPart;
import MyUtil.ConfigExpert;

import xintong.MessageReceiveService;
import xintong.TCPClientHandler;
import xintong.TCPServerListener;

public class Controller{
	

	public Node  myNode;	
	String hostname;
	
	public Controller(String hostname){
		this.hostname=hostname;
	}

	
	
	public void start(){		
		//parse config file
		
		
		try {
			this.connectTCPChannel(myNode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
	}
	
	/**
	 * init the transport layer service
	 */
	public void initTransportService(){

		TCPServerListener server = null;
		try {
			server = new TCPServerListener(myNode.localInfor.port,myNode);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread thread = new Thread(server);
		thread.start();
	}
	

	public void connectSCTPChannel(Node myNode){
		for(NodeInfor nb: myNode.neighbors.values()){
			if(UtilityTool.preIsClient(myNode.localInfor.nodeId, nb.nodeId)){				
				SctpClientPart client = new SctpClientPart(nb);
				client.connectChanel();	
			}
		}
		
	}
	
	/**
	 * start to listen connection request from node with larger id
	 */
	public void connectTCPChannel(Node myNode) throws InterruptedException{
		//return socket to node with higher ID
		for(Channel c:myNode.channelList){
				int try_num=0;
				Node n=c.remote;
				//compare remote node id with local 
				if(n.localInfor.nodeId>myNode.localInfor.nodeId){			
					continue;
				}
				Socket clientSocket = null;
				while(clientSocket==null){
					try_num++;
					try {
						clientSocket = new Socket(n.localInfor.hostName,n.localInfor.port);
						System.out.println(n.localInfor.hostName+":"+n.localInfor.port+" established successively");
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						//System.out.println("fail to establish tcp connection");
					} catch (IOException e) {
						System.out.println("fail to establish tcp connection");
						
					}
					long seconds_to_wait = (long) Math.min(60, Math.pow(2, try_num));
					Thread.sleep(seconds_to_wait*20);
					
				}
				c.socket=clientSocket;
				PrintWriter outToServer = null;
				try {
					outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outToServer.println("NODEID:"+myNode.localInfor.nodeId);
				
				//start new thread to listen the socket;
				new Thread(
						new TCPClientHandler(clientSocket,c.channelID)
						).start();  //should use start
				
					
		}

	}
	
	
	
	

}
