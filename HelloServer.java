public class HelloServer{
	
	public static void main(String args[]) {
		try {
			HelloImpl remObj = new HelloImpl();
			
			java.rmi.Naming.rebind("rmi://localhost:1099/HelloRemote", remObj);
			
			System.out.println("register remote object");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}