package lamportAlgorithm;
public class TimeInterval{
	MyVector enterCSTimeStamp;
	long enterCSSystemTime;
	MyVector leaveCSTimeStamp;
	long leaveCSSystemTime; 
	int nodeId;
	
	public TimeInterval(MyVector enterCSTimeStamp, long enterCSSystemTime, MyVector leaveCSTimeStamp, long leaveCSSystemTime, int nodeId){
		this.enterCSTimeStamp = enterCSTimeStamp;
		this.leaveCSTimeStamp = leaveCSTimeStamp;
		this.enterCSSystemTime = enterCSSystemTime;
		this.leaveCSSystemTime = leaveCSSystemTime;
		this.nodeId = nodeId;
	}
	public TimeInterval(){		
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
		return enterCSTimeStamp.toString() + " " + enterCSSystemTime+ " "+ leaveCSTimeStamp.toString() +" " + leaveCSSystemTime + " " + nodeId;
	}
}
