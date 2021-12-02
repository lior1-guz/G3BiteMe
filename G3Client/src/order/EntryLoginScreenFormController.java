
package order;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bitemeclient.BiteMeClientCommunication;
import bitemeclient.BiteMeClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orders.Order;

public class EntryLoginScreenFormController{
	
	@FXML
	private TextField userNameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Button btnLogin = null;
	
	@FXML
	private Button btnExit = null;
	
	@FXML
	private Button btnHelp = null;
	
	@FXML
	private Button btnClose = null;
	
	
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/order/EntryLoginScreenForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/order/EntryLoginScreenForm.css").toExternalForm());
		primaryStage.setTitle("Welcome to BiteMe App!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	/* using login button hide window and go user portal screen */
	public void getLoginBtn(ActionEvent event) throws Exception {
		
	}

	
	/* using help button in order to show instructions for the current screen */
	public void getHelpBtn(ActionEvent event) throws Exception {
		
	}
	
	/* using exit button in order to exit from the app */
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	
	/* using close button hide window and go backwards */
	public void getCloseBtn(ActionEvent event) throws Exception {
		EntryHomeScreenFormController prevWindowController = new EntryHomeScreenFormController();
		Stage primaryStage = new Stage();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		prevWindowController.start(primaryStage);
	}


}

