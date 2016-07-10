package leo;
import java.util.HashMap;
import java.util.List;

public class ChannelManager {
	static ChannelManager single = new ChannelManager();	
	HashMap<Integer, Channel> channels = null;
	public static ChannelManager getSingleton(){
		return single;
	}
	
	int outNodeId = 0;	
	private ChannelManager(){
		channels = new HashMap<Integer, Channel>();
	}
	
	public void addChannel(Channel  ch){
		channels.put(ch.channelID, ch);
	}
	
	private Channel getChannel(int Id){
		return channels.get(Id);
	}
	
	public void send(int dstId, String message){
		Channel ch = getChannel(dstId);
		ch.send(message);
	}
	
	public void broadcast(String message){
		for(Channel ch : channels.values()){
			ch.send(message);
		}
	}
	
	public void multicast(String message, List<Integer> desList){
		for(int nbid: desList){
			send(nbid, message);
		}
	}
	
	public void closeAll(){
		for(Channel ch : channels.values()){			
			ch.close();			
		}
	}
}
