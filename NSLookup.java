
import java.net.*;

public class NSLookup {
	public static void main(String args[]){
            
		if(args.length != 1){
			System.out.println("Usage: Classname IP or domain name");
			System.exit(0);
		}
            
		InetAddress inetaddr[] = null;
		
		try {
			inetaddr = InetAddress.getAllByName(args[0]);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<inetaddr.length;i++) {
			System.out.println("----->");
			System.out.println(inetaddr[i].getHostName());
			System.out.println(inetaddr[i].getHostAddress());
			System.out.println(inetaddr[i].toString());
			System.out.println("\r");
		}
	}
}