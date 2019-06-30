

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
 
public class HTTPUrlConnectionTest {
 
	private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception {
 
		if (args.length != 1) {
			System.out.println("Usage: Classname option [enter]"); // option means get or post
			System.exit(1);
		} else if (args[0].equals("get")) {
			HTTPUrlConnectionTest http = new HTTPUrlConnectionTest();
			System.out.println("GET Request Using HttpURLConnection");
			http.sendGet();
		} else if (args[0].equals("post")) {
			HTTPUrlConnectionTest http = new HTTPUrlConnectionTest();
			System.out.println("POST Request Using HttpURLConnection");
			http.sendPost();
		} else {
			System.out.println("Only use 'get' or 'post' for option");
			System.exit(1);
		}
	}
 
	private void sendGet() throws Exception {
		// http://www.google.com/search?q=something <- replace for some site
		String searchWord = "soongsil";
		StringBuilder stringBuilder = new StringBuilder("https://www.google.com/search");
		stringBuilder.append("?q=");
		stringBuilder.append(URLEncoder.encode(searchWord, "UTF-8"));
		
		URL obj = new URL(stringBuilder.toString());
		
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Charset", "UTF-8");
		
		System.out.println("\nSending request to URL : " + obj);
		System.out.println("Response Code : " + con.getResponseCode());
		System.out.println("Response Message : " + con.getResponseMessage());
		
		System.out.println(con.getRequestMethod());
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line;
		StringBuffer response = new StringBuffer();
		
		while((line = in.readLine()) != null)
			response.append(line);
		
		in.close();
		
		System.out.println(response.toString());
		System.out.println("\nfinish~");
	}
	
	private void sendPost() throws Exception {
		String url = "http://ptsv2.com/t/youngjong/post";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setDoOutput(true);
		con.setUseCaches(false);
		
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("ContentType", "x-www-form-urlencoded");
		
		StringBuilder tokenUri = new StringBuilder("param_yoyo=");
		tokenUri.append(URLEncoder.encode("param_yoyo", "UTF-8")); // fitted just for test for test_server
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
		// write uri or body
		
		outputStreamWriter.write(tokenUri.toString());
		outputStreamWriter.write(tokenUri.toString());
		outputStreamWriter.flush();
		
		int responseCode = con.getResponseCode();
		
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + tokenUri);
		System.out.println("Reponse Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null)
			response.append(inputLine);
		
		in.close();
		
		System.out.println(response.toString());
		System.out.println("\nfinish~");
	}
}