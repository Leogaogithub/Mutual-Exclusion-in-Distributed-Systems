package lamportAlgorithm;

import java.util.Comparator;

public class TimeStampWithID implements Comparator<TimeStampWithID>{
	public int timeStamp;
	public int nodeId;
	
	public TimeStampWithID(int nodeId, int timeStamp){
		this.timeStamp = timeStamp;
		this.nodeId = nodeId;
	}
	
	public int compare(TimeStampWithID x, TimeStampWithID y){
		if(x.timeStamp > y.timeStamp) return 1;
		if(x.timeStamp < y.timeStamp) return -1;
		if(x.timeStamp == y.timeStamp){
			if(x.nodeId > y.nodeId) return 1;
			return -1;
		}
		return 0;
	}
}
