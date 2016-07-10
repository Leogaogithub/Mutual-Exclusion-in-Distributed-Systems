package shareUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controllerUnit.Node;



public class MessageReceiveService {
	Node node=null;
	private static MessageReceiveService instance = new MessageReceiveService();
	protected List<IreceiveMessage> listenerList = new CopyOnWriteArrayList<IreceiveMessage>();
	
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

		for(IreceiveMessage obj:listenerList){
			obj.receive(msg, channelID);
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

	

}
