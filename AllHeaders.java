import java.net.*;
import java.io.*;

public class AllHeaders {

	public static void main(String args[]) {
		if (args.length < 1) {
			  System.out.println("Usage: java Classname url_with_http_for_header_view");
		  return;
		}
		
		for(int i = 0;i < args.length;i++) {
			try {
				URL u = new URL(args[i]);
				URLConnection uc = u.openConnection();
				
				for(int j = 1;;j++) {
					String header = uc.getHeaderField(j);
					
					if(header == null) break;
					
					System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
				} // end for
				
				System.out.println();
			} catch (MalformedURLException ex) {
				System.err.println(args[i] + " is not a URL.");
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}
