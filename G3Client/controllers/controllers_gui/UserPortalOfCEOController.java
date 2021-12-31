package controllers_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import users.ConfirmationStatus;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * 
 * Class description: 
 * This is a class for 
 * controlling the UI of ceo
 * form.
 * 
 * @version 14/12/2021
 */
public class UserPortalOfCEOController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	public static FXMLLoader loader;
	private static UserPortalOfCEOController userPortalOfCEOController;
	
	@FXML
	private Button btnViewQuarterlyReports;
	
	@FXML
	private Button btnExit;
	
	@FXML
	private Button btnLogout;
	
	@FXML
	private Button btnViewMonthlyReports;
	
	@FXML
	private Button btnHelp;
	
    @FXML
    private Button btnViewSentReports;
    
    @FXML
    private Text ceoName;
    
    @FXML
    private Text statusText;

    /**
     * This method will load the quertely reports
     * screen.
     * 
     * @param event
     */
	@FXML
	public void getQuarterlyReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ViewQuarterlyReportsScreenController VQRC = new ViewQuarterlyReportsScreenController();
		VQRC.initViewQuarterlyReportsScreen();
	}
	
	 /**
     * This method disconnect the specific client,
     * log out the connected user and exit.
     * 
     * @param event
     */
	@FXML
	public void getExitBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message= new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser=null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}
	
	 /**
     * This method log out the connected user,
     * and load the login screen again.
     * 
     * @param event
     */
	@FXML
	public void getLogoutBtn(ActionEvent event) {
		// TODO Autogenerated
		Message message= new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser=null;
		setToLoginScreen(event);
	}
	
	 /**
     * This method load the screen of monthly reports.
     * 
     * @param event
     */
	@FXML
	public void getMonthlyReports(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ViewSystemReportsScreenCEOController viewSystemReportsScreenCEOController = new ViewSystemReportsScreenCEOController();
		viewSystemReportsScreenCEOController.initViewSystemReportsCEOScreen();
	}
	
	/**
	 * This is pop message for the help button.
	 * 
	 * @param event
	 */
	@FXML
	public void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("This is you'r the User-Portal, from here you can access the system functionalities!");	
	}
	
	 /**
     * This method load the screen of Sent reports.
     * 
     * @param event
     */
    @FXML
    public void getSentReports(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
    	ViewBMQuarterlyReportsScreenController viewBMQuarterlyReportsScreenController = new ViewBMQuarterlyReportsScreenController();
    	viewBMQuarterlyReportsScreenController.initViewBMQuarterlyReportsScreen();
    }
    
	/**
	 * Returns to login screen
	 * 
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
	public void initCEOUserPortal(Stage primaryStage,String fxmlPath) {
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
					userPortalOfCEOController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
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
	
	/**
	 * Initializes the screen again for return from different screen
	 */
	public void initCEOUserPortalAgain() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfCEO.fxml").openStream());
					userPortalOfCEOController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("CEO Main Screen");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 /**
     * This method will initialize the portal
     * according to the connected user details.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ceoName.setText(connectedUser.getUserFirstName());
		statusText.setText(connectedUser.getStatusInSystem().toString());
		if(connectedUser.getStatusInSystem().equals(ConfirmationStatus.FROZEN)) {
			btnViewMonthlyReports.setDisable(true);
			btnViewQuarterlyReports.setDisable(true);
		}
		
	}
}
