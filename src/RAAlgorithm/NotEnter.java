package RAAlgorithm;

import shareUtil.MessageSenderService;

public class NotEnter extends Status{
	LamportClock currentClock;
	public NotEnter(Ricart ricart) {
		super(ricart);
		currentClock=ricart.clock;
	}

	@Override
	public void execute() {
		while(!ricart.getQueue().isEmpty()){			
			int tmpid=ricart.getQueue().poll();
			System.out.println("After leaving cs, forward ok to "+tmpid);
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
			
		}
		
		
	}
	
	public void sendOkMsg(int channel){
		LamportClock currentClock=ricart.clock;
		Message OK = MessageFactory.getSingleton().getMessage("OK");
		OK.addAVP("TIMESTAMP", currentClock.toString());
		MessageSenderService.getInstance().send(OK.toString(), channel, System.currentTimeMillis());
	}





}
