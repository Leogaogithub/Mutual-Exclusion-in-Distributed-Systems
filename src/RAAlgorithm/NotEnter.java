package RAAlgorithm;

import shareUtil.MessageSenderService;

public class NotEnter extends Status{
	LamportClock currentClock;
	public NotEnter(Ricart ricart) {
		super(ricart);
		currentClock=ricart.clock;
	}

	@Override
	public synchronized void execute() {
	
		while(!ricart.getQueue().isEmpty()){			
			int tmpid=ricart.getQueue().poll();
			//System.out.println("After leaving cs, forward ok to "+tmpid);
			sendOkMsg(tmpid);
		}
		
	
		
		
	}

	@Override
	public void receive(Message message, int channel, long milliseconds) {

		ricart.clock.receiveMsg(Integer.parseInt(message.avp.get("TIMESTAMP")));
		
		if(message.method.equals("REQUEST")){
			sendOkMsg(channel);
		}
		else if(message.method.equals("OK")){
			//System.out.println("In not enter pattern: current received ok:"+ricart.getNumOfOk()+"numofNodes"+ricart.node.numNodes);
		}

	}
	
	public void sendOkMsg(int channel){
		Message OK = MessageFactory.getSingleton().getMessage("OK");
		currentClock.update();
		OK.addAVP("TIMESTAMP", currentClock.toString());
		MessageSenderService.getInstance().send(OK.toString(), channel, System.currentTimeMillis());
		//System.out.println("during idle, reply ok to channel"+channel);
	}





}
