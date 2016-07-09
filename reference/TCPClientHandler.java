import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClientHandler implements Runnable{

	private Socket socket = null;
	
	public TCPClientHandler(Socket socket){
		this.socket=socket;

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
			String receivedMsg = null;	
			while(reader.hasNextLine()){
				receivedMsg = reader.nextLine();
				System.out.println("Clinet handler received msg:"+receivedMsg);
				MessageHandlerService.getInstance().receive(receivedMsg);
							
			}
				

	}



}
