package lamportAlgorithm;


public class MessageNodeID extends Message {
	public int nodeId ;
	public MessageNodeID(int id){
		type = MessageParser.getSingleton().typeNodeId;
		nodeId = id;
	}
	
	public String toString(){
		String res = type;
		res += ":" + String.valueOf(nodeId)+ ";";
		return res;
	}
}
