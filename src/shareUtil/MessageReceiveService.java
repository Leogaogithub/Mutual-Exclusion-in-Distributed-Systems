package shareUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controllerUnit.Node;



public class MessageReceiveService implements IreceiveMessage{
	Node node=null;
	private static MessageReceiveService instance = new MessageReceiveService();
	protected List<IreceiveMessage> listenerList = new CopyOnWriteArrayList<IreceiveMessage>();
	protected List<IreceiveMessageWithClock> listenerListWithClock = new CopyOnWriteArrayList<IreceiveMessageWithClock>();
	
	public static MessageReceiveService getInstance(){		
		return instance;
	}
	/**
	 * connect MessageReceiveService with node
	 * @param node
	 * @throws IOException
	 */
	public void connectNode(Node node) throws IOException{
		this.node=node;
	}
	
	/**
	 * receive message from network socket
	 * @param msg
	 * @param ippaddress
	 */
	public  void receive(String msg,int channelID){
		if(!msg.startsWith("CLOCK:")){
			for(IreceiveMessage obj:listenerList){
				obj.receive(msg, channelID);
			}
		}else if(msg.startsWith("CLOCK:")){
			String clock=msg.split(";")[0].substring(6);
			for(IreceiveMessageWithClock obj:listenerListWithClock){
				obj.receive(msg, channelID,Long.parseLong(clock));
			}
		}
		
	}
	
	/**
	 * register to the message receive event.
	 * @param obj
	 */
	public void register(IreceiveMessage obj){
		listenerList.add(obj);
	}
	
	/**
	 * unregister from receiving any coming message
	 * @param obj
	 */
	public void unregister(IreceiveMessage obj){
		listenerList.remove(obj);
	}


	public void registerWithClock(IreceiveMessageWithClock obj){
		listenerListWithClock.add(obj);
	}
	
	public void unregisterWithClock(IreceiveMessageWithClock obj){
		listenerListWithClock.remove(obj);
	}

}
