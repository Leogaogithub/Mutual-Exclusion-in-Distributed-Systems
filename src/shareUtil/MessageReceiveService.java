package shareUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controllerUnit.Node;



public class MessageReceiveService implements IreceiveMessage{
	Node node=null;
	private static MessageReceiveService instance = new MessageReceiveService();
	protected List<IreceiveMessage> listenerList = new CopyOnWriteArrayList<IreceiveMessage>();
	protected List<IreceiveControlMessage> listenerCtrlList = new CopyOnWriteArrayList<IreceiveControlMessage>();
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
	public synchronized void receive(String msg,int channelID){				
		if(msg.startsWith("BYE")){
			for(IreceiveControlMessage i:listenerCtrlList){
				i.receiveControlMessage(msg, channelID);
			}
			return;
		}
		PerformanceMeasureService.getInstance().addReceiveMessageCount();
		//System.out.println("Received"+msg+"from channel"+channelID);
		if(!msg.startsWith("CLOCK:")){
			
			//SCALARTIME
			String tmp =msg.substring(11, msg.indexOf(';'));
			LamportLogicalClockService.getInstance().receiveAction(Integer.parseInt(tmp));
			//vector
			int vcStartPos=msg.indexOf("VECTORCLOCK:", 10);
			int vcEndPos=msg.indexOf(";", vcStartPos+12);
			String vc = msg.substring(vcStartPos+12,vcEndPos+1);
			VectorClockService.getInstance().receiveAction(vc);
			
			for(IreceiveMessage obj:listenerList){
				obj.receive(msg.substring(vcEndPos+1), channelID);
			}
			
		}else if(msg.startsWith("CLOCK:")){
			
			String clock=msg.substring(6,18);	
			//SCALARTIME
			String tmp =msg.substring(31,msg.indexOf(';', 31));
			LamportLogicalClockService.getInstance().receiveAction(Integer.parseInt(tmp));
			//vector time
			int vcStartPos=msg.indexOf("VECTORCLOCK:", 31);
			int vcEndPos=msg.indexOf(";", vcStartPos+12);
			String vc = msg.substring(vcStartPos+12,vcEndPos+1);
			VectorClockService.getInstance().receiveAction(vc);
			
			for(IreceiveMessageWithClock obj:listenerListWithClock){
				obj.receive(msg.substring(vcEndPos+1), channelID,Long.parseLong(clock));
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
	
	public void registerCtrlMsg(IreceiveControlMessage obj){
		listenerCtrlList.add(obj);
	}
	
	public void unregisterCtrlMsg(IreceiveControlMessage obj){
		listenerCtrlList.remove(obj);
	}
	


}
