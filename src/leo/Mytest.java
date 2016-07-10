package leo;
public class Mytest {
	public void connectSCTPChannelFromClientToSever(Node myNode){
		for(NodeInfor nb: myNode.neighbors.values()){
			if(UtilityTool.preIsClient(myNode.localInfor.nodeId, nb.nodeId)){				
				SctpClientPart client = new SctpClientPart(nb);
				client.connectChanel();	
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		Mytest test = new Mytest();
		String name = "config.txt";
		int nodeId = 1;
		Paser.getSingleton().setLocalNodeId(nodeId);		
		Node myNode = Paser.getSingleton().parseFile(name);				
		String prefixName = name.substring(0, name.length()-4)+String.valueOf(nodeId);
		LogWriter.getSingle().open(prefixName+".log");
		leo.LogWriter.getSingle().setPrefix(String.valueOf(nodeId)+":");
		OutputWriter.getSingle().open(prefixName+".out");		
		SctpServerPart serverPart = new SctpServerPart(myNode);
		serverPart.connectAllChannelFromSeverToClient();
		test.connectSCTPChannelFromClientToSever(myNode);
		
		for(int i = 0; i < 12; i++){
			//MessageOk msg = 
			ChannelManager.getSingleton().broadcast("");
		}
		
	}
	
}
