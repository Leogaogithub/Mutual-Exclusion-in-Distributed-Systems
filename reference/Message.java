
public class Message {
	
	protected Path path;
	protected String content;
	protected TimeStamp timestamp;
	protected Method method;
	
	public Message(Path path,String content,TimeStamp timestamp,Method method){
		this.path=path;
		this.content=content;
		this.timestamp=timestamp;
		this.method=method;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * Message format : path:1-2;method:request;timestamp:1-2-3;content:aaabbbcccddd;
	 * 
	 */
	public String toString(){
		String output="PATH:"+path.toString()+"METHOD"+method.toString()+"TIMESTAMP"+timestamp.toString()+"CONTENT"+content;
		return output;
	}
	

	public Message(Method method){
		this.method=method;
	}

	protected void setPath(Path path) {
		this.path = path;
	}
	protected void setContent(String content) {
		this.content = content;
	}
	protected void setTimestamp(TimeStamp timestamp) {
		this.timestamp = timestamp;
	}
	protected void setMethod(Method method) {
		this.method = method;
	}

	
	

}
