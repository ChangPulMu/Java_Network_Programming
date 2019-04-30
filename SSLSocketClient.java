
import java.io.*;
import javax.net.ssl.*;

public class SSLSocketClient {
	
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = System.out;
		
		SSLSocketFactory f = null;
		SSLSocket c = null;
		
		BufferedWriter w = null;
		BufferedReader r = null;

		String sServer = "";
		int sPort = -1;
		
		if (args.length != 2) {
			System.out.println("Usage: Classname ServerName securePort");
			System.exit(1);
		}
		sServer = args[0];
		sPort = Integer.parseInt(args[1]);
		
		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			
			f = (SSLSocketFactory) SSLSocketFactory.getDefault();
			c = (SSLSocket) f.createSocket(sServer, sPort);
			
			String[] supported = c.getSupportedCipherSuites();
			c.setEnabledCipherSuites(supported);
			printSocketInfo(c);
			
			c.startHandshake();
			
			w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
			r = new BufferedReader(new InputStreamReader(c.getInputStream()));
			
			String m = null;
			
			while((m=r.readLine())!=null) {
				out.println(m);
				m = in.readLine();
				w.write(m,0,m.length());
				w.newLine();
				w.flush();
			}
			w.close();
			r.close();
			c.close();
		} catch(IOException io) {
			try {
				w.close();
				r.close();
				c.close();
			} catch(IOException i) {
			}
		}
	}
	
	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: "+s.getClass());
		System.out.println("   Remote address = "
				+s.getInetAddress().toString());
		System.out.println("   Remote port = "+s.getPort());
		System.out.println("   Local socket address = "
				+s.getLocalSocketAddress().toString());
		System.out.println("   Local address = "
				+s.getLocalAddress().toString());
		System.out.println("   Local port = "+s.getLocalPort());
		System.out.println("   Need client authentication = "
				+s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		System.out.println("   Cipher suite = "+ss.getCipherSuite());
		System.out.println("   Protocol = "+ss.getProtocol());
	}
}