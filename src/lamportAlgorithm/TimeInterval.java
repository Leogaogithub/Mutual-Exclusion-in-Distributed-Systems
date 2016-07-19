package lamportAlgorithm;
public class TimeInterval{
	int enterCSTimeStamp;
	long enterCSSystemTime;
	int leaveCSTimeStamp;
	long leaveCSSystemTime; 
	int nodeId;
	
	public TimeInterval(int enterCSTimeStamp, long enterCSSystemTime, int leaveCSTimeStamp, long leaveCSSystemTime, int nodeId){
		this.enterCSTimeStamp = enterCSTimeStamp;
		this.leaveCSTimeStamp = leaveCSTimeStamp;
		this.enterCSSystemTime = enterCSSystemTime;
		this.leaveCSSystemTime = leaveCSSystemTime;
		this.nodeId = nodeId;
	}
	public TimeInterval(){
		this.enterCSTimeStamp = 0;
		this.leaveCSTimeStamp = 0;
		this.enterCSSystemTime = 0;
		this.leaveCSSystemTime = 0;
		nodeId = 0;
	}
	
	public void copy(TimeInterval x){			
		enterCSTimeStamp = x.enterCSTimeStamp;
		leaveCSTimeStamp = x.leaveCSTimeStamp;
		enterCSSystemTime = x.enterCSSystemTime;
		leaveCSSystemTime = x.leaveCSSystemTime;
		nodeId = x.nodeId;
	}
	
	public String toString(){
		return enterCSTimeStamp + " " + enterCSSystemTime+ " "+ leaveCSTimeStamp +" " + leaveCSSystemTime + " " + nodeId;
	}
}
