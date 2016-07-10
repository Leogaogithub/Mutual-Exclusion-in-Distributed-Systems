package shareUtil;

public class LamportLogicalClock {
	int c;
	public LamportLogicalClock(){
		c = 1;
	}
	
	public int getValue(){
		return c;
	}
	
	public void tick(){// internal event
		c++;
	}
	
	public void sendAction(){
		c++;
	}
	
	public void receiveAction(int sentValue){
		c = Math.max(c, sentValue)+1;
	}
}
