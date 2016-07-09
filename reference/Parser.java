import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


public class Parser {
	
	private int minPerActive;
	private int maxPerActive;
	private int minSendDelay;
	private int snapshotDelay;
	private int maxNumber;
	private int numberOfNodes;
	private List<Node> nodes=new ArrayList<Node>();
	public List<Node> getNodes() {
		return nodes;
	}
	private Node myNode;
	int count;
	Map<Node,List<Channel>> route = new HashMap<Node,List<Channel>>();

	public Node getMyNode() {
		return myNode;
	}


	public void setMyNode(Node myNode) {
		this.myNode = myNode;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	private String comments="#";
	
	
	
	public static Parser getInstance(){
		return instance;
	}
	private static Parser instance = new Parser();
	
	public Node parseConfigFile(String configfile,String ipadd, int listenport) throws Exception{
		
		File file = new File(configfile);
		Scanner sc = new Scanner(file);
		
		while(sc.hasNextLine()){
			String input = sc.nextLine();
			if(input.trim().startsWith(comments))
				continue;
			if(input.trim().length()==0)
				continue;
			
			String[] strArray=input.trim().split("\\s+");
			if(numberOfNodes==0){
				numberOfNodes=Integer.parseInt(strArray[0]);
				setMinPerActive(Integer.parseInt(strArray[1]));
				setMaxPerActive(Integer.parseInt(strArray[2]));
				setMinSendDelay(Integer.parseInt(strArray[3]));
				setSnapshotDelay(Integer.parseInt(strArray[4]));
				setMaxNumber(Integer.parseInt(strArray[5]));
				continue;
			}
			
			
			if(nodes.size()==Integer.parseInt(strArray[0])){
				int id=Integer.parseInt(strArray[0]);
				String hostName= strArray[1];
				int port=Integer.parseInt(strArray[2]);
				Node newNode=new Node(id,hostName,port);				
				newNode.setMaxNumber(maxNumber);
				newNode.setMaxPerActive(maxPerActive);
				newNode.setMinPerActive(minPerActive);
				newNode.setMinSendDelay(minSendDelay);
				newNode.setSnapshotDelay(snapshotDelay);
				newNode.setNumberOfNodes(numberOfNodes);
				nodes.add(newNode);
				continue;
			}

			
			
			if(nodes.get(count).getChannel().size()==0){
					for(String s:strArray){
						if(s.equals(comments))
							break;
						Node local = nodes.get(count);
						Node remote = nodes.get(Integer.parseInt(s));
						Channel channel = new Channel(local,remote);
						local.addChannel(channel);
						local.channelRemoteMap.put(remote.getNodeID(), channel);
				}
					
			}
			count++;		
			
		}
		sc.close();
		
		for(Node n:this.getNodes()){
			if(n.getIPaddress().equals(ipadd) && n.getListenPort()==listenport){
				return n;
			}
		}
		return null;	
		
	}


	public int getMinPerActive() {
		return minPerActive;
	}


	public void setMinPerActive(int minPerActive) {
		this.minPerActive = minPerActive;
	}


	public int getMaxPerActive() {
		return maxPerActive;
	}


	public void setMaxPerActive(int maxPerActive) {
		this.maxPerActive = maxPerActive;
	}


	public int getMinSendDelay() {
		return minSendDelay;
	}


	public void setMinSendDelay(int minSendDelay) {
		this.minSendDelay = minSendDelay;
	}


	public int getSnapshotDelay() {
		return snapshotDelay;
	}


	public void setSnapshotDelay(int snapshotDelay) {
		this.snapshotDelay = snapshotDelay;
	}


	public int getMaxNumber() {
		return maxNumber;
	}


	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	public void calculateRoute(Node target){
		
		Queue<Channel> queue = new LinkedList<Channel>();
		queue.addAll(myNode.channelList);
		while(queue.size()>0){
			int len=queue.size();
			for(int i=0;i<len;i++){
				Channel c = queue.poll();
				if(c.remote==target){
					//route.put(target, c);
				}else{
					queue.addAll(c.remote.channelList);
				}
					
			}
		}
		
		
	}
	
	
	
	

}
