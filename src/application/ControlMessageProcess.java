package application;
import java.util.HashSet;
import java.util.Set;

import shareUtil.IreceiveControlMessage;
import shareUtil.MessageReceiveService;

public class ControlMessageProcess implements IreceiveControlMessage{
	
	private static ControlMessageProcess instance = new ControlMessageProcess();
	public Set<Integer> myset = new HashSet<Integer>();
	int neighborSize = 0;
	private ControlMessageProcess(){
		MessageReceiveService.getInstance().registerCtrlMsg(this);
	}
	
	public static ControlMessageProcess getInstance(){		
		return instance;
	}
	
	public void init(int neighborSize){
		this.neighborSize = neighborSize;		
	}
	
	public void refresh(){
		myset.clear();
	}
	
	public boolean isOk(){
		return neighborSize == myset.size();
	}
	
	@Override
	public void receiveControlMessage(String message, int channel) {
		myset.add(channel);		
	}

}
