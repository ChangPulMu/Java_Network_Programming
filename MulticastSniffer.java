import java.net.*;
import java.io.*;

public class MulticastSniffer {

	public static void main(String[] args) {
  
		InetAddress group = null;
		int port = 0;
  
		try {
			group = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
		} catch (Exception ex) {
			System.err.println(
					"Usage: java MulticastSniffer multicast_address port");
			System.exit(1);
		}
  
		MulticastSocket ms = null;
  
		try {
			ms = new MulticastSocket(port);
			ms.joinGroup(group);
			
			byte[] buffer = new byte[256];
			
			while(true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				
				ms.receive(dp);
				
				String s = new String(dp.getData());
				
				System.out.println("from "+dp.getAddress().toString()+", Receive: "+s);
			}
		} catch(IOException ex) {
			System.err.println(ex);
		} finally {
			if(ms != null) {
				try {
					ms.leaveGroup(group);
					ms.close();
				} catch(IOException ex) {}
			}
		}
	}
}
