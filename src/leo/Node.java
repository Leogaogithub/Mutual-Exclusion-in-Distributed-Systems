package leo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Node{
	public int numNodes = 0;
	public int meanD = 0;
	public int meanC = 0;
	public int numRequest = 0;		
	NodeInfor localInfor = null;	
	public Map<Integer,NodeInfor> neighbors = new HashMap<Integer,NodeInfor>();	
	
	
	protected List<Channel> channelList= new ArrayList<Channel>();
	protected Map<Integer,Channel> channelRemoteMap = new HashMap<Integer,Channel>();
	
	public void addNeighbor(NodeInfor nodeInfor){
		neighbors.put(nodeInfor.nodeId, nodeInfor);
	}	
	
	public void setLocalInfo(NodeInfor nodeInfor){		
		localInfor = nodeInfor;
	}
	
	public Node(){	

	}
	
	public void addChannel(Channel channel){	
		this.channelList.add(channel);		
	}
}
