package application;

import java.util.Vector;

import shareUtil.LamportLogicalClockService;

import lamportAlgorithm.MyVector;
import lamportAlgorithm.TimeInterval;


import controllerUnit.DataReader;
import controllerUnit.MyLogManager;
// enterCS.start enterCS.systemtime leaveCS.end leaveCS.systemtime 
public class CorrectnessVerify {
	TimeInterval dataArray[][] = null;	
	TimeInterval[] sortedResult = null;
	public long sumSD = 0;
	public long sumE = 0;
	public long meanSD = 0;
	public long meanE = 0;
	int nodeNums;
	String fileName;	
	
	
	CorrectnessVerify(String fileName, int nums){
		dataArray = new TimeInterval[nums][];
		nodeNums = nums;
		this.fileName = fileName;
	}
	
	
	void readDataFromFile(){
		int vc[] = new int[nodeNums];
		MyVector enterCSTimeStamp;
		long enterCSSystemTime;
		MyVector leaveCSTimeStamp;
		long leaveCSSystemTime;		
		for(int i = 0; i < nodeNums; i++){
			String name = fileName+i;
			Vector<String> lines = DataReader.readLines(name);
			int size = lines.size();
			dataArray[i] = new TimeInterval[size];			 
			for(int j = 0; j < size; j++){
				String line = lines.get(j);
				String datas[] = line.split(" ");
				int idx = 0;				
				for(int vck = 0; vck < nodeNums; vck++){
					vc[vck] = Integer.parseInt(datas[idx++]);
				}
				enterCSTimeStamp = MyVector.copy(vc);
				enterCSSystemTime = Long.parseLong(datas[idx++]);
				for(int vck = 0; vck < nodeNums; vck++){
					vc[vck] = Integer.parseInt(datas[idx++]);
				}
				leaveCSTimeStamp = MyVector.copy(vc);
				leaveCSSystemTime = Long.parseLong(datas[idx++]);				
				dataArray[i][j] = new TimeInterval(enterCSTimeStamp,enterCSSystemTime ,
						leaveCSTimeStamp, leaveCSSystemTime, Integer.parseInt(datas[idx++]));								
			}
		}		
	}
	
	TimeInterval[] megerSortK(TimeInterval array[][], int start, int end){
		int mid = (start+end)/2;
		if(start >= end) return null;
		if(start+1==end) return array[start];
		TimeInterval[] l1 = megerSortK(array, start, mid);
		TimeInterval[] l2 = megerSortK(array, mid, end);
		if(l1==null) return l2;
		if(l2==null) return l1;
		TimeInterval newArray[] = new TimeInterval[l1.length+l2.length];
		int l1i = 0;
		int l2i = 0;
		int ln = 0;
		while(l1i < l1.length && l2i < l2.length){
			newArray[ln] = new TimeInterval();
			if(MyVector.compare(l2[l2i].enterCSTimeStamp, l1[l1i].enterCSTimeStamp) == 1 ){
				newArray[ln].copy(l1[l1i]);
				l1i++;
			}else{
				newArray[ln].copy(l2[l2i]);
				l2i++;
			}
			ln++;
		}
		while(l1i < l1.length ){
			newArray[ln] = new TimeInterval();
			newArray[ln].copy(l1[l1i]);
			l1i++;
			ln++;
		}
		while(l2i < l2.length ){
			newArray[ln] = new TimeInterval();
			newArray[ln].copy(l2[l2i]);
			l2i++;
			ln++;
		}
		return newArray;
	}
	
	boolean testCorrect(TimeInterval wholeData[]){
		int len = wholeData.length;
		int i = 1;
		if(MyVector.compare(wholeData[0].enterCSTimeStamp, wholeData[0].leaveCSTimeStamp)!=-1) {
			System.out.println(0);
			return false;
		}
		while(i<len){
			if(MyVector.compare(wholeData[i].enterCSTimeStamp, wholeData[i].leaveCSTimeStamp) != -1 ) {
				System.out.println(i);
				return false;
			}
			if(MyVector.compare(wholeData[i].enterCSTimeStamp,wholeData[i-1].leaveCSTimeStamp) != 1) {
				System.out.println(i);
				return false;
			}
			i++;
		}		
		return true;
	}
	
	
	boolean verifyResult(){
		readDataFromFile();
		sortedResult = megerSortK(dataArray, 0, dataArray.length);
		int len = sortedResult.length;
		int i = 0;
		while(i<len){
			MyLogManager.getSingle().getLog("verifyResult").log(sortedResult[i].toString());
			i++;
		}		
		return testCorrect(sortedResult);		
	}
	
	void caculate(){
		sortedResult = megerSortK(dataArray, 0, dataArray.length);
		int len = sortedResult.length;
		int i = 0;
		while(i<len-1){
			long sd = sortedResult[i+1].enterCSSystemTime - sortedResult[i].leaveCSSystemTime;
			sumSD += sd;
			MyLogManager.getSingle().getLog("SD").log("sd:"+sd + " id:" +  sortedResult[i].nodeId + "->" + sortedResult[i+1].nodeId);
			MyLogManager.getSingle().getLog("SD").log("sumSD:"+sumSD);
			long e = sortedResult[i].leaveCSSystemTime - sortedResult[i].enterCSSystemTime;
			sumE += e;			
			MyLogManager.getSingle().getLog("E").log("e:"+e+" id:" + sortedResult[i].nodeId);
			MyLogManager.getSingle().getLog("E").log("sumE:"+sumE);
			i++;
		}		
		meanSD = sumSD/(len-1);		
		MyLogManager.getSingle().getLog("SD").log("meanSD:"+meanSD);
		meanE = sumE/(len-1);
		
		MyLogManager.getSingle().getLog("E").log("meanE:"+meanE);
	}
	
	public static void main(String[] args) {
		String fileName = "./n3-d20-c10/1/TimeInterval";
		int nums = 3;
		if(args.length > 0){
			fileName = args[0];
		}
		if(args.length > 1){
			nums = Integer.parseInt(args[1]);
		}
		CorrectnessVerify test = new CorrectnessVerify(fileName, nums);
		boolean result = test.verifyResult();
		MyLogManager.getSingle().getLog("verifyResult").log(String.valueOf(result));
		test.caculate();
		System.out.println("verifyResult: " + result);
		System.out.println("CorrectnessTest finished");
	}
	
	public static void main1(String[] args) {
		int i = 0;
		while(i++<100){
			int enterCSTimeStamp = LamportLogicalClockService.getInstance().getValue();
			long enterCSSystemTime = System.currentTimeMillis();		
			LamportLogicalClockService.getInstance().tick();		
			int leaveCSTimeStamp = LamportLogicalClockService.getInstance().getValue();			
			long leaveCSSystemTime = System.currentTimeMillis();
			System.out.println(enterCSSystemTime + ":"+leaveCSSystemTime);
		}
	}

}
