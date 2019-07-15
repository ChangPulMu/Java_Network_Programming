package SSLFileTransferChat;

import java.net.*;
import java.io.*;

public class SSLChattingServerRunnable implements Runnable {
	SSLChattingServer server = null; 
	Socket client = null;
	PrintWriter out = null;
	BufferedReader in = null;
	int clientPort; // client port
	
	public SSLChattingServerRunnable(SSLChattingServer server, Socket client) {
		this.server = server;
		this.client = client;
		clientPort = client.getPort();
		
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	public void run() {
		try {
			String input = null; 
			
			while ((input = in.readLine()) != null) {
				
				if (input.equals("/Bye."))
					break;
				else if (input.equals("/Ready.")) {
					SSLChattingServer.ready(clientPort);
				} else if (input.equals("/UnReady.")) {
					SSLChattingServer.unready(clientPort);
				} else if (input.equals("/Start.")) {
					SSLChattingServer.whoAmI(clientPort);
				} else if (input.equals("/Help.")){
					SSLChattingServer.whoAmI(clientPort);
				} else {
					if (input.substring(0,6).equals("/Kill.")) {
						int killedPort = Integer.parseInt(input.substring(6));
						out.println(killedPort + "를 투표하였습니다.");
						SSLChattingServer.killed(clientPort, killedPort);
					} else {
						SSLChattingServer.printMessage(clientPort,clientPort + ":" + input);
					}
				}

			}
			
			SSLChattingServer.deleteClient(clientPort);
			
		} catch (SocketTimeoutException ste) {
			System.out.println("Socket timeout Occured,force close()" + clientPort);
			SSLChattingServer.deleteClient(clientPort);
		} catch (IOException e) {
			SSLChattingServer.deleteClient(clientPort);
		}
	}
	
	public void close() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (client != null)
				client.close();
		} catch (IOException i) {}
	}
}
