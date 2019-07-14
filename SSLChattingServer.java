package SSLFileTransferChat;

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.security.*;
import java.security.cert.CertificateException;

import javax.net.ssl.*;

public class SSLChattingServer implements Runnable {
	
	static SSLChattingServerRunnable clients[] = new SSLChattingServerRunnable[3];
	static int clientNumber = 0;
	int serverPort;
	static int isReady = 0;
	static int kill = 0;
	static int[] killedSomeone = new int[clients.length];
	static int killCount = 0;
	
	KeyStore keystore;
	KeyManagerFactory keymanagerfactory;
	SSLContext sslcontext;
	String runRoot = "/Users/suhyeongcho/eclipse-workspace/SSLFileTransferChat/src/.keystore/SSLSocketServerKey";
	
	SSLServerSocketFactory serversocketfactory = null;
	SSLServerSocket server = null;
	SSLSocket client = null;
	
	char keyStorePass[] = "970117".toCharArray(); // never use hard-coding
	char keyPass[] = "970117".toCharArray(); // never use hard-coding
	
	public SSLChattingServer(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			System.out.println("Usage: Classname ServerPort");
			System.exit(1);
		}
		
		int serverPort = Integer.parseInt(args[0]);
		
		new Thread(new SSLChattingServer(serverPort)).start();
	}
	
	public void run() {
		
		try {
			keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(runRoot), keyStorePass);
			
			keymanagerfactory = KeyManagerFactory.getInstance("SunX509");
			keymanagerfactory.init(keystore, keyPass);
			
			sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(keymanagerfactory.getKeyManagers(), null, null);
			
			SSLEngine sslengine = sslcontext.createSSLEngine();
			sslengine.setUseClientMode(false);
			
			serversocketfactory = sslcontext.getServerSocketFactory();
			server = (SSLServerSocket) serversocketfactory.createServerSocket(serverPort); 
			System.out.println("Server is created : Server Socket is created on Port " + serverPort);
			
			LocateRegistry.createRegistry(1099);
			
			while (true) {
				
				if (clientNumber < clients.length) {
					try {
						client = (SSLSocket)server.accept(); // accept client
						// client.setSoTimeout(40000); 
					} catch (IOException i) {
						System.out.println("Accept() fail: " + i);
					}
					
					clients[clientNumber] = new SSLChattingServerRunnable(this, client);
					
					new Thread(clients[clientNumber]).start(); 
					
					clientNumber++;
					
					System.out.println("Client is connected!");
					System.out.println("IP : " + client.getInetAddress().getHostAddress() + " / Port : " + client.getPort() + " / CurrentClient Number : " + clientNumber);
					
					try {
						RmiChatting remoteObject = new RmiChattingImpl();
						Naming.rebind("rmi://" + client.getInetAddress().getHostAddress() + ":1099/RmiChatting", remoteObject);
						
						System.out.println("Finish Register to Remote Object");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
				} else {
					try {
						Socket dummySocket = server.accept();
						SSLChattingServerRunnable dummyRunnable = new SSLChattingServerRunnable(this, dummySocket);
						new Thread(dummyRunnable);
						
						dummyRunnable.out.println(dummySocket.getPort() + " < Sorry maximum user connected now");
						System.out.println("Client refused by Maximum connection : " + clients.length);
						
						dummyRunnable.close(); 
					} catch (IOException i) {
						System.out.println(i);
					}
				}
			}
				
			
		} catch (BindException b) {
			System.out.println("Can't bind on: " + serverPort);
		} catch (IOException i) {
			System.out.println(i);
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (CertificateException e1) {
			e1.printStackTrace();
		} catch (UnrecoverableKeyException e1) {
			e1.printStackTrace();
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}
	
	public static void ready(int clientPort) {
		isReady++;
		if(isReady == clients.length) {
			for (int i=0;i<clients.length;i++) {
				clients[i].out.println("It's now Starting the game!!!!");
				clients[i].out.println("\"/Start.\"를 입력하세요.(큰따옴표 제외)");

			}
			
			int kill = (int)(Math.random() * clientNumber);
			
			for(int i = 0;i<clientNumber;i++) {
				if(i == kill)
					clients[i].out.println("당신은 마피아 입니다. 시민을 죽이세요");
				else
					clients[i].out.println("당신은 시민 입니다. 마피아를 죽이세요");
			}
		}
	}

	public static void unready(int clientPort) {
		isReady--;
	}
	
	public static void whoAmI(int clientPort) {
		int my = 0;
		for (int i = 0; i < clientNumber; i++)
			if (clients[i].clientPort == clientPort)
				my = i;
		clients[my].out.println("본 인 : " + clients[my].clientPort);
		for (int i = 0; i < clientNumber; i++)
			if (clients[i].clientPort != clientPort) {
				clients[my].out.println("상대방 : " + clients[i].clientPort);
			}
		clients[my].out.println("===================================");

	}

	public static void killed(int clientPort,int killedPort) {
		boolean isPort = false;
		int my = 0;
		for(int i=0;i<clientNumber;i++) {
			if(clients[i].clientPort == killedPort) isPort = true;
			if(clients[i].clientPort == clientPort) my = i;
		}
		if (clientPort == killedPort) isPort = false;
		if (isPort == false) {
			clients[my].out.println("잘못 입력하였습니다.");
			return;
		}
		System.out.println(clientPort + " kills " + killedPort);
		for (int i = 0;i<clientNumber;i++) {
			if (clients[i].clientPort == clientPort) {
				if(killedSomeone[i] == 0) killCount++;
				killedSomeone[i] = killedPort;
			}
		}
		System.out.println("Kill Count : " + killCount);
		if(killCount == clientNumber) {
			int[] killing = new int[3];
			for(int i=0;i<clientNumber;i++) {
				for(int j=0;j<clientNumber;j++) {
					if(killedSomeone[j] == clients[i].clientPort) {
						killing[i]++;
					}
				}
			}
			System.out.println(killing[0]+" , "+killing[1]+" , "+killing[2]);
			if (killing[0] == 1 && killing[1] == 1 && killing[2] == 1){
				for(int i=0;i<clientNumber;i++) {
					clients[i].out.println("재투표해주세요.");
					unkilled(clients[i].clientPort);
				}
			} else {
				int max = 0;
				for(int i=0;i<clientNumber;i++) {
					if (killing[max] < killing[i]) max = i;
				}
				for(int i=0;i<clientNumber;i++) {
					clients[i].out.println(clients[max].clientPort + "가 사망하였습니다.");
					if(max == kill)
						clients[i].out.println(clients[max].clientPort + "는 마피아였습니다.");
					else
						clients[i].out.println(clients[max].clientPort + "는 시민이였습니다.");
				}
				// for(int i=0;i<3;i++)
				System.exit(0);
					// deleteClient(clients[0].clientPort);
			}
			
		}
	}



	public static void printMessage(int clientPort, String input) {
		for (int i = 0; i < clientNumber; i++)
			if (clients[i].clientPort == clientPort) {
				System.out.println("Writer Port Number : " + clientPort);
			} else {
				System.out.println("Reader Port Number : " + clients[i].clientPort);
				
				clients[i].out.println(input);
			}
	}
	
	public synchronized static void deleteClient(int clientPort) {
		int locate = 0;
		
		for(int i = 0; i < clientNumber; i++)
			if(clients[i].clientPort == clientPort)
				locate = i;
		
		SSLChattingServerRunnable client = null;
		
		if (locate >= 0) {
			client = clients[locate];
			
			if (locate < clientNumber - 1)
				for (int i = locate + 1; i < clientNumber; i++)
					clients[i - 1] = clients[i];
			
			clientNumber--;
			
			System.out.println("Client was removed : " + clientPort + " at clients[" + locate + "]");
			System.out.println("Current Client Total Number : " + clientNumber);
			
			client.close();
		}
	}
}
