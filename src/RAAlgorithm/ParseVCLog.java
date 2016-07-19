package RAAlgorithm;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseVCLog {
	int numberOfNodes=0;
	int enterCSTimes=0;
	String filePath=null;
	public static void main(String[] args){
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
		String configfile=currentDir+"/enterCSalgorithmTest";  //linux
		//int a=Integer.parseInt(args[1]);
		//int b=Integer.parseInt(args[2]);
		//String s= args[3];
		
		ParseVCLog vc = new ParseVCLog(5,100,configfile);
		vc.start();
		
	}
	
	public ParseVCLog(int a, int b, String s){
		this.numberOfNodes=a;
		this.enterCSTimes=b;
		this.filePath=s;
		
	}
	
	public boolean start(){
		List<Path> lFile = parseFiles(filePath);
		int[][][] mergedResult = mergeInput(lFile);
		int[][] res = null;
		boolean output = verifyVC(mergedResult,res);
		System.out.println("result"+output);
		//write res int file?
		
		
		return output;
		
	}
	
	
	public List<Path> parseFiles(String s){
		List<Path> lFile= new ArrayList<Path>();
		for(int i=0;i<numberOfNodes;i++){
			lFile.add(Paths.get(s+Integer.toString(i)));
		}
		return lFile;
	}
	
	
	public int[][][] mergeInput(List<Path> lFile){
		int[][][] res = new int[numberOfNodes][enterCSTimes][numberOfNodes];
		
		for(int i=0;i<numberOfNodes;i++){
			List<String> tmp =null;
			try {
				tmp = Files.readAllLines(lFile.get(i),StandardCharsets.UTF_16);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int[][] input = parseVC(tmp.get(0));
			res[i]=input;
		}
		return res;
		
		
		
	}
	
	public boolean verifyVC(int[][][] input,int[][] res){
		res = new int[numberOfNodes*enterCSTimes][numberOfNodes];
		int[] ptr = new int[numberOfNodes];
		int[] reference = new int[numberOfNodes];
		Arrays.fill(reference, enterCSTimes);
		int pos=0,count=0;
		int[] min =null;
		while(compareVC(ptr,reference)==-1){
			
			pos=0;
			while(ptr[pos]==enterCSTimes)
				min = input[pos+1][ptr[pos+1]];
			if(min==null)
				min = input[pos][ptr[pos]];
			
			for(int i=pos+1;i<numberOfNodes ;i++){
				if(ptr[i]==enterCSTimes)
					continue;
				int[] refer =input[i][ptr[i]];
				int cmp=compareVC(min,refer);	
				if(cmp==100 || cmp==99 || cmp==97)
					return false;
				else if(cmp==1){
					min=input[i][ptr[i]];
					pos=i;
				}
				
			}
			ptr[pos]++;
			res[count]=min;
			min=null;
			System.out.println("process:"+pos+":"+Arrays.toString(res[count]));
			count++;
		}
		
		return true;
		
	}
	
	public int compareVC(int[] a, int[] b){
		int prevMin = Integer.MAX_VALUE;
		int prevMax = Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++){
			prevMin=Math.min(prevMin, a[i]-b[i]);
			prevMax=Math.max(prevMax, a[i]-b[i]);
		}
		if(prevMin<0 && prevMax>0 || prevMin>0 && prevMax<0) //over flow 
			return 100; //not comparable
		else if(prevMin==0 && prevMax==0)
			return 99; // same
		else if(prevMin<0 && prevMax<=0)
			return -1; //a <=b
		else if(prevMin>=0 && prevMax>0)
			return 1; //a >= b
		else
			return 97;

		
	}
	
	
	public int[][] parseVC(String str){	//num1,num2,num3,...,lastnum;
		int num=0,count=0,seq=0;
		int[][] res = new int[enterCSTimes][numberOfNodes];
		for(int i=0;i<str.length();i++){
			char c=str.charAt(i);
			if(Character.isDigit(c)){
				num=num*10+c-'0';
			}else {
				res[seq][count++]=num;
				num=0;
				if(count==numberOfNodes){
					count=0;
					seq++;
				}
				
			}
		}

		return res;
		
	}

}
