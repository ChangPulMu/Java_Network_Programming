import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator{
	
	// for Calculator's serialize warning,
	// keep serialVersionUID field for class version number
	
	private static final long serialVersionUID = 1L;
	
	public CalculatorImpl() throws RemoteException{
		super();
	}
	public long add(long a, long b) throws RemoteException{
		return a+b;
	}
	public long sub(long a, long b) throws RemoteException{
		return a-b;
	}
	public long mul(long a, long b) throws RemoteException{
		return a*b;
	}
	public long div(long a, long b) throws RemoteException{
		return a/b;
	}
	public long permutation(long n, long r) throws RemoteException{
		int tmp = 1;
		
		for(long i = n;i > r;i--)
			tmp *= i;
		
		return tmp;
	}
	public long theoryOfGeneralRelativity(long m, long c) throws RemoteException{
		return m * c * c;
	}
}