import java.net.*;
import java.io.*;

public class WhoisClient {

	public final static int DEFAULT_PORT = 43;
	public final static String DEFAULT_HOST = "whois.internic.net";

	// try ssu.ac.kr and then korea.com
	
	public static void main(String[] args) {
		InetAddress server;
		
		try {
			server = InetAddress.getByName(DEFAULT_HOST);
		} catch (UnknownHostException e) {
			System.err.println("Error: Could not locate default host "
					+ DEFAULT_HOST);
			System.err.println("Check to make sure Internet connection and that DNS is funtioning");
			System.err.println("Usage: java Classname host port");         
			return;
		}       
    
		int port = DEFAULT_PORT;
		
		try {
			Socket cSocket = new Socket(server,port);
			Writer out = new OutputStreamWriter(cSocket.getOutputStream(),"8859_1");
			
			for(int i=0;i<args.length;i++) {
				out.write(args[i]+" ");
				out.write("\r\n");
				out.flush();
			}
			
			InputStream in = new BufferedInputStream(cSocket.getInputStream());
			
			int c;
			
			while((c=in.read())!=-1)
				System.out.write(c);
			
			cSocket.close();
		} catch(IOException e) {
			System.err.println(e);
		}
	}

}
