package shareUtil;

import RAAlgorithm.Ricart;
import controllerUnit.Node;
import lamportAlgorithm.LamportAlgorithm;
import specialRightAlgorithm.Alogrithm;

public class AlgorithmFactory {

	
	private static AlgorithmFactory instance= new AlgorithmFactory();
	public static AlgorithmFactory getInstance(){
		return instance;
	}
	
	/*
	 * get algorithm for this application 
	 */
	public IMutualExclusiveStrategy getAlgorithm(Node node,String input){
		
		if(input.toLowerCase().startsWith("ricart")){
			return new Ricart(node);
		}
		else if(input.toLowerCase().startsWith("lamport")){
			return new LamportAlgorithm(node.numNodes,node.localInfor.nodeId);
		}
		else if(input.toLowerCase().startsWith("rouc")){
			return new Alogrithm(node.localInfor.nodeId, node.numNodes);
		}
		return null;
		
	}
	
	
}
