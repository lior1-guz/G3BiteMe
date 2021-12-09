package users;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * @author Alexander, Martinov.
 * Class description: This class is a class which
 * defines the main attributes and functionalities of a supplier in our
 * system.
 * @version 09/12/2021
 */
public class Supplier extends User implements Serializable{

	/**
	 * Class members description:
	 */

	/**
	 * Each supplier has a supplier business name such as Dominos ,Mcdonalds.
	 */
	private String supplierBusinessName;

	/**
	 * This is the revenue that the supplier gives to the Bite Me company per month
	 * from their monthly outcomes, 
	 * Please note that this is a number in rage [0,1]
	 */
	private double revenueFee;

	/**
	 * This is the constructor of the class Supplier
	 * 
	 * @param userId
	 * @param statusInSystem
	 * @param userFirstName
	 * @param userLastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param userEmail
	 * @param phoneNumber
	 * @param supplierBusinessName
	 * @param revenueFee
	 */
	public Supplier(String userId, ConfirmationStatus statusInSystem, String userFirstName, String userLastName,
			Branch homeBranch, boolean isLoggedIn, String userEmail, String phoneNumber,
			String supplierBusinessName, double revenueFee) {
		super(userId, statusInSystem, userFirstName, userLastName, homeBranch, isLoggedIn,
				userEmail, phoneNumber);
		this.supplierBusinessName = supplierBusinessName;
		this.revenueFee = revenueFee;
	}

	/**
	 * This section is for the Setters and Getters of the Class Supplier
	 */
	public String getSupplierBusinessName() {
		return supplierBusinessName;
	}

	public void setSupplierBusinessName(String supplierBusinessName) {
		this.supplierBusinessName = supplierBusinessName;
	}

	public double getRevenueFee() {
		return revenueFee;
	}

	public void setRevenueFee(double revenueFee) {
		this.revenueFee = revenueFee;
	}

	/**
	 * This is the toString for this class
	 */
	@Override
	public String toString() {
		return "Supplier: " + " " + supplierBusinessName + " belogns to:" + homeBranch;
	}

}
