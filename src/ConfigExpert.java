package MyUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Leo.NodeInfo;


public class ConfigExpert {
	public int globleRootNode = 0;
	public int localNodeId = 0;
	public int numAsServer = 0;
	ArrayList<Integer> clientsAsServer;
	//six tokens
	public final int MESSAGE_SIZE = 100;
	public int numNodes = 0;
	public int minPerActive = 0;
	public int maxPerActive = 0;
	public int minSendDelay = 0;
	public int snapshotDelay = 0;
	public int maxNumber = 0;	
	
	public TreeNode curTreeNode = null;
	
	
	
	public NodeInfo allNodeInfo[] = null;
	public ArrayList<Integer>[] neighbors = null;
	//HashMap<Integer, NodeInfo> allNodeInf = null;	
	//HashMap<Integer, ArrayList<Integer>> NeighborInf = null;
	
	static ConfigExpert single = new ConfigExpert();
	private ConfigExpert(){ 
		//allNodeInf = new HashMap<Integer, NodeInfo>();
		//NeighborInf = new HashMap<Integer, ArrayList<Integer>>();
	}
	
	public ArrayList<Integer> getClientsAsServer(){
		return clientsAsServer;
	}
	private void updateClientsAsServer(){
		clientsAsServer = new ArrayList<Integer>();
		ArrayList<Integer> nbs = neighbors[localNodeId];
		for(int i = 0; i < nbs.size(); i++){
			int id = nbs.get(i);
			if(UtilityTool.preIsClient(id, localNodeId)){
				clientsAsServer.add(id);
			}
		}
	}
	
	public void updateTreeNode(){		
		SpanningTreeGenerator sp = new SpanningTreeGenerator(localNodeId, neighbors);
		sp.generate();
		curTreeNode = sp.getCurTreeNode();
	}
	
	public static ConfigExpert getSingleton(){
		return single;
	}	
	
	public int getLocalNodeId(){
		return localNodeId;
	}
	
	private  ArrayList<Integer> getNeighborList(int nodeId){
		return neighbors[nodeId];
	}
	
	public  NodeInfo getNodeInfo(int nodeId){
		return allNodeInfo[nodeId];
	}
	
	public  NodeInfo getLocalNodeInfo(){
		return getNodeInfo(localNodeId);
	}
	//small id is server
	private int getNumAsServer(int nodeId){
		int result = 0;
		ArrayList<Integer> nbs = neighbors[nodeId];
		for(int i = 0; i < nbs.size(); i++){
			int id = nbs.get(i);
			if(UtilityTool.preIsClient(id, nodeId)){
				result++;
			}
		}
		return result;
	}
	
	public void setNodeId(int id){
		localNodeId = id;
	}
	
	public ArrayList<Integer> getLocalNeighborList(){
		return getNeighborList(localNodeId);
	}	
	
	public int getLocalNeighorSize(){
		ArrayList<Integer> neighb = getLocalNeighborList();
		return neighb.size();
	}
	public int getLocalNumAsServer(){
		return numAsServer;
	}
	
	
	public void loadFile(String name){		
		Vector<String> lines = DataReader.readLines(name);
		boolean part1Finished = false;
		int part2linesNum = 0;
		int part3linesNum = 0;
		for(String line: lines){
			if(UtilityTool.isValidLine(line)){
				line= UtilityTool.regularingLine(line);
				String [] args = line.split(" ");
				if(!part1Finished){					
					numNodes = Integer.parseInt(args[0]);
					minPerActive = Integer.parseInt(args[1]);
					maxPerActive = Integer.parseInt(args[2]);
					minSendDelay = Integer.parseInt(args[3]);
					snapshotDelay = Integer.parseInt(args[4]);
					maxNumber = Integer.parseInt(args[5]);
					allNodeInfo = new NodeInfo[numNodes];
					neighbors = new ArrayList[numNodes];
					part1Finished = true;
					
				}else if(part2linesNum < numNodes){
					int nodeId = Integer.parseInt(args[0]);
					String nodeName = args[1];
					int port = Integer.parseInt(args[2]);
					allNodeInfo[part2linesNum] = new NodeInfo(nodeId, nodeName, port);	
					//allNodeInf.put(part2linesNum, new NodeInfo(nodeId, nodeName, port));
					part2linesNum++;					
				}else{
					neighbors[part3linesNum] = new ArrayList();
					for(String arg: args){						
						neighbors[part3linesNum].add(Integer.parseInt(arg));							
					}
					part3linesNum++;					
				}
			}			
		}			
		numAsServer = getNumAsServer(localNodeId);
		updateClientsAsServer();
		updateTreeNode();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "config.txt";
		ConfigExpert.getSingleton().loadFile(name);
		System.out.println("gao");
	}

}
