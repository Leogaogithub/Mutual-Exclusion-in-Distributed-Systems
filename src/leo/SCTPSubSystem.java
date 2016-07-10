package leo;

public class SCTPSubSystem {
	Node localNode;
	public SCTPSubSystem(Node myNode){
		localNode = myNode;
	}
	public void connectSCTPChannel(){
		for(NodeInfor nb: localNode.neighbors.values()){
			if(UtilityTool.preIsClient(localNode.localInfor.nodeId, nb.nodeId)){				
				SctpClientPart clientThread = new SctpClientPart(localNode, nb);
				clientThread.start();	
			}
		}		
	}
	
	public void startSystem(){
		SctpServerPart serverthread = new SctpServerPart(localNode);
		serverthread.start();
		connectSCTPChannel();		
	}
}
