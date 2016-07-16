package RAAlgorithm;

import shareUtil.MessageSenderService;

public class EnteringCS extends Status{

	
	public EnteringCS(Ricart ricart) {
		super(ricart);
		// TODO Auto-generated constructor stub
	}


	public void execute() {
		LamportClock currentClock=ricart.clock;
		Message request = MessageFactory.getSingleton().getMessage("REQUEST");
		request.addAVP("TIMESTAMP", currentClock.toString());
		MessageSenderService.getInstance().sendBroadCast(request.toString(), System.currentTimeMillis());
		
	}

	
	public  void receive(Message message, int channel, long milliseconds) {
		ricart.clock.receiveMsg(Integer.parseInt(message.avp.get("TIMESTAMP")));
		boolean isLargerTS = Integer.parseInt(message.avp.get("TIMESTAMP"))<ricart.requestSentClock;
		boolean isSameTSLargerID = Integer.parseInt(message.avp.get("TIMESTAMP"))==ricart.requestSentClock && ricart.node.localInfor.nodeId>channel;
		if(message.method.equals("REQUEST")){
			if(isLargerTS || isSameTSLargerID){
				Message OK = MessageFactory.getSingleton().getMessage("OK");
				OK.addAVP("TIMESTAMP", Integer.toString(ricart.clock.getClock()));
				System.out.println("Strategy forward ok to "+channel);
				MessageSenderService.getInstance().send(OK.toString(), channel, System.currentTimeMillis());
			}else{
				System.out.println("add request channel id"+channel+"in to queue");
				ricart.addToRequestQueue(channel);
			}
		}
		else if(message.method.equals("OK")){
			ricart.addNumOfOk();
			//if all the process has sent ok to me
			System.out.println("current received ok:"+ricart.getNumOfOk()+"numofNodes"+ricart.node.numNodes);
			if(ricart.getNumOfOk()==ricart.node.numNodes-1){
				//resume the enterCS();
				System.out.println("try to notify main thread");
				
				synchronized(ricart){
					ricart.notify();	
				}
				
				
			}
				
		}
		

		
	}



}
