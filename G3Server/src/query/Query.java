package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import bitemeserver.BiteMeServerUI;
import communication.Answer;
import communication.Message;
import communication.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import orders.DeliveryType;
import orders.OrderStatus;
import orders.OrderTimeType;
import orders.SupplyType;

import users.Branch;
import users.BranchManager;
import users.BusinessCustomer;
import users.Company;
import users.CreditCard;
import users.Customer;
import users.SupplierWorker;
import util.DateTimeHandler;
/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * Class description:
 * This class will analyze the login feature from the side of the server
 * @version 15/12/2021
 */
public class Query {

	private static Connection con;
	private static Connection externalCon;
	public static void setConnectionFromServerToDB(Connection connection) {
		con = connection;
	}
	public static void setConnectionFromServerToExternalDB(Connection connection) {
		externalCon=connection;
	}
	
	
	
	/**
	 * This method will be called once to import the data of users management from the external DB.
	 * @return
	 */
	public static ResultSet getExternalDB() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query = "SELECT * FROM externaldb.usersmanagement";
		try {
		pstmt = externalCon.prepareStatement(query);
		rs= pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;	
	}
	
	/**
	 * This method  will get the external data and insert it into our registration table .
	 * @param userType
	 * @param userID
	 * @param statusInSystem
	 * @param firstName
	 * @param lastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param email
	 * @param phoneNumber
	 * @param creditCardNumber
	 * @param creditCardCvvCode
	 * @param creditCardDateOfExpiration
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean insertRowIntoRegistrationTable(String userType, String userID, String statusInSystem, String firstName,String lastName,
			String homeBranch,int isLoggedIn,String email, String phoneNumber, String creditCardNumber,String creditCardCvvCode,String creditCardDateOfExpiration,
			String username,String password) {
		String query = "INSERT INTO semesterialproject.registration ( userType, userID, statusInSystem, firstName,lastName,homeBranch,isLoggedIn,email, phoneNumber, creditCardNumber,"
				+ " creditCardCvvCode,creditCardDateOfExpiration,username,  password  ) VALUES( '"+ userType +"' , '" +userID  +"' , '"+ statusInSystem  +"' , '" + firstName  +"' , '"  + lastName
				+"' , '"  + homeBranch +"' , '"  + isLoggedIn +"' , '"  + email +"' , '"  + phoneNumber +"' , '"  + creditCardNumber +"' , '"  + creditCardCvvCode +"' , '"  + creditCardDateOfExpiration 
				+"' , '"  + username +"' , '"  +password +"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Data Already Imported.\n");
			return true;
		}
		return false;
	}
	/**
	 * General methods for getting Data from DB
	 * 
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public static ResultSet getRowsFromTableInDB(String tableName, String condition) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
		String query = "SELECT * FROM semesterialproject."+tableName+"  WHERE "+condition;
		pstmt = con.prepareStatement(query);
		rs= pstmt.executeQuery();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static ResultSet getColumnFromTableInDB(String tableName, String columnName) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT "+columnName +" FROM semesterialproject."+tableName;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
	public static ResultSet getColumnWithConditionFromTableInDB(String tableName,String columnName,String condition) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT "+columnName +" FROM semesterialproject."+tableName+" WHERE "+condition;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	
	/**
	 * this method gets a table name , and returns all the table.
	 * @param tableName
	 * @return
	 */
	public static ResultSet getTableFromDB(String tableName) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM semesterialproject."+tableName;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	/**
	 * 
	 * @param tableName
	 * @param columnSet : the name of the column and what we want to set it for example : (isLoggedIn=1)
	 * @param condition : what is the condition behind the WHERE operand , for example : (userID = 12)
	 */
	public static void updateOneColumnForTableInDbByPrimaryKey(String tableName, String columnSet, String condition) {
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE semesterialproject."+ tableName+" SET "+columnSet+" WHERE " +"("+condition+")";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @param userName
	 * @param Password
	 * @param userId
	 * @param userType
	 */
	
	public static void insertOneRowIntoLoginTable(String userName,String Password,String userId, String userType) {
		String query = "INSERT INTO semesterialproject.login ( username, password, userID, userType) VALUES( '" + userName +"' , '"+Password +"' , '" + userId+"' , '" + userType+"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a query for entering a full row into the Order table in the DB
	 * 
	 * @param orderNumber
	 * @param supplierId
	 * @param customerUserId
	 * @param customerUserType
	 * @param branch
	 * @param timeType
	 * @param status
	 * @param issueDateTime
	 * @param estimatedSupplyDateTime
	 * @param actualSupplyDateTime
	 * @param supplyType
	 * @param totalPrice
	 * @param receiverFirstName
	 * @param receiverLastName
	 * @param receiverAddress
	 * @param receiverPhoneNumber
	 * @param deliveryFee
	 * @param itemList
	 * @param comments
	 */
	public static void insertOneRowIntoOrderTable(int orderNumber, String supplierId, String customerUserId, String customerUserType,
			Branch branch, OrderTimeType timeType, OrderStatus status, Date issueDateTime, Date estimatedSupplyDateTime,
			Date actualSupplyDateTime, SupplyType supplyType, double totalPrice, String receiverFirstName,
			String receiverLastName, String receiverAddress, String receiverPhoneNumber, double deliveryFee, String itemList, String comments, DeliveryType deliveryType) {
		
		String issueDateAndTime = DateTimeHandler.convertMySqlDateTimeFormatToString(issueDateTime);
		String estimatedSupplyDateAndTime = DateTimeHandler.convertMySqlDateTimeFormatToString(estimatedSupplyDateTime);
		String actualSupplyDateAndTime = DateTimeHandler.convertMySqlDateTimeFormatToString(actualSupplyDateTime);

				
		String query = "INSERT INTO semesterialproject.order ( orderNumber, supplierId, customerUserId, customerUserType, branch,"
				+ " timeType, status, issueDateTime, estimatedSupplyDateTime, actualSupplyDateTime, supplyType,"
				+ " totalPrice, receiverFirstName, receiverLastName, receiverAddress, receiverPhoneNumber, deliveryFee,"
				+ " itemsList, comments, deliveryType) VALUES( '" + orderNumber +"' , '"+supplierId +"' , '" + customerUserId+"' , '" + customerUserType+"' , '" + branch+"' ,"
						+ " '" + timeType+"' , '" + status.name()+"' , '" + issueDateAndTime +"' , '" + estimatedSupplyDateAndTime  +"' , '" + actualSupplyDateAndTime+"' , '" + supplyType.name()+"' ,"
								+ " '" + totalPrice+"' , '" + receiverFirstName+"' , '" + receiverLastName+"' , '" + receiverAddress+"' , '" + receiverPhoneNumber+"' ,"
										+ " '" + deliveryFee+"' , '" + itemList+"' , '" + comments+"' , '" +deliveryType.name() +"' )";

		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param creditCardNumber
	 * @param expDate
	 * @param cvvNumber
	 */
	public static void insertOneRowIntoCreditCardTable(CreditCard creditCard) {
		String query = "INSERT INTO semesterialproject.creditcard ( creditCardNumber, creditCardCvvCode, creditCardDateOfExpiration) VALUES ( '" 
				+ creditCard.getCreditCardNumber()+"' , '" +creditCard.getCreditCardDateOfExpiration() + "' , '"+creditCard.getCreditCardCvvCode()+"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @param status
	 * @param firstName
	 * @param lastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param w4c
	 * @param email
	 * @param phoneNumber
	 * @param creditCard
	 */
	public static void insertOneRowIntoCustomerTable(Customer customer) {
		String query = "INSERT INTO semesterialproject.customer ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, privateW4cCodeNumber, email, phoneNumber, "
				+ "privateCreditCard ) VALUES( '" + customer.getUserId() + "', '" + customer.getStatusInSystem().toString() +  "', '" +customer.getUserFirstName() +  "', '" +
				customer.getUserLastName() +  "', '" + customer.getHomeBranch().toString() + "', '"  + 0 +  "', '" + customer.getPrivateW4cCodeNumber() 
				+  "', '" + customer.getUserEmail() +  "', '" + customer.getPhoneNumber() +  "', '" + customer.getPrivateCreditCard() +  "' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 *  at the beginning we get the w4cCode of the company then we add it to the query.
	 * @param businessCustomer
	 * @param type
	 */
	public static void insertOneRowIntoBusinessCustomerOrHrManagerTable(BusinessCustomer businessCustomer,String type) {
		int employerID=0;
		ResultSet rs = null;
		PreparedStatement pstmt1=null;
		if(type.equals("businesscustomer")) {
		try {
			String query1 = "SELECT companyCode FROM semesterialproject.company WHERE companyName='"+businessCustomer.getCompanyOfBusinessCustomerString()+"'";
			pstmt1 = con.prepareStatement(query1);
			rs= pstmt1.executeQuery();
			if(rs.next())
			 employerID = rs.getInt(1);
			rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String query2 = "INSERT INTO semesterialproject." +type+" ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, businessW4cCodeNumber, email, phoneNumber, "
				+ "privateCreditCard, companyName, budgetType, customerPosition, budgetMaxAmount ,privateW4cCodeNumber ) VALUES ( '" + businessCustomer.getUserId() +  "', '" + businessCustomer.getStatusInSystem().toString()+  "', '" +
				businessCustomer.getUserFirstName() +  "', '" + businessCustomer.getUserLastName() +  "', '" + businessCustomer.getHomeBranch().toString() +  "', '" + 0 +  "', '" + employerID
				+  "', '" + businessCustomer.getUserEmail() +  "', '" + businessCustomer.getPhoneNumber() +  "', '" + businessCustomer.getPrivateCreditCard() +  "', '"  + businessCustomer.getCompanyOfBusinessCustomerString() 
				+ "', '" +  businessCustomer.getBudgetOfBusinessCustomer().toString() + "', '"  + businessCustomer.getPositionType().toString() + "', '"  + businessCustomer.getBudgetMaxAmount() +  "', '" 
				+ businessCustomer.getPrivateW4cCodeNumber()+"' )";
		PreparedStatement pstmt2=null;
		try {
			pstmt2 = con.prepareStatement(query2);
			pstmt2.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param customer
	 */
	public static void insertOneRowIntoSupplierTable(SupplierWorker supplierWorker) {
		String query = "INSERT INTO semesterialproject.supplierworker ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber, "
				+ "supplierId, workerPosition ) VALUES( '" + supplierWorker.getUserId() + "', '" + supplierWorker.getStatusInSystem() + "', '" + supplierWorker.getUserFirstName() + "', '" 
				+ supplierWorker.getUserLastName() + "', '" +  supplierWorker.getHomeBranch() + "', '" +  0 + "', '" +  supplierWorker.getUserEmail() + "', '" +  supplierWorker.getPhoneNumber()
				+ "', '" +  supplierWorker.getSupplier().getSupplierId() + "', '" + supplierWorker.getWorkerPosition() +"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 * This method gets a company object and insert it into the company table in DB.
	 * @param company
	 */
	public static void insertOneRowIntoCompanyTable(Company company) {
		String query = "INSERT INTO semesterialproject.company (companyName, companyStatusInSystem, address, email, companyCode) VALUES ('" +company.getCompanyName()
		+"', '" + company.getStatusCompanyInSystem() +"', '" + company.getAddress() +"', '" + company.getEmail() +"', '" + company.getcompanyCode() + "' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void inserOneRowIntoCeoBiteMeTable(CeoBiteMe ceo) {
		String query = "INSERT INTO semesterialproject.ceobiteme (userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber) VALUES ('" + ceo.getUserId()
		+ "' , '" + ceo.getStatusInSystem().toString() 	+ "' , '" + ceo.getUserFirstName() + "' , '" + ceo.getUserLastName() + "' , '" + ceo.getHomeBranch().toString() + "' , '" + 0 
		+ "' , '" + ceo.getUserEmail() + "' , '" + ceo.getPhoneNumber() +"' )";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertOneRowIntoBranchManagerTable(BranchManager bm) {
		String query = "INSERT INTO semesterialproject.branchmanager (userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber) VALUES ('" + bm.getUserId()
		+ "' , '" + bm.getStatusInSystem().toString() 	+ "' , '" + bm.getUserFirstName() + "' , '" + bm.getUserLastName() + "' , '" + bm.getHomeBranch().toString() + "' , '" + 0 
		+ "' , '" + bm.getUserEmail() + "' , '" + bm.getPhoneNumber() +"' )";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method gets a table name and condition , and deletes the row according to the condition from DB.
	 * condition must be according to table's primary key !
	 * @param tableName
	 * @param condition
	 */
	public static void deleteRowFromTableInDbByPrimaryKey(String tableName,String condition) {
		//DELETE FROM `semesterialproject`.`customer` WHERE (`userID` = '100022');
		String query = "DELETE FROM semesterialproject."+tableName+" WHERE ("+condition+")";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
