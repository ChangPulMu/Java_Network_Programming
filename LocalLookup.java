import java.net.*;
 
public class LocalLookup {
	public static void main(String[] args){
		InetAddress inetaddr = null;
		
		try {
			inetaddr = InetAddress.getLocalHost();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("Hostname : "+inetaddr.getHostName());
		System.out.println("Fully qualified domainname : "+inetaddr.getCanonicalHostName());
		
		System.out.println("IP address : "+inetaddr.getHostAddress());
		System.out.println("Hash value of ip : "+inetaddr.hashCode());
	}
}
