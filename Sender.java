package SSLFileTransferChat;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.SSLSocket;

public class Sender implements Runnable {
	SSLSocket chatting = null;
	RmiChatting rmiChatting;
	String input;
	Scanner in = new Scanner(System.in);
	PrintWriter out = null;
	int mood = 0;
	String tmp = null;
	
	public Sender(SSLSocket chatting, RmiChatting rmiChat) {
		this.chatting = chatting;
		this.rmiChatting = rmiChat;
	}
	
	public void run() {
		try {
			out = new PrintWriter(chatting.getOutputStream(), true);
			
			System.out.println("Your IP : " + chatting.getInetAddress().getHostAddress() + "Your Port : " + chatting.getLocalPort());
			System.out.println(rmiChatting.help());
			
			while((input = in.nextLine()) != null) {
				if(input.equalsIgnoreCase("/Bye."))
					break;
				else if(input.equals("/Ready.") && mood != 1) {
					mood = rmiChatting.ready();
					System.out.println("Convert to Ready!!");
					out.println(input);
					out.flush();
				}
				else if(input.equals("/UnReady.") && mood != 0) {
					System.out.println("Convert to UnReady..");
					mood = rmiChatting.unready();
					out.println(input);
					out.flush();
				}
				else if(input.equals("/Start.") && mood != 2 && mood != 3) {
					System.out.println("Kill the killer!");
					mood = rmiChatting.unkilled();
					out.println(input);
					out.flush();
				}
				else if(input.equals("/Kill.")) {
					System.out.println("Who do you want to kill?");
					input = in.nextLine();
					out.println("/Kill."+input);
					out.flush();
					mood = rmiChatting.killed();
				}
				else if(input.equals("/Help.")) {
					String description = rmiChatting.help();
					System.out.println(description);
					out.println(input);
					out.flush();
				}
				else {
					switch(mood) {
					case 0:
						tmp = "[UnReady] ";
						break;
					case 1:
						tmp = "[Ready] ";
						break;
					case 2:
						tmp = "[Unkilled] ";
						break;
					case 3:
						tmp = "[Killed] ";
						break;
					}
					input = tmp + input;
					
					out.println(input);
					out.flush();
				}
			}
			
			chatting.close();
			in.close();
			out.close();
		} catch(IOException i) {
			try {
				if (chatting != null)
					chatting.close();
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {}
			
			System.exit(1);
		}
	}
}