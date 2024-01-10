package NewsBillCollection;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BillCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboPhNo;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtTo;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnGetBill;

    @FXML
    private Button btnGenerateBill;
    
    Connection conn;
    PreparedStatement pst;
    ResultSet table;
    
    Date start, end;
    float amount=0;
    Long phno;
    
    @FXML
    void DoPayBill(ActionEvent event) {
    	phno= Long.parseLong(comboPhNo.getSelectionModel().getSelectedItem());
    	try {
			pst= conn.prepareStatement("UPDATE bills SET Status=1 WHERE Phone=?");
			pst.setLong(1, phno);
			int a= pst.executeUpdate();
			if(a==0) {
				new showAlert("Error in saving payment details", "Error in saving details of payment. Check your connection and try again");
				return;
			}
			new showAlert("Payment Successful", "Payment record for Phone number "+phno+" was successfully saved in records.");
			doClearAll(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void doClearAll(ActionEvent event) {
    	txtAmount.setText(null);
    	txtFrom.setText(null);
    	txtTo.setText(null);
    	comboPhNo.getSelectionModel().select(null);
    	btnGenerateBill.setDisable(true);    	
    }

    @FXML
    void doGenerateBill(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GenerateBill/GenBillView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doGetBill(ActionEvent event) {
    	if(comboPhNo.getSelectionModel().getSelectedItem()== "") {
    		new showAlert("Empty field", "Select Phone number and try again");
    		return;
    	}
    	long phno= Long.parseLong(comboPhNo.getSelectionModel().getSelectedItem());
    	try {
			pst= conn.prepareStatement("SELECT * FROM bills WHERE GenKey=(SELECT MAX(GenKey) FROM bills WHERE Phone=? AND Status=0)");
			pst.setLong(1, phno);
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("Bill not generated yet", "Bill for "+phno+" is not generated till now. Go to Generate bill and try again");
				btnGenerateBill.setDisable(false);
				return;
			}
			do{
				start= table.getDate(3);
				end= table.getDate(4);
				amount=table.getFloat(5);
				phno=table.getLong(2);
				System.out.println(table.getInt(1)+" "+phno+" "+start+" "+end+" "+amount+" "+table.getInt(6));
				txtAmount.setText(amount+"");;
				txtFrom.setText(start+"");
				txtTo.setText(end+"");
			}while(table.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception");
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
    	doFillCombo();
    }
    void doFillCombo() {
    	try {
			pst= conn.prepareStatement("SELECT Phone FROM coustomers");
			table= pst.executeQuery();
			while(table.next()) {
				long ph= table.getLong(1);
				comboPhNo.getItems().add(ph+"");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}    	
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
