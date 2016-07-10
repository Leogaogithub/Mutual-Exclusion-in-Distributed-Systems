package leo;

public class LogWriter extends MyWriter{
	private static LogWriter instance =  new LogWriter();		

	public  static LogWriter getSingle(){
		return instance;
	}
	
	public void open(String name){		
		super.open(name);
		clear();
	}	
	
	public static void main(String[] args) {
		String name = "test.out";
		LogWriter.getSingle().open(name);		
		LogWriter.getSingle().write(name.substring(0,2));
	}
	
}
