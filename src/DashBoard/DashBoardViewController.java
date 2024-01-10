package DashBoard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashBoardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doAreaMaster(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("areaMaster/AreaMasterView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBillCollection(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("NewsBillCollection/BillCollectionView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doBillStatuc(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("NewsBillStatus/BillStatusView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doCustomer(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Customer/coustomerView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doCustomerList(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerDisplay/TableView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doDeveloper(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("DeveloperInfo/1DeveloperInfo.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doHawkerControl(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HawkersControl/HawkersView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doHawkerLIst(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("haekerDisplay/TableView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doNewspaperMaster(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("newspaperMaster/PaperMasterView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	
    }
}
