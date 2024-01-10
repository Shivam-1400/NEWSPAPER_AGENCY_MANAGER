package newspaperMaster;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaperMasterViewController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> comboName;
    @FXML
    private TextField lblPrice;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRefresh;

    Connection conn;
    ResultSet table;
    PreparedStatement pst;
    
    @FXML
    void doDelete(ActionEvent event) {
    	if(comboName.getSelectionModel().getSelectedItem().isBlank()) {
    		new showAlert("Fill name field", "Name field is required to delete. Fill paper name fields and try again.");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("DELETE FROM newspaper_master WHERE Name= ? ");
			pst.setString(1, comboName.getSelectionModel().getSelectedItem());
			int count=   pst.executeUpdate();
			if(count==0) {
				new showAlert("Record deletion failed", "No record found with name "+ comboName.getSelectionModel().getSelectedItem());
				doClearAll();
				return;
			}
			new showAlert("Record deleted successfully", "Record deleted successfully of name "+ comboName.getSelectionModel().getSelectedItem());
			doRefresh(null);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doRefresh(ActionEvent event) {
    	doClearAll();
    	doFill();
    }

    @FXML
    void doSave(ActionEvent event) {
    	if(lblPrice.getText().isBlank() || comboName.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Fill all the fields", "All fields are required. Fill all fields and try again.");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("INSERT INTO newspaper_master VALUES (?, ?) ");
			//pst.set
			pst.setString(1, comboName.getSelectionModel().getSelectedItem());
			pst.setFloat(2, Float.parseFloat(lblPrice.getText()));
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Unable to save record", "Record not saved due to some error. Please check connectivity and try again");
				doClearAll();
				return;
			}
			new showAlert("Record saved successfully", "Record saved successfully of name "+ comboName.getSelectionModel().getSelectedItem());
			doRefresh(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	doClearAll();
    }

    @FXML
    void doSearch(ActionEvent event) {
    	if(comboName.getSelectionModel().getSelectedItem().isBlank() || comboName.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Fill name field", "Name field is required to Searching. Fill paper name fields and try again.");
    		return;
    	}
    	try {
    		pst= conn.prepareStatement("SELECT * FROM newspaper_master WHERE Name= ?");
    		pst.setString(1, comboName.getSelectionModel().getSelectedItem());
    		table= pst.executeQuery();
    		if(!table.next()){
    			new showAlert("No similar record found", "No record found with name "+ comboName.getSelectionModel().getSelectedItem());
    			doClearAll();
    			return;
    		}
    		do {
    			float price= table.getFloat(2);
    			lblPrice.setText(price+"");
    		}while(table.next());
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }

    @FXML
    void doUpdate(ActionEvent event) {
    	if(lblPrice.getText().isBlank() || comboName.getSelectionModel().getSelectedItem().isEmpty()){
    		new showAlert("Fill all the fields", "All fields are required. Fill all fields and try again.");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("UPDATE newspaper_master SET Price= ? WHERE Name= ?");
			pst.setFloat(1, Float.parseFloat(lblPrice.getText()));
			pst.setString(2, comboName.getSelectionModel().getSelectedItem());
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Record updataion failed", "No newspaper found with name "+ comboName.getSelectionModel().getSelectedItem());
				doClearAll();
				return;
			}
			new showAlert("Record updated successfully", "Record updated successfully of newspaper " + comboName.getSelectionModel().getSelectedItem());
			doRefresh(null);
			
		} catch (SQLException e) {
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
			pst= conn.prepareStatement("SELECT DISTINCT Name FROM newspaper_master ");
			table= pst.executeQuery();
			
			while(table.next()){
				String name= table.getString(1); 
				comboName.getItems().add(name);
				//comboName.getItems().add(table.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    void doClearAll() {
    	lblPrice.setText(null);
    	comboName.setValue(null);
    	comboName.getItems().clear();
    	doFill();
    	
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
	 showAlert(String header, String msg) {
		 Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
	 }
 }
