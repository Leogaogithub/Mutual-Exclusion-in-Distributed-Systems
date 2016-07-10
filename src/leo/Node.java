package leo;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Node{
	public int numNodes = 0;
	public int meanD = 0;
	public int meanC = 0;
	public int numRequest = 0;	
	public NodeInfor localInfor = null;	
	public Map<Integer,NodeInfor> neighbors = new HashMap<Integer,NodeInfor>();	
	public Map<Integer,Channel> channelRemoteMap = new ConcurrentHashMap<Integer,Channel>();	
	public void addNeighbor(NodeInfor nodeInfor){
		neighbors.put(nodeInfor.nodeId, nodeInfor);
	}	
	
	public void setLocalInfo(NodeInfor nodeInfor){		
		localInfor = nodeInfor;
	}
	
	public Node(){	

	}
	
	public void addChannel(Channel channel){	
		channelRemoteMap.put(channel.channelID, channel);		
	}
}
