package shareUtil;
import java.util.Vector;
import controllerUnit.DataReader;

public class PerformanceParser {
	public static double[] result = new double[2];
	public static void main(String[] args){		
		
	}
	
	public static void parseFile(String fileNamePrefix,int numOfNodes){
		result[0] = 0;
		result[1] = 0;
		for(int i=0;i<numOfNodes;i++){
			double[] tmp= parseSingleFile(fileNamePrefix+i);
			result[0]+=tmp[0];//message complexity
			result[1]+=tmp[1];//response time
		}		
		result[0]/=numOfNodes;
		result[1]/=numOfNodes;				
	}

	
	public static double[] parseSingleFile(String singleFileName){		
		Vector<String> list = DataReader.readLines(singleFileName);		
		double[] res = new double[2];
		String[] lines=null;
		for(int i=0;i<list.size();i++){
			lines = list.get(i).split("[,;]");
			res[1]+=Integer.parseInt(lines[3]);
		}
		int numOfRun=Integer.parseInt(lines[0]);
		double numOfMessage=(Integer.parseInt(lines[1])+Integer.parseInt(lines[2]))/2.0;
		res[1]=res[1]/numOfRun;
		res[0]=numOfMessage/numOfRun;
		return res;		
	}
	

}
