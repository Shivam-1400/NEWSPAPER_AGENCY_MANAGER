package Customer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;



//import com.sun.tools.javac.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class coustomerViewController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField txtMobNo;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<String> comboAreas;
    @FXML
    private ComboBox<String> comboHawker;
    @FXML
    private ListView<String> listPapersAv;
    @FXML
    private ListView<String> listPrice;
    @FXML
    private ListView<String> listSelected;
    @FXML
    private Button btnFetch;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnUnselect;
    @FXML
    private DatePicker dateDOStart;
    @FXML
    private Button btnClearAll;
    
    Connection conn;
    PreparedStatement pst;
    ResultSet table;
    String selpaper="";
    float cost=0;
    //ArrayList<Float> costList= new ArrayList<Float>();
    
    //String costListString= "";
    //List<Float> costList= new ArrayList<Float>();
    
    @FXML
    void doClearAll(ActionEvent event) {
    	selpaper="";
        cost=0;
        
    	txtAddress.setText(null);
    	txtMobNo.setText(null);
    	txtName.setText(null);
    	txtPrice.setText(null);
    	dateDOStart.setValue(null);
    	
    	comboHawker.getItems().clear();
    	listSelected.getItems().clear();
//    	costList.clear();
//    	costListString= "";
    	listPapersAv.getItems().clear();
    	listPrice.getItems().clear();
    	listSelected.getItems().clear();
    	initialize();
    	
    }

    @FXML
    void doFetch(ActionEvent event) {
    	
    	if(txtMobNo.getText()== null ||txtMobNo.getText().isBlank()) {
    		new showAlert("Mobile no. required", "Mobile number is required for fetching coustomers details. Fill mobile number and try again.");
    		return;
    	}
    	try {
    		Statement objj= conn.createStatement();
			pst= conn.prepareStatement("SELECT * FROM coustomers WHERE Phone=?");
			pst.setString(1, txtMobNo.getText());
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("No record found", "No record found with Mobile: "+ txtMobNo.getText()+". Check it and try again.");
				doClearAll(null);
				return;
			}
			do {
				txtName.setText(table.getString(2));
				txtAddress.setText(table.getString(3));
				txtPrice.setText(table.getFloat(7)+"");
				
//				comboAreas.getItems().clear();
//				comboAreas.setValue(table.getString(4));
//				comboHawker.getItems().clear();
//				comboHawker.setValue(table.getString(5));
//				String hawker= table.getString(5);
				//comboHawker.setSelectionModel(SingleSelectionModel<hawker>  );
				
				String paper= table.getString(6);
				List<String> paperList= Arrays.asList(paper.split(","));
				listSelected.getItems().addAll(paperList);
				
				//costListString= table.getString(8);-----------------------------------
				//List<String> costListList;
				//ArrayList<String> paper
				
				dateDOStart.setValue(LocalDate.parse(table.getDate(8)+""));
				
			}while(table.next());
		} catch (SQLException e) {

			e.printStackTrace();
		}
    }

    @FXML
    void doFillHawker(ActionEvent event) {
    	String ar= comboAreas.getSelectionModel().getSelectedItem();
    	comboHawker.getItems().clear();
    	try {
			pst= conn.prepareStatement("SELECT Name FROM hawkers WHERE AreaAssigned LIKE '%"+ ar +"%'");
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("No Hawkers Here", "No hawkers are assinged for this area. First assing hawker in this area and then add coustomer.");
				return;
			}
			do {
				comboHawker.getItems().add(table.getString(1));
			}while(table.next());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doFillSelected(MouseEvent event) {
    	if(event.getClickCount()== 2) {
    		listSelected.getItems().add(listPapersAv.getSelectionModel().getSelectedItem());
    		int indexx= listPapersAv.getSelectionModel().getSelectedIndex();
    		listPrice.getSelectionModel().select(indexx);
    		selpaper= selpaper+ listPapersAv.getSelectionModel().getSelectedItem()+" ,";
    		cost= cost+ (Float.parseFloat(listPrice.getItems().get(indexx)));
    		;
    		
    		//costList.add(Float.parseFloat(listPrice.getItems().get(indexx)));
    		//System.out.println(costList);
    		
//    		cost=0;costListString= "";
//    		for(float a: costList) {
//    			cost= cost+a;
//    			//costListString += a+",";
//    		}
    		//System.out.println(costListString);
    		txtPrice.setText(String.valueOf(cost));
    		
    		
    		  		
    	}
    }
    
    @FXML
    void doUnselect(ActionEvent event) {
    	cost= 0;
    	selpaper= "";
    	listSelected.getItems().clear();
    	txtPrice.setText("");
    	
    }

    @FXML
    void doModify(ActionEvent event) {
    	try {
			pst= conn.prepareStatement("UPDATE coustomers SET Name=?, Address=?, Area=?, Hawker=?, SelePaper=?, TotlPrice=?, DateStart=? WHERE Phone= ?");
			pst.setString(1, txtName.getText());
			pst.setString(2, txtAddress.getText());
			pst.setString(3, comboAreas.getSelectionModel().getSelectedItem());
			pst.setString(4, comboHawker.getSelectionModel().getSelectedItem());
			pst.setString(5, selpaper);
			pst.setFloat(6, cost);
			pst.setDate(7, Date.valueOf(dateDOStart.getValue()));
			pst.setLong(8, Long.parseLong(txtMobNo.getText()));
			
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Unable to modify record", "Record not modified. Check and try again");
				doClearAll(null);
				return;
			}
			new showAlert("Record modified successfully", "Record modified successfully with Name:"+ txtName.getText());
			doClearAll(null);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void doRemove(ActionEvent event) {
    	if(txtMobNo.getText()== null ||txtMobNo.getText().isBlank()) {
    		new showAlert("Mobile no. required", "Mobile number is required for deleting coustomers details. Fill mobile number and try again.");
    		return;
    	}
    	try {
    		pst= conn.prepareStatement("DELETE FROM coustomers WHERE  Phone= ?");
    		pst.setLong(1, Long.parseLong(txtMobNo.getText()));
    		int count= pst.executeUpdate();
    		if(count==0) {
    			new showAlert("Record deletion failed", "Unable to delete the record. Please check phone number and try again.");
    			doClearAll(null);
    			return;
    		}
    		new showAlert("Record deleted successfully", "Record with phone no. "+txtMobNo.getText() +" deleted successfully.");
    		doClearAll(null);
    		return;
    	}catch (SQLException e) {
    		e.printStackTrace();
		}
    }

    @FXML
    void doSave(ActionEvent event) {
    	
    	try {
			pst= conn.prepareStatement("INSERT INTO coustomers VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setLong(1, Long.parseLong(txtMobNo.getText()));
			pst.setString(2, txtName.getText());
			pst.setString(3, txtAddress.getText());
			pst.setString(4, comboAreas.getSelectionModel().getSelectedItem());
			pst.setString(5, comboHawker.getSelectionModel().getSelectedItem());
			//String area="";
			pst.setString(6,selpaper.substring(0, selpaper.length()-1) );
			pst.setFloat(7, cost);
			pst.setDate(8, Date.valueOf(dateDOStart.getValue()));
//			pst.setString(9, costListString.substring(0, costListString.length()-1));
			
			int count= pst.executeUpdate();
			if(count==0) {
				new showAlert("Unable to save record", "Record not saved. Check and try again");
				doClearAll(null);
				return;
			}
			new showAlert("Record saved", "Record saved successfully with Name:"+ txtName.getText());
			doClearAll(null);
			
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
				comboAreas.getItems().add(area);
			}
			
			pst= conn.prepareStatement("SELECT * FROM newspaper_master");
			table= pst.executeQuery();

			while(table.next()) {
				listPapersAv.getItems().add(table.getString(1));
				listPrice.getItems().add(String.valueOf(table.getFloat(2)));
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

