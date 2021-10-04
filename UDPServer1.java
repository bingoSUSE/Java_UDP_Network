
 
import java.io.*;
import java.net.*;
 

class TestUDPServer2 {
 
	public static void main(String[] args) throws IOException {
		new TestUDPServer2().go();
	}
	
	private void go() {
		 // Server starts 2 threads, 1 send, 1 receive
		ServerSendThread send = new ServerSendThread();
		new Thread(send).start();
		
		ServerRecvThread recv = new ServerRecvThread();
		new Thread(recv).start();
	}
 
 //server send thread
	class ServerSendThread implements Runnable{
 
		@Override
		public void run() {
			try {
				DatagramSocket ds = new DatagramSocket();
				String str = "";
				byte[] buf = null;
				while (true) {
					 System.out.println("Please enter >");
					BufferedReader br = new BufferedReader(
							new InputStreamReader(System.in));
					str = br.readLine();
					if ("bye".equals(str))
					
					buf = str.getBytes();
 
					DatagramPacket dp = new DatagramPacket(buf, buf.length,
							new InetSocketAddress("127.0.0.1", 5679));
					ds.send(dp);
					buf = null;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
 // server receives thread
	class ServerRecvThread implements Runnable{
 
		@Override
		public void run() {
			byte[] buf = new byte[1024];
			
			 //The port on the receiving end needs to be specified, otherwise the sender does not know which port to send the packet to.
			DatagramSocket ds;
			try {
				ds = new DatagramSocket(5678);
			
				String message = "";
				while(true){
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					ds.receive(dp);
					
					message = new String(buf,0,dp.getlength());
					 System.out.println("The information received on the client side is: "+message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
