package shareUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Experimental Evaluation: Compare the performance of the two different mutual exclusion
algorithms with respect to message complexity, response time and system throughput using exper-
iments for various values of system parameters
 * 
 *
 */
public class PerformanceMeasureService {
	
	private long[] sendMessageCount=null;
	private long[] receiveMessageCount=null;
	private long[] enterCSTimes=null;
	private long[] responseTime=null;
	private long receiveTotal=0;
	private long sendTotal=0;
	public int count=0;
	public int interval;
	public RandomAccessFile pmFile;
	public int nodeid;

	private static PerformanceMeasureService instance = new PerformanceMeasureService();
	public static PerformanceMeasureService getInstance(){
		return instance;
	}
	
	public void init(int interval,int nodeid){
		this.sendMessageCount=new long[interval];
		this.receiveMessageCount= new long[interval];
		this.enterCSTimes=new long[interval];
		this.responseTime=new long[interval];
		this.interval=interval;
		this.nodeid=nodeid;
		String fileName="performance_file"+"seq_"+count+"_node"+Integer.toString(nodeid);
		try {
			pmFile = new RandomAccessFile(fileName,"rw");
			pmFile.setLength(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public synchronized void updateCSTime(long requestT,long grantT){
		this.sendMessageCount[count]=this.sendTotal;
		this.receiveMessageCount[count]=this.receiveTotal;
		this.enterCSTimes[count]=count+1;
		this.responseTime[count]=grantT-requestT;
		this.toFile();
		count++;
	
	}
	
	


	public synchronized void addSendMessageCount() {
		sendTotal++;
	}


	public synchronized void addReceiveMessageCount() {
		receiveTotal++;
	}
	
	public void toFile(){
	
		StringBuilder res = new StringBuilder();
			int i=count;
			res.append(this.enterCSTimes[i]);
			res.append(',');
			res.append(this.receiveMessageCount[i]);
			res.append(',');
			res.append(this.sendMessageCount[i]);
			res.append(',');
			res.append(this.responseTime[i]);
			res.append(';');
			res.append(System.getProperty("line.separator"));
			
			try {
				pmFile.writeChars(res.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.setLength(0);

		
		
	}
	
}
