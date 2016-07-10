package xintong;

import leo.Node;

public class TestSendApplication {
	
	public TestSendApplication(Node node,int loop){
		
		for(int i=0;i<loop;i++){
			for(int j=0;j<node.neighbors.size();j++){
				int channelID=node.neighbors.get(j).nodeId;
				MessageSenderService.getInstance().send("Hello;",channelID );
			}
			
			
			
		}
		
		
		
		
	}
	

}
