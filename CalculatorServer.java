import java.rmi.Naming;

public class CalculatorServer{
	public CalculatorServer(String server, String servname) {
		try {
			Calculator c = new CalculatorImpl();
			Naming.rebind("rmi://"+server+":1099/"+servname, c);
		} catch(Exception e) {
			System.out.println("Trouble : "+e);
		}
	}
	
	public static void main(String args[]) {
		if(args.length != 2) {
			System.out.println("Usage : Classname ServerName ServName");
			System.exit(1);
		}
		
		String mServer = args[0];
		String mServName = args[1];
		
		System.out.println("started at "+mServer+" and use default port(1099), Service name : "+mServName);
		new CalculatorServer(mServer, mServName);
	}
}