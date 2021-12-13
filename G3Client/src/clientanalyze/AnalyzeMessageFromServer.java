package clientanalyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_gui.AbstractBiteMeController;
import controllers_gui.UserPortalOfBusinessCustomerController;
import controllers_gui.BusinessCustomerRegistartionController;
import controllers_gui.EditCustomerInformationScreenController;
import users.User;

/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 * Class description:
 * This is a class which is a Wrapper for handeling
 * all messages from the server.
 * @version 12/12/2021
 */
public class AnalyzeMessageFromServer {
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant client gui classes of changes 
	 */
	private static final List<AnalyzeClientListener> clientListeners = new ArrayList<AnalyzeClientListener>();
	
	
	/**
	 * This is a function which analyzes all the messages from the server and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void analyzeMessageFromServer(Object message) throws IOException {
		if (!(message instanceof Message)) {
			return;
		} else {
			Message recivedMessageFromServer = (Message) message;
			Task recivedTaskFromServer = recivedMessageFromServer.getTask();
			Answer recievedAnswerFromServer = recivedMessageFromServer.getAnswer();
			switch (recivedTaskFromServer) {
			//changed controller method calls into listener notifications
			case CONFIRM_IP:
				switch (recievedAnswerFromServer) {
				case SUCCEED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) { 
						listener.clientIpConfirmed();
					}
					break;
				case ERROR_FAILED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIpNotConfirmed();
					}
					break;
				default:
					break;
				}
				break;
			case CREATE_USER_PORTAL:
				AbstractBiteMeController.connectedUser = ((User)recivedMessageFromServer.getObject()); // add the received user into the connected users list.
				switch (recievedAnswerFromServer) {
				case CREATE_USER_PORTAL_FOR_CUSTOMER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotCustomerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_HR_MANAGER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotHRManagerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotBusinessCustomerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_CEO_BITE_ME:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotCEOLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_SUPPLIER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotSupplierLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_BRANCH_MANAGER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotBranchManagerLogin();
					}
				default:
					break;
				}
				break;
			case PRINT_ERROR_TO_SCREEN:
				switch (recievedAnswerFromServer) {
				case ERROR_USER_NOT_FOUND:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientNotFoundInSystem();
					}
					break;
				case ERROR_ALREADY_LOGGED_IN:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIsLogedIn();
					}
					break;
				case ERROR_USER_NOT_CONFIRMED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIsNotConfirmed();
					}
					break;
				case USER_ID_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientUserIdExist();
					}
					break;
				case USER_NAME_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientUserNameExist();;
					}
					break;
				case USER_ID_ALREADY_EXIST_BUSINESS:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessUserIdExist();;
					}
					break;
				case USER_NAME_ALREADY_EXIST_BUSINESS:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessUserNameExist();;
					}
					break;
				case USER_ID_ALREADY_EXIST_SUPPLIER:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierIdExist();
					}
					break;
				case USER_NAME_ALREADY_EXIST_SUPPLIER:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierUserNameExist();
					}
					break;
				case SUPPLIER_NAME_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierNameExist();
					}
					break;
				default:
					break;
				}
			case DISPLAY_MESSAGE_TO_CLIENT:
				switch (recievedAnswerFromServer) {
				case CUSTOMER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientPrivateCustomerRegistrationSucceed();;
					}
					break;
				case BUSINESS_CUSTOMER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessCustomerRegistrationSucceed();;
					}
					break;
				case SUPPLIER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierRegistrationSucceed();
					}
					break;
				default:
					break;
				}
				break;
			case DISPLAY_COMPANIES_INTO_COMBOBOX:
				BusinessCustomerRegistartionController.companies=(ArrayList<String>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_CUSTOMERS_INTO_TABLE:
				EditCustomerInformationScreenController.customersList=(ArrayList<User>)recivedMessageFromServer.getObject();
				break;
			default:
				break;
			}
		}

	}
	
	/**
	 * add client listener to the array.
	 * 
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addClientListener(AnalyzeClientListener listener) {
		clientListeners.add(listener);
	}
	/**
	 * remove client listener to the array.
	 * 
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void removeClientListener(AnalyzeClientListener listener) {
		clientListeners.remove(listener);
	}
}
