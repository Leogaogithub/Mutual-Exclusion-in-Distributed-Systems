package leo;
import java.util.Vector;

public class Paser {
	public int localNodeId = 0;
	static Paser single = new Paser();
	private Paser(){ 		
	}
	
	public static Paser getSingleton(){
		return single;
	}	
	
	public int getLocalNodeId(){
		return localNodeId;
	}
	
	public void setNodeId(int id){
		localNodeId = id;
	}	
	
	public Node parseFile(String name){		
		Vector<String> lines = DataReader.readLines(name);
		boolean part1Finished = false;
		int part2linesNum = 0;			
		Node node = new Node();
		for(String line: lines){
			if(UtilityTool.isValidLine(line)){
				line= UtilityTool.regularingLine(line);
				String [] args = line.split(" ");
				if(!part1Finished){					
					node.numNodes = Integer.parseInt(args[0]);
					node.meanD = Integer.parseInt(args[1]);
					node.meanC = Integer.parseInt(args[2]);
					node.numRequest = Integer.parseInt(args[3]);					
					part1Finished = true;
					
				}else if(part2linesNum < node.numNodes){
					NodeInfor nodeInfor = new NodeInfor();
					nodeInfor.nodeId = Integer.parseInt(args[0]);
					nodeInfor.hostName = args[1];
					nodeInfor.port = Integer.parseInt(args[2]);
					if(nodeInfor.nodeId == localNodeId){
						node.setLocalInfo(nodeInfor);
					}else{
						node.addNeighbor(nodeInfor);
						
					}					
					part2linesNum++;					
				}
			}
		}		
		return node;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "config.txt";
		Node node = Paser.getSingleton().parseFile(name);
		System.out.println("gao");
	}

}
