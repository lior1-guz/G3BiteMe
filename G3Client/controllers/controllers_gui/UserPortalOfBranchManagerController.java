package controllers_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import users.ConfirmationStatus;

/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * controlling the UI of Branch Manager
 * form.
 * 
 * @version 15/12/2021
 */
public class UserPortalOfBranchManagerController extends AbstractBiteMeController implements Initializable{
	

	@FXML
	private Button btnViewSysReports;
	@FXML
	private Button btnCompanyRegManagement;
	@FXML
	private Button btnEditCustomerInfo;
	@FXML
	private Button btnSupplierReg;
	@FXML
	private Button btnCustomerReg;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnAddQuarterlyReports;
	@FXML
	private Label customerNameLabel;
	@FXML
	private Button btnHelp;
    @FXML
    private Text branchManagerName;
    @FXML
    private Text statusText;
    @FXML
    private Text branchName;
    
	
	public static FXMLLoader loader;
	private static UserPortalOfBranchManagerController userPortalOfBranchManagerController;
	
	
	// Event Listener on Button[#btnViewSysReports].onAction
	@FXML
	public void getViewReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ViewSystemReportsScreenController viewSystemReportsScreenController = new ViewSystemReportsScreenController();
		viewSystemReportsScreenController.initViewSystemReportsScreen();
	}
	// Event Listener on Button[#btnCompanyRegManagement].onAction
	@FXML
	public void getCompRegManagement(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		CompanyRegistartionManagementScreenController companyRegistartionManagementScreenController = new CompanyRegistartionManagementScreenController();
		companyRegistartionManagementScreenController.initSupplierRegistrationScreen();
	}
	// Event Listener on Button[#btnEditCustomerInfo].onAction
	@FXML
	public void getEditCustomerInfo(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		EditCustomerInformationScreenController editCustomerInformationScreenController = new EditCustomerInformationScreenController();
		editCustomerInformationScreenController.initEditCustomerInformationScreen(); // call the init of the next screen
	}
	// Event Listener on Button[#btnSupplierReg].onAction
	@FXML
	public void getSupplierReg(ActionEvent event) {
//		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//		SupplierWorkerRegistrationScreenController supplierWorkerRegistrationScreenController = new SupplierWorkerRegistrationScreenController();
//		supplierWorkerRegistrationScreenController.initSupplierRegistrationScreen(); // call the init of the next screen
	}
	// Event Listener on Button[#btnCustomerReg].onAction
	@FXML
	public void getCustomerReg(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		UsersRegistrationScreenController usersRegistrationScreenController = new UsersRegistrationScreenController();
		usersRegistrationScreenController.initUserRegistrationScreen();; // call the init of the next screen
		
		
	}
	// Event Listener on Button[#btnExit].onAction
	@FXML
	public void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}
	// Event Listener on Button[#btnLogout].onAction
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;  // after we changed the status in DB we set the connectedUser as null so we can get new login from same client.
		setToLoginScreen(event);  //set the login screen
	}
	// Event Listener on Button[#btnAddQuarterlyReports].onAction
	@FXML
	public void getAddQuarterlyReports(ActionEvent event) {
		// TODO Autogenerated
	}
	
	/**
	 * This is pop message for the help button.
	 * @param event
	 */
	// Event Listener on Button[#btnHelp].onAction
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is the Main screen of Branch manager, Please press any button!");
	}
	
	/**
	 * Returns to login screen
	 * @param event
	 */
	private void setToLoginScreen(ActionEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/LoginScreen.fxml").openStream());
					LoginScreenController LSC = loader.getController();
					LSC.initLoginScreen();
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
	}
	
	/**
	 * This is the initialization function for this 
	 * screen.
	 * 
	 * @param primaryStage
	 * @param fxmlPath
	 */
	public void initBranchManagerUserPortal(Stage primaryStage,String fxmlPath) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					primaryStage.hide(); 
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource(fxmlPath).openStream());
					userPortalOfBranchManagerController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Main menu");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initPortalAgain() {
		loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = null;
		try {
			root = loader.load(getClass().getResource("/fxmls/UserPortalOfBranchManager.fxml").openStream());
			//root = FXMLLoader.load(getClass().getResource("/fxmls/LoginScreen.fxml"));
			userPortalOfBranchManagerController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();/* show the new screen */
	}
	
	
	/**
	 * This method sets the correct values of the branchManager in portal.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		branchManagerName.setText(connectedUser.getUserFirstName());
		statusText.setText(connectedUser.getStatusInSystem().toString());
		branchName.setText(connectedUser.getHomeBranch().toString());
		if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
			btnAddQuarterlyReports.setDisable(true);
			btnCustomerReg.setDisable(true);
			btnCompanyRegManagement.setDisable(true);
			btnSupplierReg.setDisable(true);
			btnEditCustomerInfo.setDisable(true);
			btnViewSysReports.setDisable(true);
		}
		
		
	}
}
