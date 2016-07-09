
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Node{
	
	protected int nodeID;
	protected int minPerActive;
	protected int maxPerActive;
	protected int minSendDelay;
	protected int snapshotDelay;
	protected int maxNumber;
	protected int numberOfNodes;
	protected int listenPort;
	protected String IPaddress;
	protected List<Channel> channelList= new ArrayList<Channel>();
	protected Map<Integer,Channel> channelRemoteMap = new HashMap<Integer,Channel>();
	
	public Node(int nodeId,String IPadd,int listenPort){
		
		this.listenPort=listenPort;
		this.IPaddress=IPadd;
		this.nodeID=nodeId;
	}
	
	
	public int getNodeID() {
		return nodeID;
	}
	public int getListenPort() {
		return listenPort;
	}

	public List<Channel> getChannel() {
		return channelList;
	}
	
	public String getIPaddress() {
		return IPaddress;
	}


	
	public void addChannel(Channel channel){	
		this.channelList.add(channel);
		
	}


	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	public int getMinPerActive() {
		return minPerActive;
	}
	public int getMaxPerActive() {
		return maxPerActive;
	}

	public int getMinSendDelay() {
		return minSendDelay;
	}
	public int getSnapshotDelay() {
		return snapshotDelay;
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	
	
	

}
