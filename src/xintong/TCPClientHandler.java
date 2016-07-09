package xintong;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import leo.Node;

public class TCPClientHandler implements Runnable{
	int destChannelID;
	private Socket socket = null;
	
	public TCPClientHandler(Socket socket, int channelID){
		this.socket=socket;
		this.destChannelID=channelID;
	}
	
	@Override
	public void run() {
		
			Scanner reader=null;
			try {
				reader = new Scanner(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(true){
				String receivedMsg = null;	
				while(reader.hasNextLine()){
					receivedMsg = reader.nextLine();
					System.out.println("Clinet handler received msg:"+receivedMsg);
					MessageReceiveService.getInstance().receive(receivedMsg, destChannelID);
			
				}
			}

	}



}
