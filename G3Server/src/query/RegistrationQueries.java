package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.BusinessCustomer;
import users.CreditCard;
import users.Customer;
import users.HrManager;
import users.Login;
import users.Supplier;
import users.User;

/**
 * 
 * @author Mousa, Srour
 * Class description:
 * This class will contain all the queries to DB and logi
 * that relate to Registration phase.
 * @version 11/12/2021
 */
public class RegistrationQueries {

	/**
	 * In this method we check if all the details are not exist before in db ( userID , username , creditcard)
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * in case the credit card number already exist , we do create rows and inesrt them but we dont create a new row for the credit card.
	 * @param message
	 * @return
	 */
	public static Message getPrivateCustomerRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		Customer customer = (Customer) list.get(0);
		Login login = (Login) list.get(1);
		CreditCard creditCard = (CreditCard) list.get(2);
		if(checkIfUserIdExist(customer)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(!checkIfCreditCardNumberExist(creditCard))
			Query.insertOneRowIntoCreditCardTable(creditCard);
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), customer.getUserId(), "customer");
		Query.insertOneRowIntoCustomerTable(customer);
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.CUSTOMER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}
	
	/**
	 * 	 * In this method we check if all the details are not exist before in db ( userID , username , creditcard)
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * in case the credit card number already exist , we do create rows and inesrt them but we dont create a new row for the credit card.
	 * @param message
	 * @return
	 */
	
	public static Message getBusinessCustomerRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		String businessCustomerType=null;
		if(list.get(0) instanceof HrManager) {
			businessCustomerType = "hrmanager";
		}
		else {
			businessCustomerType = "businesscustomer";
		}
		Login login = (Login) list.get(1);
		CreditCard creditCard = (CreditCard) list.get(2);
		if(checkIfUserIdExist((User)list.get(0))) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST_BUSINESS,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST_BUSINESS,null);
			return messageBackToClient;
		}
		if(!checkIfCreditCardNumberExist(creditCard))
			Query.insertOneRowIntoCreditCardTable(creditCard);
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), ((User)list.get(0)).getUserId(), businessCustomerType);
		if(businessCustomerType.equals("hrmanger")) {
			Query.insertOneRowIntoBusinessCustomerOrHrManagerTable((BusinessCustomer)list.get(0), "hrmanager");
		}
		else {
			Query.insertOneRowIntoBusinessCustomerOrHrManagerTable((BusinessCustomer)list.get(0), "businesscustomer");
		}
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.BUSINESS_CUSTOMER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}
	
	/**
	 * 	 * In this method we check if all the details are not exist before in db ( userID , username )
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * we also check here if the supplier name already exist.
	 * @param message
	 * @return
	 */
	public static Message getSupplierRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		Supplier supplier = (Supplier) list.get(0);
		Login login = (Login) list.get(1);
		if(checkIfUserIdExist(supplier)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST_SUPPLIER,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST_SUPPLIER,null);
			return messageBackToClient;
		}
		if(checkIfSupplierNameExsist(supplier)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.SUPPLIER_NAME_EXIST,null);
			return messageBackToClient;
		}
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), supplier.getUserId(), "supplier");
		Query.insertOneRowIntoSupplierTable(supplier);
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.SUPPLIER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}

	
	/**BUS
	 * This method checks if the customerID exist in the customer table.
	 * @param customer
	 * @return
	 */
	private static boolean checkIfUserIdExist(User user) {	
		ResultSet rs = Query.getColumnFromTableInDB("login", "userID");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(user.getUserId())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the username already exists on the login table.
	 * @param login
	 * @return
	 */
	private static boolean checkIfLoginUserNameExist(Login login) {
		ResultSet rs = Query.getColumnFromTableInDB("login", "username");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(login.getUserName())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the credit card already exist in db.
	 * @param creditCard
	 * @return
	 */
	private static boolean checkIfCreditCardNumberExist(CreditCard creditCard) {
		ResultSet rs = Query.getColumnFromTableInDB("creditcard", "creditCardNumber");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(creditCard.getCreditCardNumber())) {
					return true;
					
				}
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * this method checks if the supplier name already exist in db .
	 * @param supplier
	 * @return
	 */
	private static boolean checkIfSupplierNameExsist(Supplier supplier) {
		ResultSet rs = Query.getColumnFromTableInDB("supplier", "supplierBusinessName");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(supplier.getSupplierBusinessName())) {
					return true;
					
				}
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method gets from the db all the confirmed companies and returns them as arraylist.
	 * @param message
	 * @return
	 */
	public static Message getCompaniesFromDB(Message message) {
		ArrayList<String> companies = new ArrayList<>();
		ResultSet rs = Query.getColumnWithConditionFromTableInDB("company", "companyName","companyStatusInSystem='CONFIRMED'" );
		try {
			while(rs.next()) {
				companies.add(rs.getString(1));
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setTask(Task.DISPLAY_COMPANIES_INTO_COMBOBOX);
		message.setAnswer(Answer.SUCCEED);
		message.setObject(companies);
		return message;
	}

}
