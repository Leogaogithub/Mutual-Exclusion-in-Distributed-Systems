package shareUtil;


import java.io.IOException;

import controllerUnit.Node;

import channelTranportLayer.Channel;



public class MessageSenderService implements IsendMessage,IsendMessageWithClock,IsendMessageMultiCast,IsendMessageBroadCast{
	Node node=null;
	private static MessageSenderService instance = new MessageSenderService();
	public static MessageSenderService getInstance(){
		return instance;
	}
	public void connectNode(Node node) throws IOException{
		this.node=node;
	}
	
	@Override
	public void send(String message, int channelID) {
		System.out.println("channelremote size"+node.channelRemoteMap.size());	
		Channel channel = node.channelRemoteMap.get(channelID);
		channel.send(message);
			
	}
	@Override
	public void send(String message, int channelID, long milliseconds) {
		System.out.println("channelremote size"+node.channelRemoteMap.size()+"Clock"+milliseconds);	
		Channel channel = node.channelRemoteMap.get(channelID);
		channel.send("CLOCK:"+milliseconds+";"+message);
		
		
	}
	/**
	 * multi cast msg to the channels
	 */
	public void send(String message, long milliseconds, int[] channels) {
		for(int i:channels){
			send(message,i,milliseconds);
		}
		
	}
	/**
	 * broad cast msg to all the neigbors' channels
	 */
	public void sendBroadCast(String message, long milliseconds) {
		for(Integer i:node.channelRemoteMap.keySet()){
			send(message,i,milliseconds);
		}
		
	}
	
	public void sendBroadCast(String message) {
		for(Integer i:node.channelRemoteMap.keySet()){
			send(message,i);
		}	
	}


}
