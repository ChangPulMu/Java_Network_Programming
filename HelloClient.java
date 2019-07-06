import java.rmi.Naming;

public class HelloClient{
	public static void main(String args[]) {
		try {
			Hello remoteObj = (Hello)Naming.lookup("rmi://localhost:1099/HelloRemote");
			
			String msg = remoteObj.sayHello("Wow");
			
			System.out.println("Message : "+msg);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}