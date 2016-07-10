package channelTranportLayer;
public abstract class Channel{
	public int channelID;	
	public Channel(int channelID){
		this.channelID = channelID;
	}	
	public abstract String receive();	
	public abstract void send(String message);	
	public abstract void close();
}
