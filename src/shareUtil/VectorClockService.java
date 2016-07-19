package shareUtil;

public class VectorClockService {
	
	private static VectorClockService instance = new VectorClockService();
	private VectorClockService(){
	}
	public static VectorClockService getInstance(){
		return instance;
	}
	
	public int numProcess ;
	public int localNodeId;
	int c[] = null;
	public void init(int numPro, int nodeId){
		numProcess = numPro;
		localNodeId = nodeId;
		c = new int[numPro];
		for(int i = 0; i < numProcess; i++){
			c[i] = 0;
		}
		c[localNodeId]++;
	}
	
	public void sendAction(){
		c[localNodeId]++;
	}
	
	public void tick(){
		c[localNodeId]++;
	}
	

	
	public void receiveAction(String vc){
		int num=0,count=0;
	
		for(int i=0;i<vc.length();i++){
			char ch=vc.charAt(i);
			if(Character.isDigit(ch)){
				num=num*10+ch-'0';
			}
			else {
				c[count]=Math.max(c[count],num);
				count++;
				num=0;
			}	
		}
		c[localNodeId]++;
	}
	
	
	public String toString(){
		StringBuilder stb = new StringBuilder();
		for(int i = 0; i < numProcess; i++){
			stb.append(c[i]);
			stb.append(',');
		}
		
		return stb.substring(0, stb.length()-1);
	}
	

}
