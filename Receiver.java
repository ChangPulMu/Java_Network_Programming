package SSLFileTransferChat;

import java.io.*;
import java.net.*;

import javax.net.ssl.SSLSocket;

public class Receiver implements Runnable {
	SSLSocket chatting = null; 
	String input = null; 
	BufferedReader in = null;
	
	public Receiver(SSLSocket chatting) {
		this.chatting = chatting;
	}
	
	public void run() {
		while (!chatting.isClosed()) {
			try {
				in = new BufferedReader(new InputStreamReader(chatting.getInputStream()));
				
				while ((input = in.readLine()) != null)
					System.out.println(input);

				chatting.close();
				in.close();
			} catch (IOException i) {
				try {
					if (in != null)
						in.close();
					if (chatting != null)
						chatting.close();
				} catch (IOException e) {}
				
				System.out.println("Leave.");
				System.exit(1);
			}
		}
		try {
			if (in != null)
				in.close();
			if (chatting != null)
				chatting.close();
		} catch (IOException e) {}
		
		System.out.println("Leave.");
		System.exit(1);
	}
}
