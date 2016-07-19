package application;

import controllerUnit.Node;
import shareUtil.AlgorithmFactory;
import shareUtil.IMutualExclusiveStrategy;

import java.util.Random;

/**
 * Created by Yifan on 7/18/16.
 */
public class YifanApp {
    public int interRequestDelay;
    public int csExecTimer;
    public Node node;
    public Random random;
    public IMutualExclusiveStrategy strt;
    public YifanApp(Node node, String algo){
        random = new Random(System.currentTimeMillis());
        interRequestDelay = node.meanD;
        csExecTimer = node.meanC;
        this.node = node;
        strt = AlgorithmFactory.getInstance().getAlgorithm(node, algo);
    }

    public void start() {
        System.out.println("numofrequest" + node.numRequest);
        for (int i = 0; i<node.numRequest; i++){
            int t1 = nextInterRequestDelay();
            int t2 = nextcsExecutionTimer();
            try{
                Thread.sleep(t1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("application enter cs! at "+(i+1)+"times with execution time "+t2+"and interrequest delay"+t1);
            strt.csEnter();
            try{
                Thread.sleep(t2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strt.csLeave();
            System.out.println("application leaves cs!");
        }
    }

    public int nextInterRequestDelay(){

        return (int) (-interRequestDelay*Math.log(random.nextDouble()));
    }

    public int nextcsExecutionTimer(){
        return (int) (-csExecTimer*Math.log(random.nextDouble()));
    }

}
