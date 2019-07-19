package SSLFileTransferChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.net.ssl.SSLSocket;

public class RmiChattingImpl extends UnicastRemoteObject implements RmiChatting {
	
	protected RmiChattingImpl() throws RemoteException {
		super();
	}

	public int ready() throws RemoteException{
		return 1;
	}
	public int unready() throws RemoteException{
		return 0;
	}
	
	public int unkilled() throws RemoteException{
		return 2;
	}

	public int killed() throws RemoteException{
		return 3;
	}

	public String help() throws RemoteException {
		return 
		"===================================\n" +
		"\"/Bye.\" : 프로그램을 종료합니다.\n" +
		"\"/Ready.\" : 게임 시작전 준비합니다.\n" +
		"\"/UnReady.\" : 준비를 취소합니다.\n" +
		"\"/Start.\" : 게임을 시작합니다.\n" +
		"\"/Kill.\" : 죽일 사람을 투표합니다.\n" +
		"\"/Help.\" : 도움말을 출력합니다.\n" + 
		"===================================";
	}
}
