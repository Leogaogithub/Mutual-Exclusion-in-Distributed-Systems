package shareUtil;

public class LamportLogicalClockService {
	private static LamportLogicalClockService instance = new LamportLogicalClockService();
	public static LamportLogicalClockService getInstance(){		
		return instance;
	}
	
	int c;
	public LamportLogicalClockService(){
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
