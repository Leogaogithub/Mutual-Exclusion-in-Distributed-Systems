package leo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPChannel extends Channel{
	public Socket socket;
	PrintWriter writer = null;
	BufferedReader reader = null;
	
	TCPChannel(Socket socket, int channelId){
		super(channelId);
		this.socket = socket;
		try {
			writer = new PrintWriter(socket.getOutputStream());		
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}

	
	@Override
	public String receive(){
		String msg = "";
		try {
			msg = reader.readLine();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return msg;
	}
	
	public void send(String message){
		writer.println(message);			
	}
	
	public void close(){
		try {			
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
