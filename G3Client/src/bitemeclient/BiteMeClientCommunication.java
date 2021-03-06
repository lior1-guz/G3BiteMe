// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package bitemeclient;

import java.io.IOException;

import clientanalyze.AnalyzeMessageFromServer;
import communication.Message;
import ocsf.client.AbstractClient;

/**
 *  Class description: 
 * 
 * This class overrides some of the methods
 * defined in the abstract superclass
 * in order to give more functionality
 * to the client.
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @version 03/12/2021
 */
public class BiteMeClientCommunication extends AbstractClient {
	/**
	 * Class members description:
	 */

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	public static BiteMeClientCommunication clientCommunication;
	
	/**
	 * wait for response from server
	 */
	public static boolean awaitResponse = false;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 * 
	 * @param host the host of connection.
	 * @param port port of connection (5555 by default).
	 * @throws IOException
	 */
	public BiteMeClientCommunication(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		clientCommunication = this;
		openConnection(); // create socket and open connection
	}


	/**
	 * This method handles 
	 * all data that comes in 
	 * from the server.
	 * 
	 * @param message received message from server.
	 */
	public void handleMessageFromServer(Object message) throws IOException {
		if(message instanceof Message ) {
			awaitResponse = false;
			AnalyzeMessageFromServer.analyzeMessageFromServer(message);
		}
	}

	/**
	 * This method handles all
	 *  data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Message message) {
		try {
			awaitResponse = true;
			openConnection();// in order to send more than one message
			sendToServer(message); // send the msg to the server
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}

	/**
	 * This method 
	 * terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
