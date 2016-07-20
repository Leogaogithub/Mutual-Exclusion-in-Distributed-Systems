package specialRightAlgorithm;

import shareUtil.*;

/**
 * Created by Yifan on 7/18/16.
 */
public class Alogrithm implements IMutualExclusiveStrategy,
        IreceiveMessageWithClock {


    private int me;
    private int N;

    //usage?    i.e., scalar clock!
    private int our_seq_num;
    private int hgst_seq_num;
    private boolean[] rights;
    private boolean using;
    private boolean waiting;
    private boolean[] rep_deferred;

    public Alogrithm(int myid, int allnum){
        //assume nodeID start from 0
        me = myid;
        N = allnum;
        our_seq_num = 0;
        hgst_seq_num = 0;
        rights = new boolean[N];
        using = false;
        waiting = false;
        rep_deferred = new boolean[N];
        MessageReceiveService.getInstance().registerWithClock(this);

    }

    @Override
    synchronized public void csEnter() {
        waiting = true;
        our_seq_num  = hgst_seq_num + 1;
        for(int j = 0; j < N; j++){
            if(j != me && !rights[j]){
                //send REQUEST(our_seq_num, me, j) to j
                Message tmp = MessageFactory.createMessage("REQUEST", me, j);
                tmp.setBody(String.valueOf(our_seq_num));
//                System.out.println("Send: " + tmp.toString());
                MessageSenderService.getInstance().send(tmp.toString(), j, System.currentTimeMillis());
            }
        }
        boolean flag = true;
        for(int j = 0; j < N; j++){
            if(j != me && !rights[j]) flag = false;
        }
//        System.out.println("Enter condition: "+ print(rights));
        while(!flag){
            try{
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            for(int j = 0; j < N; j++){
                if(j != me && !rights[j]) flag = false;
            }
        }
        waiting = false;
        using = true;
    }

    @Override
    synchronized public void csLeave() {
        using = false;
        for(int j = 0; j<N; j++){
            if(rep_deferred[j]) {
                rights[j] = false;
                rep_deferred[j] = false;
                //send REPLY(me, j) to j
                Message tmp = MessageFactory.createMessage("REPLY", me, j);
                MessageSenderService.getInstance().send(tmp.toString(), j, System.currentTimeMillis());
            }
        }
    }

    @Override
    public void receive(String message, int channel, long milliseconds) {
//        System.out.println("Receive: "+message);
        Message msg = MessageParser.parseString(message);
        if(msg.getType().equals("REQUEST"))
            treatRequest(Integer.parseInt(msg.getBody()), msg.getFrom());
        if(msg.getType().equals("REPLY"))
            treatReply(msg.getFrom());
    }


    synchronized private void treatRequest(int their_seq_num, int from){
        boolean our_priority;
        hgst_seq_num = Math.max(hgst_seq_num, their_seq_num);
        our_priority = their_seq_num > our_seq_num ||
                ((their_seq_num == our_seq_num) && from > me);
        if(using || (waiting && our_priority))
            rep_deferred[from] = true;
        if((!(using || waiting)) || (waiting && !rights[from] && !our_priority)){
            rights[from] = false;
            //send REPLY(ME, J) TO J
            Message tmp = MessageFactory.createMessage("REPLY", me, from);
//            System.out.println("Send: " + tmp.toString());
            MessageSenderService.getInstance().send(tmp.toString(), from, System.currentTimeMillis());
        }
        if(waiting && rights[from] && !our_priority){
            rights[from] = false;
            //send REPLY(ME, J) TO J
            Message tmp = MessageFactory.createMessage("REPLY", me, from);
//            System.out.println("Send: " + tmp.toString());
            MessageSenderService.getInstance().send(tmp.toString(), from, System.currentTimeMillis());
            //send REQUEST(our_sequence_num, me, j) to j
            tmp = MessageFactory.createMessage("REQUEST", me, from);
            tmp.setBody(String.valueOf(our_seq_num));
//            System.out.println("Send: " + tmp.toString());
            MessageSenderService.getInstance().send(tmp.toString(), from, System.currentTimeMillis());
        }
//        System.out.println("After treat Request: "+print(rights));
        notifyAll();
    }

    synchronized private void treatReply(int from){
        rights[from] = true;
//        System.out.println("After treat Reply: "+print(rights));
        notifyAll();
    }

    private String print(boolean[] qwer){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<qwer.length; i++){
            sb.append(i).append(":").append(qwer[i]).append(";");
        }
        return sb.toString();
    }
}
