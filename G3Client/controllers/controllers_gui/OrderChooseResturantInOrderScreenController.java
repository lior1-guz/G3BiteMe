package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import users.BusinessCustomer;

/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of the restaurant 
 * choosing for the private customer.
 * 
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * @author Alexander, Martinov
 * @author Mousa, Srour.
 * @version 15/12/2021
 */
public class OrderChooseResturantInOrderScreenController extends AbstractBiteMeController implements Initializable{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * The FXMLLoader of the current screen.
	 */
	private static FXMLLoader loader;
	
	/**
	 * A static object of the current class.
	 */
    private static OrderChooseResturantInOrderScreenController orderChooseResturantInOrderScreenController;
    /**
     * This array List will help us to get the data we want 
     *about the restaurants in the specific
     *  Branch that the user picked
     *  */
    public static Map<String,String> suppliersList = new HashMap<>(); 

    @FXML
    /**
     * The ComboBox of available restaurants.
     */
    private ComboBox<String> chooseResComboBox;

    @FXML
    /**
     * The Next Button.
     */
    private Button nextBtn;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The empty text to display messages for the user. 
     */
    private Text errorText;

    /**
     * This is a function for returning
     * to the previous screen of the customer 
     * w4c identification window.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	if(connectedUser instanceof BusinessCustomer) {
    		((BusinessCustomer) connectedUser).setLoggedInAsBusinessAccount(false);
    	}
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD1W4C_Identification_Screen.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("W4C Identification");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
    }

    /**
     * This function is used for
     * switching to the next screen and 
     * presenting the relevant restaurant for the user,
     * we need to pass the Supplier ID and his restaurant name
     * for showing the right menu to the user
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBtnNext(ActionEvent event) {
    	String pickedRestaurntName =  chooseResComboBox.getValue(); //Receive the restaurants name from the user
    	String pickedRestaurantId = null;
    	if(chooseResComboBox.getValue() == null) {
    		errorText.setText("Choose restaurant!");
    	}
    	else {
    	for(Entry<String, String> entry: suppliersList.entrySet()) {
    	      // if give value is equal to value from entry
    	      // get the corresponding key
    	      if(entry.getValue().equals(pickedRestaurntName)) {
    	    	  pickedRestaurantId = entry.getKey();
    	        break;
    	      }
    	}
    	//now we need to change this screen to the next one
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		OrderChooseItemsScreenController orderChooseItemsScreenController = new OrderChooseItemsScreenController();
		orderChooseItemsScreenController.initChooseItemsScreen(pickedRestaurantId,pickedRestaurntName); // call the init of the next screen
    	}
    }

    /**
     * This is the function for exiting 
     * the system of Bite me
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getExitBtn(ActionEvent event) {
    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
		sendToClient(disconnectMessage);
		System.exit(0);
    }

    /**
     * This is a button for 
     * getting information about the current screen
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can choose the resturant you want to order from, to view the resturant menu please click next.");
    }

    /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
    public void initChooseRestaurantScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD2ChooseResturantInOrderScreen.fxml").openStream());
					orderChooseResturantInOrderScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Choose restaurant!");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    /**
     * This is a function for 
     * initializing the screen.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//prepare message for the server to get the relevant restaurants
		Message message = new Message (Task.GET_RESTAURANTS_FOR_ORDER,Answer.WAIT_RESPONSE,connectedUser);
		sendToClient(message);
		//There are no restaurants for this Branch, set message to user
		if(suppliersList == null) {
			errorText.setText("Switch Branch, this one has no restaurants!");
    		errorText.setFill(Color.RED);
    		nextBtn.setDisable(true);
		}
		else {
			//add the relevant suppliers to the combo box
			List<String> restaurantsNames = new ArrayList<>();
			for(Entry<String, String> entry: suppliersList.entrySet()) {
				restaurantsNames.add(entry.getValue());
			}
			chooseResComboBox.getItems().addAll(restaurantsNames);
			nextBtn.setDisable(false);
		}
			
	}

}
