import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
	
	static String eServer = "";
	static int ePort = 0000;
	// static int clientID = -1;
	static Socket chatSocket = null;
	
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Usage: Classname ServerName ServerPort");
			System.exit(1);
		}
		
		eServer = args[0];
		ePort = Integer.parseInt(args[1]);
		
		try {
			chatSocket = new Socket(eServer, ePort);
			// clientID = chatSocket.getLocalPort();
		} catch (BindException b) {
			System.out.println("Can't bind on: "+ePort);
			System.exit(1);
		} catch (IOException i) {
			System.out.println(i);
			System.exit(1);
		}
		new Thread(new ClientReceiver(chatSocket)).start();
		new Thread(new ClientSender(chatSocket)).start();
	}
}

class ClientSender implements Runnable {
	private Socket chatSocket = null;
	
	ClientSender(Socket socket){
		this.chatSocket = socket;
	}
	
	public void run() {
		Scanner KeyIn = null;
		PrintWriter out = null;
		
		try {
			KeyIn = new Scanner(System.in);
			out = new PrintWriter(chatSocket.getOutputStream(),true);
			
			String userInput = "";
			System.out.print("Your are "+chatSocket.getLocalPort()
			+", Type Message (\"Bye.\" to leave)\n");
			
			while((userInput=KeyIn.nextLine())!=null) {
				out.println(userInput);
				out.flush();
				
				if(userInput.equalsIgnoreCase("Bye."))
					break;
			}
			
			KeyIn.close();
			out.close();
			
			chatSocket.close();
		} catch(IOException i) {
			try {
				if(out!=null)
					out.close();
				if(KeyIn!=null)
					KeyIn.close();
				if(chatSocket!=null)
					chatSocket.close();
			} catch(IOException e) {}
			System.exit(1);
		}
	}
}

class ClientReceiver implements Runnable{
	private Socket chatSocket = null;
	
	ClientReceiver(Socket socket){
		this.chatSocket = socket;
	}
	
	public void run() {
		while(chatSocket.isConnected()) {
			BufferedReader in = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
				String readSome = null;
				
				while((readSome = in.readLine())!=null) {
					System.out.println(readSome);
				}
				
				in.close();
				chatSocket.close();
			} catch(IOException i) {
				try {
					if(in!=null)
						in.close();
					if(chatSocket!=null)
						chatSocket.close();
				} catch(IOException e) {
				}
				// SocketTimeout, "Bye.", Max connection and more
				System.out.println("Leave.");
				System.exit(1);
			}
		}
	}
}

