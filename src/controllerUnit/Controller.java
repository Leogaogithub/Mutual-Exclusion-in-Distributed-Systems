package controllerUnit;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import lamportAlgorithm.ChannelManager;

import application.TestReceiveApplication;
import application.TestSendApplication;

import channelTranportLayer.SCTPSubSystem;
import channelTranportLayer.TCPChannel;
import channelTranportLayer.TCPClientHandler;
import channelTranportLayer.TCPServerListener;

import shareUtil.MessageReceiveService;
import shareUtil.MessageSenderService;



public class Controller{
	

	public Node  myNode;	
	public String filename;
	public String transport;
	public int nodeID;
	public Controller(int nodeID,String transport,String configFile){

		this.transport=transport;
		this.filename=configFile;
		this.nodeID=nodeID;

	}

	
	
	public void start(){		
		//parse config file
		Parser.getSingleton().setLocalNodeId(nodeID);
		this.myNode=Parser.getSingleton().parseFile(filename);
		if(transport.toLowerCase().equals("tcp"))
			initTCPTransport();
		else
			initSCTPTransport();
		
		while(!isAllSocketUp(myNode)){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			MessageReceiveService.getInstance().connectNode(myNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			MessageSenderService.getInstance().connectNode(myNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		TestReceiveApplication testreceive = new TestReceiveApplication(myNode.localInfor.nodeId);
		testreceive.listen();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TestSendApplication testsend = new TestSendApplication(myNode,10);
		testsend.sendTest();
		
	}
	
	/**
	 * init the TCP server and client
	 */
	public void initTCPTransport(){			
		this.initTCPServerListener();			
		try {
			this.connectTCPChannel(myNode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isAllSocketUp(Node node){
		if(node.channelRemoteMap.size() == node.neighbors.size()){
			return true;
		}		
		return false;
	}
	
	public void initSCTPTransport(){	
		ChannelManager.getSingleton().setNodeChannels(myNode.channelRemoteMap);
		SCTPSubSystem sctpSubsys = new SCTPSubSystem(myNode);
		sctpSubsys.startSystem();		
	}
	
	/**
	 * init the transport layer service
	 */
	private void initTCPServerListener(){
		
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

	

	
	
	/**
	 * start to listen connection request from node with larger id
	 */
	private void connectTCPChannel(Node myNode) throws InterruptedException{
		//return socket to node with higher ID
		for(NodeInfor remoteNode:myNode.neighbors.values()){
				int try_num=0;
				//compare remote node id with local 
				if(remoteNode.nodeId>myNode.localInfor.nodeId){			
					continue;
				}
				Socket clientSocket = null;
				while(clientSocket==null){
					try_num++;
					try {
						clientSocket = new Socket(remoteNode.hostName,remoteNode.port);
						System.out.println(remoteNode.hostName+":"+remoteNode.port+" established successively");
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						//System.out.println("fail to establish tcp connection");
					} catch (IOException e) {
						System.out.println("fail to establish tcp connection");
						
					}
					long seconds_to_wait = (long) Math.min(60, Math.pow(2, try_num));
					Thread.sleep(seconds_to_wait*20);
					
				}
				
				TCPChannel tcpChannel = new TCPChannel(remoteNode.nodeId);
				tcpChannel.setSocket(clientSocket);

				myNode.addChannel(tcpChannel);
	
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
						new TCPClientHandler(clientSocket,remoteNode.nodeId)
						).start();  //should use start
				
					
		}

	}
	
	
	
	

}
