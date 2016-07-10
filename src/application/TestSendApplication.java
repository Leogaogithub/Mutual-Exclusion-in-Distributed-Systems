package application;

import controllerUnit.Node;
import shareUtil.MessageSenderService;

public class TestSendApplication {
	int loop;
	Node node;
	public TestSendApplication(Node node,int loop){
		this.loop=loop;
		this.node=node;

	}
	

	public void sendTest(){
		for(int i=0;i<loop;i++){
			
			for(int channelID:node.channelRemoteMap.keySet()){

				System.out.println("Send times"+i+"to channel"+channelID);
				MessageSenderService.getInstance().send("Hello;",channelID );
				
			}
			
			
			
		}
	}
	
}
