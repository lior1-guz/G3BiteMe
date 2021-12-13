package serveranalyze;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import communication.Answer;
import communication.Message;
import communication.Task;
import ocsf.server.ConnectionToClient;
import query.EditUsersQueries;
import query.LoginQueries;
import query.RegistrationQueries;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 *  Class description:
 *  This is a class which is a Wrapper for handling
 *  all messages from the client.
 * @version 06/12/2021
 */
public class AnalyzeMessageFromClient {
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant server gui classes of changes 
	 */
	private static final List<AnalyzeServerListener> serverListeners = new ArrayList<AnalyzeServerListener>();
	
	/**
	 * This is a function which analyzes all the messages from the client and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @param client
	 * @throws SQLException 
	 * @throws IOException
	 */
	public static Message analyzeMessageFromClient(Object message, ConnectionToClient client) throws SQLException{
		if (!(message instanceof Message)) {
			return null;
		} else {
			Message recivedMessageFromClient = (Message) message;
			Task recivedTaskFromClient = recivedMessageFromClient.getTask();
			Answer recievedAnswerFromClient = recivedMessageFromClient.getAnswer();
			/**
			 * Switch case for getting Message from client side.
			 */
			switch (recivedTaskFromClient) {
			case CONFIRM_IP:
				//changed controller call into listener notification
				for (AnalyzeServerListener listener : serverListeners) {
					listener.displayToGuiServerConsole("status: connected " + client.getInetAddress().getHostName() + "  "
							+ client.getInetAddress().getHostAddress());
				}
				recivedMessageFromClient.setAnswer(Answer.SUCCEED);
				break;
				
			 case LOGIN:
				recivedMessageFromClient = LoginQueries.createLoginMessageForServer(recivedMessageFromClient);
				break;
			 case LOGOUT:
				 LoginQueries.logOutUser(recivedMessageFromClient);
				 break;
			 case REGISTER_PRIVATE_CUSTOMER:
				recivedMessageFromClient= RegistrationQueries.getPrivateCustomerRegistration((Message)message);
				break;
			 case GET_COMPANIES_FROM_DB:
				 recivedMessageFromClient = RegistrationQueries.getCompaniesFromDB((Message)message);
				 break;
			 case REGISTER_BUSINESS_CUSTOMER:
				 recivedMessageFromClient = RegistrationQueries.getBusinessCustomerRegistration((Message)message);
				 break;
			 case REGISTER_SUPPLIER:
				 recivedMessageFromClient = RegistrationQueries.getSupplierRegistration((Message) message);
				 break;
			 case GET_CUSTOMERS_FROM_DB:
				  recivedMessageFromClient = EditUsersQueries.getCustomersListFromDb((Message) message);
				  break;
			 case UPDATE_CUSTOMER_STATUS:
				 EditUsersQueries.setCustomerNewStatus((Message)message);
				 break;
			 case REMOVE_USER_FROM_DB:
				 EditUsersQueries.removeUserFromDB((Message)message);
				 break;
			default:
				break;
			}
			return recivedMessageFromClient;
		}
	}

	/**
	 * add server listener to the array
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addServerListener(AnalyzeServerListener listener) {
		serverListeners.add(listener);
	}
	
	/**
	 * This is a method for publishing messages to the 
	 * gui server console.
	 * 
	 * @param message
	 */
	public static void displayToMessageConsole(String message) {
		for(AnalyzeServerListener listener : serverListeners) {
			listener.displayToGuiServerConsole(message);
		}
	}

}
