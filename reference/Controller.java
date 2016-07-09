
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller{
	

	Node  myNode;
	MessageHandlerService msgHandler;
	String ipAddr=null;
	int port;
	boolean isActive;
	boolean isMarkerInitiator;
	
	public Controller(String[] s,boolean isactive,boolean isMarkerInitiator){
		ipAddr=s[0];
		port=Integer.parseInt(s[1]);
		this.isActive=isactive;
		this.isMarkerInitiator=isMarkerInitiator;
	}
	public Controller(String[] s,boolean isactive){
		ipAddr=s[0];
		port=Integer.parseInt(s[1]);
		this.isActive=isactive;

	}
	
	
	public void start(){		
		try {
			myNode=this.init(ipAddr,port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		TCPServerService server = null;
		try {
			server = new TCPServerService(myNode.getListenPort());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread thread = new Thread(server);
		thread.start();
		
		
		msgHandler=MessageHandlerService.getInstance();
		try {
			msgHandler.connectNode(myNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("This is node"+myNode.getListenPort());
		try {
			this.connectChannel(myNode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
	}
	
	
	
	
	public Node init(String IPadd,int port) throws Exception{
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		Parser p1 = Parser.getInstance();		
		String configfile=currentDir+"/config.txt";  //linux
		return p1.parseConfigFile(configfile,IPadd,port);
		
	}
	
	
	
	public void connectChannel(Node myNode) throws InterruptedException{
		//return socket to node with higher ID
		for(Channel c:myNode.channelList){
				int try_num=0;
				Node n=c.remote;
				Socket clientSocket = null;
				
				while(clientSocket==null){
					try_num++;
					try {
						clientSocket = new Socket(n.getIPaddress(),n.getListenPort());
						System.out.println(n.getIPaddress()+":"+n.getListenPort()+" established successively");
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						//System.out.println("fail to establish tcp connection");
					} catch (IOException e) {
						System.out.println("fail to establish tcp connection");
						
					}
					long seconds_to_wait = (long) Math.min(60, Math.pow(2, try_num));
					Thread.sleep(seconds_to_wait*20);
					
				}
				c.socket=clientSocket;
					
		}
		
		
		
		
	}
	
	
	
	

}
