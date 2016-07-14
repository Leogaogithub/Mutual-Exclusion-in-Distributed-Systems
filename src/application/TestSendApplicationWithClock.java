package application;

import controllerUnit.Node;
import shareUtil.IsendMessageWithClock;
import shareUtil.MessageSenderService;

public class TestSendApplicationWithClock  {
	int loop;
	Node node;
	public TestSendApplicationWithClock(Node node,int loop){
		this.loop=loop;
		this.node=node;

	}
	

	public void sendTest(){

				System.out.println(" broad cast to channel");
				MessageSenderService.getInstance().sendBroadCast("HELLO: from node: "+node.localInfor.nodeId+" at ", System.currentTimeMillis());

	}


	
}
