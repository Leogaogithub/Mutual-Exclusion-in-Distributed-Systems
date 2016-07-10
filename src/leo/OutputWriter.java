package leo;
public class OutputWriter extends MyWriter{
	private static OutputWriter instance =  new OutputWriter();		

	public  static OutputWriter getSingle(){
		return instance;
	}		
	
	public void open(String name){
		clear();
		super.open(name);
	}	

	public static void main(String[] args) {
		String name = "test.out";
		OutputWriter.getSingle().open(name);
		OutputWriter.getSingle().clear();
		OutputWriter.getSingle().write(name.substring(0,2));
	}
}
