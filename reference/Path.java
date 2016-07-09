

public class Path {
	
	int localID;
	int remoteID;
	public Path(Channel ch){
	
	}
	
	public Path(String path){
		String[] input = path.split("-");
		int remoteID= Integer.parseInt(input[0]);
		int localID= Integer.parseInt(input[1]);
	}
	
	public String toString(){
		return "["+localID+"-"+remoteID+"]";
	}

}
