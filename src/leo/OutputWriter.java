package leo;
public class OutputWriter extends MyWriter{
	private static OutputWriter instance =  new OutputWriter();		

	public  static OutputWriter getSingle(){
		return instance;
	}		
	
	public void open(String name){		
		super.open(name);
		clear();
	}	

	public static void main(String[] args) {
		String name = "test.out";
		OutputWriter.getSingle().open(name);
		OutputWriter.getSingle().clear();
		OutputWriter.getSingle().write(name.substring(0,2));
	}
}
