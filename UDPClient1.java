
 
import java.io.*;
import java.net.*;
 
class TestUDPClient2 {
	public static void main(String[] args) throws IOException {
		new TestUDPClient2().go();
	}
 
	private void go() {
		ClientSendThread send = new ClientSendThread();
		new Thread(send).start();
 
		ClientRecvThread recv = new ClientRecvThread();
		new Thread(recv).start();
	}
 
	class ClientSendThread implements Runnable {
 
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
 
//					System.out.println("-----buf.length-------" + buf.length);
					DatagramPacket dp = new DatagramPacket(buf, buf.length,
							new InetSocketAddress("127.0.0.1", 5678));
					ds.send(dp);
					buf = null;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	class ClientRecvThread implements Runnable {
 
		@Override
		public void run() {
			byte[] buf = new byte[1024];
			
			 //The port on the receiving end needs to be specified, otherwise the sender does not know which port to send the packet to.
			DatagramSocket ds;
			try {
				ds = new DatagramSocket(5679);
			
				String message = "";
				DatagramPacket dp = null;
				while(true){
					dp = new DatagramPacket(buf, buf.length);
					ds.receive(dp);
					
					message = new String(buf,0,dp.getLength());
					 System.out.println("Received server information: "+message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
