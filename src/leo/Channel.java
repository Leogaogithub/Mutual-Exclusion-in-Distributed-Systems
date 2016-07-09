package leo;
public abstract class Channel{
	public int channelID;
	//protected NodeInfor remote;
	public Channel(int channelID){
		this.channelID = channelID;
	}	
	public abstract String receive();	
	public abstract void send(String message);	
	public abstract void close();
}
