package shareUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PerformanceParser {
	
	public static void main(String[] args){
		
		
		
		
	}
	
	public static void parseFile(String fileNamePrefix,int numOfNodes,String outputfile){
		int[] res = new int[2];
		for(int i=0;i<numOfNodes;i++){
			int[] tmp= parseSingleFile(fileNamePrefix,i);
			res[0]+=tmp[0];
			res[1]+=tmp[1];
		}
		
		res[0]/=numOfNodes;
		res[1]/=numOfNodes;
		
		RandomAccessFile parsedFile = null;
	

			try {
				parsedFile = new RandomAccessFile(outputfile,"rw");
				parsedFile.setLength(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}

	
	public static int[] parseSingleFile(String singleFileName,int id){
		Path path = Paths.get(singleFileName+"_"+id, null);
		List<String> list =null;
		try {
			list =Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] res = new int[2];
		String[] lines=null;
		for(int i=0;i<list.size();i++){
			lines = list.get(0).split("[,;]");
			res[1]+=Integer.parseInt(lines[3]);
		}
		int numOfRequest=Integer.parseInt(lines[0]);
		int numOfMessage=Integer.parseInt(lines[1]);
		res[1]=res[1]/numOfRequest;
		res[0]=numOfMessage/numOfRequest;
		return res;
		
	}
	

}
