package xintong;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import leo.Node;
public class TCPServerListener implements Runnable
{
	int port;
	ServerSocket serversock;
	Node local;
	public TCPServerListener(int port,Node node) throws IOException{
		this.port=port;
		this.serversock = new ServerSocket(port);
		this.local=node;
		
	}
	
	public void close() throws IOException{
		serversock.close();
	}

	@Override
	public void run()
	{
			System.out.println("TCP sever starts listening on"+port);
			while(true){
				Socket socket = null;
				try {
						socket = serversock.accept();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				Scanner reader = null;
				try {
					reader = new Scanner(socket.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String input=reader.nextLine();
				System.out.println("received remote notify:"+input);
				int channelID=-1;
				if(input.startsWith("NODEID:"))
					channelID=input.charAt(7)-'0';
				
				new Thread(
						new TCPClientHandler(socket,channelID)
						).start();  //should use start

			}
			
	
	}



}
