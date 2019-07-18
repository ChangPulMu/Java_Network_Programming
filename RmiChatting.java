package SSLFileTransferChat;

import java.rmi.*;
import javax.net.ssl.SSLSocket;

public interface RmiChatting extends Remote {
	public int ready() throws RemoteException;
	public int unready() throws RemoteException;
	public int killed() throws RemoteException;
	public int unkilled() throws RemoteException;
	public String help() throws RemoteException;
}
