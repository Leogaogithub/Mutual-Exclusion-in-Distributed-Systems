package RAAlgorithm;

public class LamportClock {
	
	private int clock;
	
	public LamportClock(){
		clock=1;
	}
	
	public synchronized void update(){
		clock++;
	}
	
	public void receiveMsg(int time){
		clock=Math.max(time, clock)+1;	//need to add one here
	}
	
	public synchronized int getClock(){
		return clock;
	}
	
	public int toCompare(LamportClock other){
		if(this.clock>other.clock)
			return 1;
		else if(this.clock<other.clock)
			return -1;
		else
			return 0;
	}
	
	public String toString(){
		return Integer.toString(clock);
	}

}
