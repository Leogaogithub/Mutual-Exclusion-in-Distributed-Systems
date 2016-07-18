package specialRightAlgorithm;

import shareUtil.IMutualExclusiveStrategy;
import shareUtil.IreceiveMessage;
import shareUtil.MessageSenderService;

/**
 * Created by Yifan on 7/18/16.
 */
public class Alogrithm implements IMutualExclusiveStrategy,
        IreceiveMessage {

    //TODO need a listening thread.

    private int me;
    private int N;

    //usage?    i.e., scalar clock!
    private int our_seq_num;
    private int hgst_seq_num;
    private boolean[] rights;
    private boolean using;
    private boolean waiting;
    private boolean[] rep_deferred;

    Alogrithm(int myid, int allnum){
        //assume nodeID start from 0
        me = myid;
        N = allnum;
        our_seq_num = 0;
        hgst_seq_num = 0;
        rights = new boolean[N];
        using = false;
        waiting = false;
        rep_deferred = new boolean[N];
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
                MessageSenderService.getInstance().send(tmp.toString(), j);
            }
        }
        //fuck it! busy waiting
        while(true){
            boolean flag = true;
            for(int j = 0; j < N; j++){
                if(j != me && !rights[j]) flag = false;
            }
            if(flag) break;
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
                MessageSenderService.getInstance().send(
                        MessageFactory.createMessage("REPLY", me, j).toString(), j);

            }
        }
    }

    @Override
    public void receive(String message, int channel) {
        Message msg = MessageParser.parseString(message);
        //TODO ...
    }

    synchronized private void treatRequest(int their_seq_num, int from){
        boolean our_priority;
        hgst_seq_num = Math.max(hgst_seq_num, their_seq_num);
        our_priority = their_seq_num > our_seq_num ||
                (their_seq_num == our_seq_num && from > me);
        if(using || (waiting && our_priority))
            rep_deferred[from] = true;
        if((!(using || waiting)) || ((waiting && rights[from]) && !our_priority)){
            rights[from] = false;
            //send REPLY(ME, J) TO J
            Message tmp = MessageFactory.createMessage("REPLY", me, from);
            MessageSenderService.getInstance().send(tmp.toString(), from);
        }
        if((waiting && rights[from]) && !our_priority){
            rights[from] = false;
            //send REPLY(ME, J) TO J
            Message tmp = MessageFactory.createMessage("REPLY", me, from);
            MessageSenderService.getInstance().send(tmp.toString(), from);
            //send REQUEST(our_sequence_num, me, j) to j
            tmp = MessageFactory.createMessage("REQUEST", me, from);
            tmp.setBody(String.valueOf(our_seq_num));
            MessageSenderService.getInstance().send(tmp.toString(), from);
        }
    }

    synchronized private void treatReply(int from){
        rights[from] = true;
    }
}
