import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageHandlerService {
	Node node=null;
	TCPServerService server;
	private static MessageHandlerService instance = new MessageHandlerService();
	protected List<IreceiveMessage> listenerList = new CopyOnWriteArrayList<IreceiveMessage>();
	
	public static MessageHandlerService getInstance(){		
		return instance;
	}
	public void connectNode(Node node) throws IOException{
		this.node=node;

	}

	
	public   void receive(String msg){
		Message message = parseMsgHeader(msg);
		Channel ch = node.channelRemoteMap.get(message.path.remoteID);
		
		for(IreceiveMessage obj:listenerList){
			obj.newMessageReceived(message, ch);
		}

	}
	
	public Message parseMsgHeader(String msg){
		
		String[] comingMessage = msg.trim().split(";");
		Path path=null;
		Method method=null;
		String content="";
		TimeStamp timestamp=null;
		for(String str:comingMessage){
			if(str.startsWith("PATH:")){
				path=new Path(str.substring(5));	
			}
			if(str.startsWith("METHOD:")){				
				method=MethodFactory.getSingleton().getMethod(str.substring(7));
			}
			if(str.startsWith("TIMESTAMP:")){
				timestamp=new TimeStamp(str.substring(10));
			}
			if(str.startsWith("CONTENT:")){
				content=str.substring(8);
			}

		}
		return new Message(path,content, timestamp, method);
		
		
	}
	
	
	

}
