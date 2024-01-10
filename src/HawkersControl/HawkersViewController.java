package HawkersControl;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class HawkersViewController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAreaAssigned;
    @FXML
    private DatePicker dateDOJ;
    @FXML
    private TextField txtAadhar;
    @FXML
    private Button btnRegistre;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnBrowse;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ComboBox<String> comboAreaAv;
    @FXML
    private ComboBox<String> comboName;
    @FXML
    private Button btnSerch;

    Connection conn;
    PreparedStatement pst;
    ResultSet table;
    String picPath;
    File selectedFile= null;
    String Ar= ""; 
    
    @FXML
    void doAddArea(ActionEvent event) {
    	String slarea= comboAreaAv.getSelectionModel().getSelectedItem();
    	Ar= Ar + slarea +", ";
    	
    	txtAreaAssigned.setText(Ar.substring(0, Ar.length()-2));
    	//comboAreaAv.getItems().remove(slarea);												//doubt
    	
    }
    @FXML
    void doBrowse(ActionEvent event) {
    	Window primaryStage = null;
		selectedFile= new FileChooser().showOpenDialog(primaryStage);
		imageViewer.setImage(new Image(selectedFile.toURI().toString()));
		picPath= selectedFile.toString();
    }

    @FXML
    void doClear(ActionEvent event) {
    	picPath= "";
        selectedFile= null;
        Ar= ""; 
        
    	comboName.setValue(null);
    	comboAreaAv.setValue(null);
    	txtContact.setText(null);
    	txtAddress.setText(null);
    	txtAadhar.setText(null);
    	txtAreaAssigned.setText(null);
    	dateDOJ.setValue(null);
    	 	
    	File profile= new File("C:\\Users\\Dell\\Pictures\\project newspaper\\profile.jpg");
		imageViewer.setImage(new Image(profile.toURI().toString()));
		

    	comboName.setValue(null);
//    	comboAreaAv.setValue(null);
		comboName.getItems().clear();
		comboAreaAv.getItems().clear();
		doFill();
    }

    @FXML
    void doDelete(ActionEvent event) {
    	if(comboName.getSelectionModel().getSelectedItem()== null) {
    		new showAlert("Fill name field", "Name field required for deletion. Fill name field and try again");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("DELETE FROM hawkers WHERE NAME= ?");
			pst.setString(1, comboName.getSelectionModel().getSelectedItem());
			int count= pst.executeUpdate();
			if(count==0){
				new showAlert("Record Deletion failed", "No record found with Name: "+ comboName.getSelectionModel().getSelectedItem());
				return;
			}
			new showAlert("Record deleted successfully", "Record with Name: "+ comboName.getSelectionModel().getSelectedItem() +" deleted sucessfully.");
			doClear(null);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doModify(ActionEvent event) {
    	if(checkFields()== -1) {
    		new showAlert("Fill all the fields", "Please fill all the fields and try again");
    		return;
    	}  
    	try {
    		pst= conn.prepareStatement("UPDATE hawkers SET Contact=?, PicPath=?, Address=?, Aadhar=?, AreaAssigned=?, DateOfJoining=? WHERE Name= ? ");
    		
    		picPath= selectedFile.toString();
    		pst.setLong(1, Long.parseLong(txtContact.getText()));
    		pst.setString(2, picPath);
    		pst.setString(3, txtAddress.getText());
    		pst.setLong(4, Long.parseLong(txtAadhar.getText()));
    		pst.setString(5, txtAreaAssigned.getText());
    		pst.setDate(6, Date.valueOf(dateDOJ.getValue()));
    		pst.setString(7, comboName.getSelectionModel().getSelectedItem());
    		
    		int count= pst.executeUpdate();
    		if(count==0) {
    			new showAlert("Record updation failed", "Record updation failed. Please check connectivity and try again");
    			doClear(null);
    			return;
    		}
    		new showAlert("Record updated successfully", "Record updated successfully of Name: "+ comboName.getSelectionModel().getSelectedItem());
    		doClear(null);
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void doRegister(ActionEvent event) {
    	
    	if(checkFields()== -1) {
    		new showAlert("Fill all the fields", "Please fill all the fields and try again");
    		return;
    	}  
    	String nm=comboName.getSelectionModel().getSelectedItem();
    	
    	try {
    		pst= conn.prepareStatement("SELECT DISTINCT Name FROM hawkers");
			ResultSet table2= pst.executeQuery();
			
			while(table2.next()){
				String name= table2.getString(1);
				if(name.equals(comboName.getSelectionModel().getSelectedItem())) {
					new showAlert("Hawker with same already exists", "A Hawker with Name: "+name+" already exists. Do add Some different characters in it and try again");
					
					return;
				}
			}
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
    	}
    	
    	
    	try {
			pst= conn.prepareStatement("INSERT INTO hawkers VALUES (?, ?, ?, ?, ?, ?, ?)");
			pst.setString(1, nm);
			pst.setLong(2, Long.parseLong(txtContact.getText()));
			pst.setString(3, picPath);
			pst.setString(4, txtAddress.getText());
			pst.setLong(5, Long.parseLong(txtAadhar.getText()));
			pst.setString(6, txtAreaAssigned.getText());
			pst.setDate(7, Date.valueOf(dateDOJ.getValue()));
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Record not saved", "Record not saved due to some error. Please check your connectivity and try again.");
				return;
			}
			new showAlert("Record saved successfully", "Record saved successfully to the database");
			doClear(null);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doSearch(ActionEvent event) {
    	if(comboName.getSelectionModel().getSelectedItem()== null) {
    		new showAlert("Fill name field", "Name field required for searching. Fill name field and try again");
    		return;
    	}
    	  	
    	try {
			pst= conn.prepareStatement("SELECT * FROM hawkers WHERE Name= ?");
			pst.setString(1, comboName.getSelectionModel().getSelectedItem());
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("No Record found", "No record found with Name: "+comboName.getSelectionModel().getSelectedItem()+". Check name and try again");
				comboName.setValue(null);
				return;
			}
			do {
				//comboName.setValue(table.getString(1));
				txtContact.setText(String.valueOf(table.getLong(2)));
				txtAddress.setText(table.getString(4));
				txtAadhar.setText(table.getString(5));
				txtAreaAssigned.setText(table.getString(6));
				dateDOJ.setValue(LocalDate.parse(table.getString(7)));
				
				selectedFile= new File(table.getString(3));
				imageViewer.setImage(new Image(selectedFile.toURI().toString()));
				picPath= selectedFile.toString();
				
				dateDOJ.setValue(LocalDate.parse(table.getDate(7)+""));
				
			}while(table.next());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }

    @FXML
    void initialize() {
    	conn= DatabaseConnector.getConnectionnn();
    	if(conn== null) {
    		new showAlert("Connection Faliure", "Unable to connect to the database. Check your connection and try again");
    		return;
    	}
        doFill();
    }
    
    void doFill() {
    	try {
			pst= conn.prepareStatement("SELECT DISTINCT Area FROM area_master ");
			table= pst.executeQuery();
			
			while(table.next()){
				String area= table.getString(1);
				comboAreaAv.getItems().add(area);
			}
			
			pst= conn.prepareStatement("SELECT DISTINCT Name FROM hawkers");
			table= pst.executeQuery();
			
			while(table.next()){
				String name= table.getString(1);
				comboName.getItems().add(name);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    int checkFields(){
    	if(comboName.getSelectionModel().getSelectedItem()== null|| txtContact.getText().isBlank() || txtAddress.getText().isEmpty() ||txtAadhar.getText().isEmpty() || txtAreaAssigned.getText().isEmpty() || picPath== null || dateDOJ.getValue()== null  ) {
    		
    		return -1;
    	}
    	return 1;
    }

}

class DatabaseConnector {
	public static Connection getConnectionnn() {
		Connection con= null;
		
		try {
			con= DriverManager.getConnection("jdbc:mysql://localhost/project_jdbc", "root", "");
			//System.out.println("Connection with Database is Successful.");
		} catch (SQLException e) {
			 
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

