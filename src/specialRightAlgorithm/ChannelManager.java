package specialRightAlgorithm;


import channelTranportLayer.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yifan on 7/18/16.
 */
public class ChannelManager {
    private ChannelManager(){
        channels = new HashMap<>();
    }
    private static ChannelManager singleton = new ChannelManager();
    public static ChannelManager getManager(){
        return singleton;
    }

    private Map<Integer, Channel> channels;

    public void setChannels(Map<Integer, Channel> map){
        channels = map;
    }
    public void addOrSetChannel(Channel ch){
        channels.put(ch.channelID, ch);
    }
    public Channel getChannel(int id){
        return channels.get(id);
    }

    /**TODO check how Channel class implemented!
     * 为什么要把ChannelManager放在这个package
     */

    //TODO 真的不用CONCURRENT CONTROLL 吗???

    public void send(int dst, String msg){
        Channel ch = getChannel(dst);
        ch.send(msg);
    }
    public void broadcast(String message){
        for(Channel ch : channels.values()){
            ch.send(message);
        }
    }

    public void multicast(String message, List<Integer> desList){
        for(int nbid: desList){
            send(nbid, message);
        }
    }

    public void closeAll(){
        for(Channel ch : channels.values()){
            ch.close();
        }
    }
}
