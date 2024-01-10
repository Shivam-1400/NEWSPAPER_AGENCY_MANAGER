package areaMaster;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;


public class AreaMasterViewController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> comboArea;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;

    Connection conn;
    ResultSet table;
    PreparedStatement pst;
    
    @FXML
    void doDelete(ActionEvent event) { 
    	if(comboArea.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Fill area", " Fill all required fields and try again.");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("DELETE FROM area_master WHERE Area= ? ");
			pst.setString(1, comboArea.getSelectionModel().getSelectedItem());
			int count=   pst.executeUpdate();
			if(count==0) {
				new showAlert("Record deletion failed", "No record found with name "+ comboArea.getSelectionModel().getSelectedItem());
				doClearAll();
				return;
			}
			new showAlert("Record deleted successfully", "Record deleted successfully of name "+ comboArea.getSelectionModel().getSelectedItem());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	doClearAll();
		
    }

    @FXML
    void doSave(ActionEvent event) {
    	if(comboArea.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Fill area", " Fill all required fields and try again.");
    		return;
    	}
    	try {
    		pst= conn.prepareStatement("INSERT INTO area_master VALUES (?) ");
			//pst.setString(1, comboArea.getSelectionModel().getSelectedItem());
    		pst.setString(1, comboInput(comboArea));
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Unable to save record", "Record not saved due to some error. Please check connectivity and try again");
				doClearAll();
				return;
			}
			
			new showAlert("Record saved successfully", "Record saved successfully of name "+ comboArea.getSelectionModel().getSelectedItem());
			doClearAll();
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }

    @FXML
    void initialize() {
    	conn= DatabaseConnector.getConnectionnn();
        doFill();
    }
    
    void doFill() {
    	try {
			pst= conn.prepareStatement("SELECT DISTINCT Area FROM area_master ");
			table= pst.executeQuery();
			
			while(table.next()){
				String name= table.getString(1);
				comboArea.getItems().add(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    void doClearAll() {
    	comboArea.setValue(null);
    	comboArea.getItems().clear();
    	doFill();
    }
    
    String comboInput(ComboBox<String> obj) {
    	String a= obj.getSelectionModel().getSelectedItem();
    	return a;
    }
}

class DatabaseConnector {
	public static Connection getConnectionnn() {
		Connection con= null;
		
		try {
			con= DriverManager.getConnection("jdbc:mysql://localhost/project_jdbc", "root", "");
			//System.out.println("Connection with Database is Successful.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
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
