package shareUtil;

import RAAlgorithm.Ricart;
import controllerUnit.Node;

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
			
		}
		else if(input.toLowerCase().startsWith("rouc")){
			
		}
		return null;
		
	}
	
	
}
