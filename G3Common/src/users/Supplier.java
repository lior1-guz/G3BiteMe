package users;

import java.io.Serializable;

/**
 *  Class description: 
 * This class is a class which
 * defines the main attributes and functionalities of a supplier in our
 * system.
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * 
 * @version 15/12/2021
 */
public class Supplier implements Serializable{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * This is the Id of the supplier
	 */
	private String supplierId;	
	/**
	 * This is the name of the supplier
	 */
	private String supplierName;
	/**
	 * This is the email address of the supplier.
	 */
	private String email;
	/**
	 * Each supplier works from a specific branch
	 */
	protected Branch homeBranch;
	/**
	 * This is the phone number of the supplier.
	 */
	private String phoneNumber;
	/**
	 * This is the revenue fee of the supplier.
	 */
	private double revenueFee; 
	/**
	 * Every Supplier has a status
	 */
	private ConfirmationStatus statusInSystem;
	
	/**
	 * This is the constructor of the class.
	 * @param supplierId Supplier ID.
	 * @param supplierName Supplier Name.
	 * @param homeBranch supplier home Branch.
	 * @param email Supplier Email.
	 * @param phoneNumber Supplier phone number.
	 * @param revenueFee Supplier's revenue Fee.
	 */
	public Supplier(String supplierId, String supplierName,Branch homeBranch, String email, String phoneNumber, double revenueFee, ConfirmationStatus statusInSystem) {
		super();
		this.supplierId = supplierId;		
		this.supplierName = supplierName;
		this.homeBranch = homeBranch;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.revenueFee=revenueFee;
		this.statusInSystem=statusInSystem;
	}
	
	/**
	 * This section is for the Setters and Getters of the Class Supplier
	 */
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public Branch getHomeBranch() {
		return homeBranch;
	}

	public void setHomeBranch(Branch homeBranch) {
		this.homeBranch = homeBranch;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getRevenueFee() {
		return revenueFee;
	}

	public void setRevenueFee(double revenueFee) {
		this.revenueFee = revenueFee;
	}
	
	public ConfirmationStatus getStatusInSystem() {
		return statusInSystem;
	}

	public void setStatusInSystem(ConfirmationStatus statusInSystem) {
		this.statusInSystem = statusInSystem;
	}

	@Override
	public String toString() {
		return "Supplier: " + supplierName + "belongs to:" + homeBranch;
	}
	
}
