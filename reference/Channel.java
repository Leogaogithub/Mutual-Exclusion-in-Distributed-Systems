import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Channel implements IreceiveMessage{

	protected int channelID;
	protected Node local;
	protected Node remote;
	protected Socket socket;
	boolean isSent;
	boolean isRecording;
	boolean isSet;
	String channelContent= "";
	public Channel(Node localnode,Node remotenode) throws Exception{
		this.channelID=remotenode.getNodeID();
		this.local=localnode;
		this.remote=remotenode;
	}
	
	/**
	 * reset the recording history on this channel
	 */
	public void reset(){
		isSent=false;
		isRecording=false;
		isSet=false;
		setEmpty();
	}
	
	/**
	 * reset the recorded message on this channel
	 */
	public void setEmpty(){
		channelContent="";
	}
	
	/**
	 * start recording 
	 */
	public void startRecordChannel(){
		System.out.println("start recording channel"+channelID);
		isRecording=true;
		
		
	}

	public void stopRecordChannel(){
		System.out.println("stop recording channel"+channelID);
		isRecording=false;
		isSet=true;
	}


	@Override
	public void newMessageReceived(Message message, Channel ch) {
		// TODO Auto-generated method stub
		
	}

}