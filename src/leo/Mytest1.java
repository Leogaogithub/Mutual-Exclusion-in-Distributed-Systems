package leo;
public class Mytest1 {	
	
	public static void main(String[] args) {
		int nodeId = 1;
		String name="config.txt";  //linux
		String currentDir = System.getProperty("user.dir");
		String prefixName = name.substring(0, name.length()-4)+"-" + String.valueOf(nodeId);
		LogWriter.getSingle().open(prefixName+".log");
		LogWriter.getSingle().clear();		
		OutputWriter.getSingle().open(prefixName+".out");
		OutputWriter.getSingle().clear();
		Mytest1 test = new Mytest1();
		//System.out.println(currentDir);
		
		Controller controller1 = new Controller(nodeId,"sctp",name);
		controller1.start();
		while(controller1.myNode.neighbors.size() != controller1.myNode.channelRemoteMap.size()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {			
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < 5; i++){
			String message = "gaogao";
			ChannelManager.getSingleton().broadcast(message);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}		
		
	}
	
}
