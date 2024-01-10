package GenerateBill;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class GenBillViewController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField txtDateStrat;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<String> comboMob;
    @FXML
    private TextField txtBill;
    @FXML
    private DatePicker dateUpTo;

    Connection conn;
    ResultSet table;
    PreparedStatement pst;
    float bill=0, price=0;
    int days=0;
    
//    LocalDate start;
//    LocalDate end;
    Date startt;
    Date endd;
    
    
    @FXML
    void doBill(ActionEvent event) {
    	if(txtPrice.getText().isBlank() || txtPrice.getText()== null || txtDateStrat.getText().isBlank() || dateUpTo.equals(null) ) {
    		new showAlert("Fields empty", "Unable to do process. Field are empty. First fetch the customers details. Then calculate bill.");
    		return;
    	}
    	startt= Date.valueOf(txtDateStrat.getText());
//    			LocalDate.parse(txtDateStrat.getText());
    	bill= countDays(startt) * price;
    	txtBill.setText(String.valueOf(bill));
    	
    }
    
    int countDays(Date start) {
    	endd= Date.valueOf(dateUpTo.getValue());
//    	int count=1;
//    	if(endd== null) {
//    		new showAlert("Fields empty", "Unable to do process. Field are empty. First fetch the customers details. Then calculate bill.");
//    		return 0;
//    	}
//    	
//    	
//    	while(!(endd.equals(start))) {
//    		count++;
//    		startt=startt.plusDays(1);
//    	}
    	
    	
    	LocalDate st= startt.toLocalDate();
    	LocalDate ed= endd.toLocalDate();
    	int count= (int)ChronoUnit.DAYS.between(st, ed);
    	System.out.println("Number of days= "+count);
    	return count;
    	
    }
    
    @FXML
    void doClearAll(ActionEvent event) {
    	bill=0;
    	price= 0;
    	days= 0;
    	comboMob.setValue(null);
    	txtBill.setText(null);
    	txtDateStrat.setText(null);
    	txtPrice.setText(null);
    	dateUpTo.setValue(null);
    	comboMob.getItems().clear();
    	startt= null;
    	doFill();
    
    }

    @FXML
    void doFetch(ActionEvent event) {
    	if(comboMob.getSelectionModel().getSelectedItem()== null || comboMob.getSelectionModel().isEmpty()) {
    		new showAlert("Please Fill Mobile number", "Mobile number is required for fetching data. Fill it and try again");
    		return;
    	}
    	try {
			pst= conn.prepareStatement("SELECT * FROM coustomers WHERE Phone= ?");
			pst.setLong(1, Long.parseLong(comboMob.getSelectionModel().getSelectedItem()));
			table= pst.executeQuery();
			if (!table.next()) {
				new showAlert("No record found", "No recourd found with Mobile no: "+ comboMob.getSelectionModel().getSelectedItem()+ ". Check number and try again");
				return;
			}
			do {
				price= table.getFloat(7);
				startt= table.getDate(8);
				txtDateStrat.setText(String.valueOf(table.getDate(8)));
				txtPrice.setText(String.valueOf(price));
			}while(table.next());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doSave(ActionEvent event) {
    	try {
			pst= conn.prepareStatement("INSERT INTO bills VALUES(?, ?, ?, ?, ?, ?)",
			Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, null);
			pst.setLong(2, Long.parseLong(comboMob.getSelectionModel().getSelectedItem()));
			pst.setDate(3, startt);
			pst.setDate(4, endd);
			pst.setFloat(5, bill);
			pst.setInt(6, 0);
			
			int count= pst.executeUpdate();
			int genId= 0;
			ResultSet id= pst.getGeneratedKeys();
			if(count==0) {
				new showAlert("Record not saved", "Record not saved. Check connection and try again");
				doClearAll(null);
				return;
			}
			if(id.next()) {
				genId= id.getInt(1);
				pst= conn.prepareStatement("UPDATE coustomers SET DateStart=? WHERE Phone=?");
				pst.setDate(1, endd);
				pst.setLong(2, Long.parseLong(comboMob.getSelectionModel().getSelectedItem()));
				int a=pst.executeUpdate();
				if(a==1) {
					new showAlert("Record saved successfully", "Record saved successfully with ID: "+ genId+". Do remember it for future use");
				}
			}
			doClearAll(null);
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
			pst= conn.prepareStatement("SELECT DISTINCT Phone FROM coustomers");
			table= pst.executeQuery();
			while(table.next()) {
				comboMob.getItems().add(table.getLong(1)+"");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

