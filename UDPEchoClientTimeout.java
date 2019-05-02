import java.net.*;
import java.io.*; 

public class UDPEchoClientTimeout {

	private static final int TIMEOUT = 3000;   // Resend timeout (milliseconds)

	private static InetAddress uServer = null;
	private static int uPort = 0000;
	
	public static void main(String args[]) { 

		if (args.length != 2) {
			System.out.println("Usage: Classname ServerName ServerPort");
			System.exit(1);
		}
		try {
			uServer = InetAddress.getByName(args[0]);
			uPort = Integer.parseInt(args[1]);
			System.out.println ("Attemping to connect to " + uServer + 
                    ") via UDP port "+uPort);
		} catch (UnknownHostException e) {
			System.out.println(e);
		}
	    new UDPEchoClientTimeout (uServer);
	}
	 
	public UDPEchoClientTimeout(InetAddress uServer) {

		String sentence;

		DatagramSocket uSocket = null;
		DatagramPacket sendPacket = null;
	      
		try {
			byte[] sendData = new byte[80];
			byte[] receData = new byte[80];
			uSocket = new DatagramSocket();
			
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter Message : ");
			
			sentence = fromUser.readLine();
			sendData = sentence.getBytes();
			
			sendPacket = new DatagramPacket(sendData, sendData.length, uServer, uPort);
			uSocket.send(sendPacket);
			
			DatagramPacket receivePacket = new DatagramPacket(receData, receData.length);
			
			System.out.println("Waiting for return packet....\n");
			
			uSocket.setSoTimeout(TIMEOUT);
			
			try {
				uSocket.receive(receivePacket);
				String modifiedSentence = new String(receivePacket.getData());
				
				InetAddress returnIPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				
				System.out.println("From server at : "+returnIPAddress+":"+port);
				System.out.println("Message : "+modifiedSentence);
			} catch(SocketTimeoutException ste) {
				System.out.println("Timeout Occurred : Packet assumed Lost");
			}
			
			uSocket.close();
		} catch (UnknownHostException ex) {
			System.err.println(ex);
		} catch (IOException e) {
			System.out.println("hello");
			System.err.println(e);
		}
	} 
}