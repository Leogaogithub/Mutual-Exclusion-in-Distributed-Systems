package lamportAlgorithm;

public class MyVector {
	public int values[];
	private MyVector(){
		
	}	
	private MyVector(int val[]){
		values = new int[val.length];
		for(int i = 0;  i < val.length; i++){
			values[i] = val[i];
		}
	}
	
	public static MyVector copy(int val[]){
		return new MyVector(val);
	}
	
	public static MyVector copy(String vc){
		String vcs[] = vc.split(",");
		int val[] = new int[vcs.length];
		for(int i = 0; i < val.length; i++){
			val[i] = Integer.parseInt(vcs[i]);
		}
		return new MyVector(val);
	}
	
	public static int compare(MyVector big, MyVector small){
		for(int i = 0; i < big.values.length; i++){
			if(big.values[i] > small.values[i]){
				while(i < big.values.length){
					if(big.values[i] < small.values[i]) return 0;
					i++;
				}
				return 1;
			} else if(big.values[i] < small.values[i]){
				while(i < big.values.length){
					if(big.values[i] > small.values[i]) return 0;
					i++;
				}
				return -1;
			}
		}		
		return 0;
	}
	
	public String toString(){
		StringBuilder re = new StringBuilder();		
		for(int i = 0; i < values.length; i++){
			re.append(values[i]);
			re.append(" ");			
		}
		return re.substring(0,re.length()-1);
	}
}
