import java.io.Serializable;
 
public class Customer implements Serializable {
 
    // member variables
    int customerId;
    String customerName;
    int customerAge;

    private static final long serialVersionUID = 1L;
    
	public Customer(int customerID, String customerName, int customerAge) {
		super();
		this.customerId = customerID;
		this.customerName = customerName;
		this.customerAge = customerAge;
	}

 	// by adding the method toString() to your class, you can customize (any way you like)
	// what gets output when passing the object to the System.out.println() method.
    // overriding toString() method
    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ","
                + " customerName=" + customerName + ","
                + " customerAge=" + customerAge
                + "]";
    }
}