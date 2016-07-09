package leo;
public class OutputWriter extends MyWriter{
	private static OutputWriter instance =  new OutputWriter();		

	public  static OutputWriter getSingle(){
		return instance;
	}		
	
	public void reset(){
		prefix = "";
	}
	public static void main(String[] args) {
		String name = "test.out";
		OutputWriter.getSingle().open(name, 1);
		OutputWriter.getSingle().clear();
		OutputWriter.getSingle().write(name.substring(0,2));
	}
}
