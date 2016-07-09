import java.io.*;
import java.net.*;
public class TCPServerService implements Runnable
{
	int port;
	ServerSocket serversock;
	public TCPServerService(int port) throws IOException{
		this.port=port;
		this.serversock = new ServerSocket(port);
		
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
				
				new Thread(
					new TCPClientHandler(socket)
						).start();  //should use start
				
			}
			
			
	
	}



}
