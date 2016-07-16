package RAAlgorithm;

import shareUtil.IMutualExclusiveStrategy;
import shareUtil.IreceiveMessageWithClock;

public abstract class Status  {
	Ricart ricart;
	public Status(Ricart ricart){
		this.ricart=ricart;
	}
	public abstract void execute();
	public abstract void receive(Message message, int channel, long milliseconds);
}
