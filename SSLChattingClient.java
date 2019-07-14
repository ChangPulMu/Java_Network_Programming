package SSLFileTransferChat;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLChattingClient {
	
	static String serverIP = "";
	static int serverPort = 0000;
	static RmiChatting rmiChatting;
	
	static SSLSocketFactory socketfactory = null;
	static SSLSocket chatting = null;
	
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.out.println("Usage: Classname ServerName ServerPort ");
			System.exit(1);
		}

		serverIP = args[0];
		serverPort = Integer.parseInt(args[1]); // Server Port
		
		System.out.println("OK");
		
		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "970117");
						
			socketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			chatting = (SSLSocket) socketfactory.createSocket(serverIP, serverPort);
						
			String[] supported = socketfactory.getSupportedCipherSuites();
			chatting.setEnabledCipherSuites(supported);
						
			chatting.startHandshake();
						
			rmiChatting = (RmiChatting)Naming.lookup("rmi://" + chatting.getInetAddress().getHostAddress() +"/RmiChatting");
			
		} catch (BindException b) {
			System.out.println("Can't bind on: " + serverPort);
			System.exit(1);
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println(i);
			System.exit(1);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		new Thread(new Receiver(chatting)).start();
		new Thread(new Sender(chatting, rmiChatting)).start();
	}
}
