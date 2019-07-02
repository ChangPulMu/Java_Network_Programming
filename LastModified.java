import java.net.*;
import java.io.*;
import java.util.*;

 public class LastModified {

	public static void main(String args[]) {
		if (args.length < 1) {
			  System.out.println("Usage: java Classname url_with_http_for_lastmodified_view");
		  return;
		}
		
		for(int i = 0; i < args.length; i++) {
			try {
				URL u = new URL(args[i]);
				HttpURLConnection http = (HttpURLConnection)u.openConnection();
				
				http.setRequestMethod("HEAD");
				
				System.out.println(u + " was Last modified at " + new Date(http.getLastModified()));
			} catch (MalformedURLException mex) {
				System.err.println(mex);
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}  
}
