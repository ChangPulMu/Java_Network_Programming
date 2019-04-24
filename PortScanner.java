
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class PortScanner {
	public static void main(String[] args) throws Exception {

		if (args.length != 3) {
			System.out.println("Usage: Classname hostName startPort endPort");
			System.exit(1);
		}

		String host = args[0];
		int start = Integer.parseInt(args[1]);
		int end = Integer.parseInt(args[2]);
		
		InetAddress inetAddress = InetAddress.getByName(host);
		String hostName = inetAddress.getHostName();
		
		for(int port=start;port<=end;port++) {
			try {
				Socket cSocket = new Socket(hostName, port);
				System.out.println(hostName+" is listening on port "+port);
				cSocket.close();
			} catch(IOException e) {
				System.out.println(hostName+" is not listening on port "+port);
			}
		}
	}
}