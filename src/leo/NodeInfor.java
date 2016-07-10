package leo;
public class NodeInfor {
	int nodeId;
	String hostName;
	int port;
	String ipAddress;
	
	NodeInfor(){
		nodeId = 0;
		port = 0;
		hostName = "";
		ipAddress = "";
	}
	
	NodeInfor(int id, String hostName, int port, String ipAddress){
		this.nodeId = id;
		this.port = port;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
	}
	
	NodeInfor(int id, String hostName, int port){
		this.nodeId = id;
		this.port = port;
		this.hostName = hostName;		
	}
}
