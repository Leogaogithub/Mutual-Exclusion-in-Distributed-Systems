package leo;
public class NodeInfor {
	public int nodeId;
	public String hostName;
	public int port;
	public String ipAddress;
	
	public NodeInfor(){
		nodeId = 0;
		port = 0;
		hostName = "";
		ipAddress = "";
	}
	
	public NodeInfor(int id, String hostName, int port, String ipAddress){
		this.nodeId = id;
		this.port = port;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
	}
	
	public NodeInfor(int id, String hostName, int port){
		this.nodeId = id;
		this.port = port;
		this.hostName = hostName;		
	}
}
