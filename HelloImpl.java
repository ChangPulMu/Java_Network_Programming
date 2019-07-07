import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello{
	
	// for Calculator's serialize warning,
	// keep serialVersionUID field for class version number
	private static final long serialVersionUID = 1L;
	
	public HelloImpl() throws RemoteException{
		super();
	}
	
	public String sayHello(String name) {
		return "Hello World " + name + "!!";
	}
}