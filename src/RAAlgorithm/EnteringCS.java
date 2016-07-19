package RAAlgorithm;

import shareUtil.MessageSenderService;

public class EnteringCS extends Status{

	LamportClock currentClock;
	public EnteringCS(Ricart ricart) {
		super(ricart);
		currentClock=ricart.clock;
		// TODO Auto-generated constructor stub
	}


	public void execute() {

		Message request = MessageFactory.getSingleton().getMessage("REQUEST");
		ricart.requestSentClock=currentClock.update();
		request.addAVP("TIMESTAMP", Integer.toString(ricart.requestSentClock));
		MessageSenderService.getInstance().sendBroadCast(request.toString(), System.currentTimeMillis());
		//System.out.println("send request to all");
		
	}

	
	public  void receive(Message message, int channel, long milliseconds) {
		

		ricart.clock.receiveMsg(Integer.parseInt(message.avp.get("TIMESTAMP")));
		if(message.method.equals("REQUEST")){
			
			//System.out.println("node"+ricart.node.localInfor.nodeId+"at requested timestamp"+ricart.requestSentClock+"received request from:"+channel+"with time stamp"+message.avp.get("TIMESTAMP"));
			if(Integer.parseInt(message.avp.get("TIMESTAMP"))<ricart.requestSentClock || (Integer.parseInt(message.avp.get("TIMESTAMP"))==ricart.requestSentClock) && ricart.node.localInfor.nodeId>channel){
				Message OK = MessageFactory.getSingleton().getMessage("OK");
				currentClock.update();
				OK.addAVP("TIMESTAMP", currentClock.toString());
			
				//System.out.println("reply ok to "+channel);
				MessageSenderService.getInstance().send(OK.toString(), channel, System.currentTimeMillis());
			}else{	
				ricart.pendingQueue.add(channel);		
				//System.out.println("add request channel id"+channel+"in to queue");
			}
		}
		else if(message.method.equals("OK")){
			ricart.addNumOfOk();
			//if all the process has sent ok to me
			//System.out.println("entering status current received ok:"+ricart.getNumOfOk()+"numofNodes "+ricart.node.numNodes+"from "+channel);
			if(ricart.getNumOfOk()==ricart.node.numNodes-1){
				//resume the enterCS();
				//System.out.println("try to notify main thread");
				
				synchronized(ricart){
					ricart.notify();	
				}
				
				
			}
				
		}
		//end receiving
	}



}
