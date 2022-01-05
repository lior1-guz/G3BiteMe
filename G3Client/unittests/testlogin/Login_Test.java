package testlogin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clientanalyze.AnalyzeMessageFromServer;
import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_gui.LoginScreenController;
import users.BranchManager;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Customer;
import users.HrManager;
import users.IGetLoginDetails;
import users.Login;
import users.SupplierWorker;

/*This is the class for the Login testing from the client side*/
public class Login_Test {

	/*This is the real login controller*/
	public LoginScreenController loginScreenController;
	
	/*This is the Object of the stubbed class*/
	public StubGetLoginDetails stubGetLoginDetails;
	
	/*Stub for IGetLoginDetails - we inject it using Constructor injection to the Login controller*/
	public class StubGetLoginDetails implements IGetLoginDetails{
		public String userNameLogin = null;
		public String passwordLogin = null;

		@Override
		public Login getLogin() {
			return new Login(userNameLogin,passwordLogin);
		}

		@Override
		public boolean isLoginDataValidFromUser(Login login) {
			if(login.getUserName().equals("") || login.getPassword().equals("")) {
				return false;
			}
			else
				return true;
		}
		
		/*This is the method that gives us data received from the Server and the DB and than checks logic in AnalyzeMessageFromServer */
		@Override
		public void userReturnDataFromDB(Message message) {
			
			/*This is the server part we stub and the DB we stub to get the whole data from the server side*/
			if(userNameLogin.equals("ceo") && passwordLogin.equals("ceo")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME);
				message.setObject(new CeoBiteMe());
			}
			else if(userNameLogin.equals("cu") && passwordLogin.equals("cu")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CUSTOMER);
				message.setObject(new Customer());
			}
			else if(userNameLogin.equals("bc") && passwordLogin.equals("bc")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER);
				message.setObject(new BusinessCustomer());
			}
			else if(userNameLogin.equals("hrmanager") && passwordLogin.equals("hrmanager")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER);
				message.setObject(new HrManager());
			}
			else if(userNameLogin.equals("bmn") && passwordLogin.equals("bmn")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER);
				message.setObject(new BranchManager());
			}
			else if(userNameLogin.equals("sw") && passwordLogin.equals("sw")) {
				message.setTask(Task.CREATE_USER_PORTAL);
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_SUPPLIER);
				message.setObject(new SupplierWorker());
			}
			else if(userNameLogin.equals("ta") && passwordLogin.equals("fus")) {
				message.setTask(Task.PRINT_ERROR_TO_SCREEN);
				message.setAnswer(Answer.ERROR_ALREADY_LOGGED_IN);
				message.setObject(new SupplierWorker());
			}
			else {
				message.setTask(Task.PRINT_ERROR_TO_SCREEN);
				message.setAnswer(Answer.ERROR_USER_NOT_FOUND);
			}
			try {
				/*This is the Analyze message of the client side*/
				AnalyzeMessageFromServer.analyzeMessageFromServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/*Getters and Setters of the Stubbed Class*/
		public String getUserNameLogin() {
			return userNameLogin;
		}

		public void setUserNameLogin(String userNameLogin) {
			this.userNameLogin = userNameLogin;
		}

		public String getPasswordLogin() {
			return passwordLogin;
		}

		public void setPasswordLogin(String passwordLogin) {
			this.passwordLogin = passwordLogin;
		}
		
	}
	
		
	/*Before each test we will create the Stub object*/
	@BeforeEach
	void setUp() throws Exception {	
		stubGetLoginDetails = new StubGetLoginDetails();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	//Test Description: Check if the user has entered invalid user name
	//Test Input: userName = "" , password = "cu"
	//Test Expected Output: return false from the function doLoginProcess()
	void test_user_log_in_with_invalid_user_name() {
		stubGetLoginDetails.setUserNameLogin("");
		stubGetLoginDetails.setPasswordLogin("cu");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return false - use Assert false*/
		assertFalse(resultFromLoginProcess);
	}
	
	@Test
	//Test Description: Check if the user has entered invalid password
	//Test Input: userName = "cu" , password = ""
	//Test Expected Output: return false from the function doLoginProcess()
	void test_user_log_in_with_invalid_password() {
		stubGetLoginDetails.setUserNameLogin("cu");
		stubGetLoginDetails.setPasswordLogin("");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return false - use Assert false*/
		assertFalse(resultFromLoginProcess);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with ceo user
	//Test Input: userName = "ceo" , password = "ceo"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer to create ceo
	//user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME
	void test_user_log_in_with_ceo_user() {
		stubGetLoginDetails.setUserNameLogin("ceo");
		stubGetLoginDetails.setPasswordLogin("ceo");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with customer user
	//Test Input: userName = "cu" , password = "cu"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to create customer user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_CUSTOMER
	void test_user_log_in_with_customer_user() {
		stubGetLoginDetails.setUserNameLogin("cu");
		stubGetLoginDetails.setPasswordLogin("cu");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_CUSTOMER, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with business customer user
	//Test Input: userName = "bc" , password = "bc"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to create business customer user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER
	void test_user_log_in_with_business_customer_user() {
		stubGetLoginDetails.setUserNameLogin("bc");
		stubGetLoginDetails.setPasswordLogin("bc");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with hr manager user
	//Test Input: userName = "hrmanager" , password = "hrmanager"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to create business customer user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER
	void test_user_log_in_with_hr_manager_user() {
		stubGetLoginDetails.setUserNameLogin("hrmanager");
		stubGetLoginDetails.setPasswordLogin("hrmanager");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with branch manager user
	//Test Input: userName = "bmn" , password = "bmn"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to create business customer user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER
	void test_user_log_in_with_branch_manager_user() {
		stubGetLoginDetails.setUserNameLogin("bmn");
		stubGetLoginDetails.setPasswordLogin("bmn");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if the user succeeded entering with supplier user
	//Test Input: userName = "sw" , password = "sw"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to create supplier user portal with the Answer - Answer.CREATE_USER_PORTAL_FOR_SUPPLIER
	void test_user_log_in_with_supplier_user() {
		stubGetLoginDetails.setUserNameLogin("sw");
		stubGetLoginDetails.setPasswordLogin("sw");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.CREATE_USER_PORTAL , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.CREATE_USER_PORTAL_FOR_SUPPLIER, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if a user is already loggedIn in the system 
	//Test Input: userName = "ta" , password = "fus"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to return that the user is already logged in with the Answer - Answer.ERROR_ALREADY_LOGGED_IN
	void test_user_loggedIn_in_the_system() {
		stubGetLoginDetails.setUserNameLogin("ta");
		stubGetLoginDetails.setPasswordLogin("fus");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.PRINT_ERROR_TO_SCREEN , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.ERROR_ALREADY_LOGGED_IN, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
	@Test
	//Test Description: Check if a user is already loggedIn in the system 
	//Test Input: userName = "li" , password = "li"
	//Test Expected Output: return true from the function doLoginProcess() in addition expect AnalyzeMessageFromServer
	//to return that the user doesn't exist with the Answer - Answer.ERROR_USER_NOT_FOUND
	void test_user_not_found_in_the_system() {
		stubGetLoginDetails.setUserNameLogin("li");
		stubGetLoginDetails.setPasswordLogin("li");
		loginScreenController = new LoginScreenController(stubGetLoginDetails);
		/*Call function under test*/
		boolean resultFromLoginProcess = loginScreenController.doLoginProcess();
		
		/*Check if the answer is correct - expected that doLoginProcess will return true - use Assert true*/
		assertTrue(resultFromLoginProcess);
		assertEquals(Task.PRINT_ERROR_TO_SCREEN , AnalyzeMessageFromServer.taskForUnitTest);
		assertEquals(Answer.ERROR_USER_NOT_FOUND, AnalyzeMessageFromServer.answerForUnitTest);
	}
	
}
