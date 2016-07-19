package shareUtil;

import controllerUnit.Node;

public class VectorClockService {
	private int[] v;
	Node node;
	int myId;
	StringBuilder stb=null;
	
	private static VectorClockService instance = new VectorClockService();
	public static VectorClockService getInstance(){
		return instance;
	}
	
	public synchronized void init(Node node){
		this.node=node;
		myId=node.localInfor.nodeId;
		v=new int[node.numNodes];
		v[myId]=1;
	}
	
	public synchronized void update(){
		v[myId]++;
	}
	
	public  synchronized String  sendAction(){
		v[myId]++;
		return this.toString();
	}
	
	public synchronized void receiveMsg(String str){
		int num=0,count=0;
		for(int i=0;i<str.length();i++){
			char c=str.charAt(i);
			if(Character.isDigit(c)){
				num=num*10+c-'0';
			}else{
				v[count]=Math.max(v[count], num);
				num=0;
				count++;
			}
		}
		v[myId]++;
	}
	
	public synchronized int getValue(int i){
		return v[i];
	}
	
	public synchronized String toString(){
		StringBuilder stb = new StringBuilder();
		for(int i:v){
			stb.append(i);
			stb.append(',');
		}
		stb.setLength(stb.length()-1); //remove the last ','
		return stb.toString();
	}
	

}
