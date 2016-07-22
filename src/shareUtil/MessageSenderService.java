package shareUtil;


import java.io.IOException;

import controllerUnit.Node;

import channelTranportLayer.Channel;



public class MessageSenderService implements IsendMessage,IsendMessageWithClock,IsendMessageMultiCast,IsendMessageBroadCast{
	Node node=null;
	private static MessageSenderService instance = new MessageSenderService();
	private MessageSenderService(){
		
	}
	public static MessageSenderService getInstance(){
		return instance;
	}
	public void connectNode(Node node) throws IOException{
		this.node=node;
	}
	
	@Override
	public synchronized void send(String message, int channelID) {
		Channel channel = node.channelRemoteMap.get(channelID);	
		//add vector clock to head 
		PerformanceMeasureService.getInstance().addSendMessageCount();
		LamportLogicalClockService.getInstance().sendAction();
		VectorClockService.getInstance().sendAction();
		
		channel.send("SCALARTIME:"+LamportLogicalClockService.getInstance().getValue()+";"+"VECTORCLOCK:"+VectorClockService.getInstance().toString()+";"+message);
			
	}
	@Override
	public synchronized void send(String message, int channelID, long milliseconds) {
		PerformanceMeasureService.getInstance().addSendMessageCount();
		Channel channel = node.channelRemoteMap.get(channelID);
		LamportLogicalClockService.getInstance().sendAction();
		VectorClockService.getInstance().sendAction();
		channel.send("CLOCK:"+milliseconds+";"+"SCALARTIME:"+LamportLogicalClockService.getInstance().getValue()+";"+"VECTORCLOCK:"+VectorClockService.getInstance().toString()+";"+message);

		
	}
	/**
	 * multi cast msg to the channels
	 */
	public synchronized void send(String message, long milliseconds, int[] channels) {
		for(int i:channels){
			send(message,i,milliseconds);
		}
		
	}
	/**
	 * broad cast msg to all the neigbors' channels
	 */
	public synchronized void sendBroadCast(String message, long milliseconds) {
		for(Integer i:node.channelRemoteMap.keySet()){
			send(message,i,milliseconds);
		}
		
	}
	
	public void sendBroadCast(String message) {
		for(Integer i:node.channelRemoteMap.keySet()){
			send(message,i);
		}	
	}
	

	public synchronized void sendControlMessageng(String message, int channelID) {
		Channel channel = node.channelRemoteMap.get(channelID);	
		channel.send(message);

	}


}
