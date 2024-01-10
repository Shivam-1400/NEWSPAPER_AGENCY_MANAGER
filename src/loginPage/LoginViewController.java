package loginPage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtPswd;
    
    @FXML
    private Button btnSingin;

    @FXML
    void doOpenDashboard(ActionEvent event) {
    	if(txtUserId.getText().isEmpty() || txtPswd.getText().isEmpty()) {
    		new showAlert("Empty field", "Found empty fields. Fill all the fields and try again.");
    		return;
    	}
    	
    	if(!txtUserId.getText().equals("root") || !txtPswd.getText().equals("..")) {
    		new showAlert("Invalid Credentials", "Credentials didnot match. Check once and try again.");
    		doClear();
    		return;
    	}
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DashBoard/DashBoardView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			doClear();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    void doClear() {
    	txtPswd.setText(null);
    	txtUserId.setText(null);
    }

    @FXML
    void initialize() {
        
    }
}
class showAlert{
	 showAlert( String header, String msg) {
		 Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
	 }
}
