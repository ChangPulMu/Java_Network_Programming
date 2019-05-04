import java.net.*;

public class UDPEchoServer {

	public static void main(String args[]) throws Exception { 

		if (args.length != 1) {
			System.out.println("Usage: Classname ServerPort");
			System.exit(1);
		}
		int uPort = Integer.parseInt(args[0]);
		
		DatagramSocket uSocket = null;
		
		try { 
			uSocket = new DatagramSocket(uPort); 
	  
			while(true) {
				byte[] receiveData = new byte[80];
				byte[] sendData = new byte[80];
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				
				System.out.println("Waiting for datagram packet");
				
				uSocket.receive(receivePacket);
				
				String sentence = new String(receivePacket.getData());
				String capitalizedSentence = sentence.toUpperCase();
				
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendData = capitalizedSentence.getBytes();
				
				System.out.println("From : "+IPAddress+":"+port);
				System.out.println("Message : "+sentence);
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				uSocket.send(sendPacket);
			}
		} catch (SocketException ex) {
			System.out.println("UDP Port "+uPort+" is occupied.");
			System.exit(1);
		}
		uSocket.close();
	} 
}
