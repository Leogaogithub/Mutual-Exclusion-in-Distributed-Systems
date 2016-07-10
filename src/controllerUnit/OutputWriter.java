package controllerUnit;
public class OutputWriter extends MyWriter{
	private static OutputWriter instance =  new OutputWriter();		

	public  static OutputWriter getSingle(){
		return instance;
	}		
	
	public void open(String name){		
		super.open(name);
		clear();
	}	
}
