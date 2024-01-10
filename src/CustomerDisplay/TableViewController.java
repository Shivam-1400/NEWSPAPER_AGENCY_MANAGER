package CustomerDisplay;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<CustomerBean> tblView;


    Connection conn;
    PreparedStatement pst;
    ObservableList<CustomerBean> list;
    ResultSet table;
    
    @FXML
    void doAreaSearch(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	if(comboArea.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Select Area", "Area not selected. Select one and try again");
    		return;
    	}
    	String ar= comboArea.getSelectionModel().getSelectedItem();
    	try {
			pst= conn.prepareStatement("SELECT  Phone, Name, Hawker, SelePaper, DateStart FROM coustomers WHERE Area= ?");
			pst.setString(1, ar);
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("No Record found", "No record found for Area: "+ar);
				return;
			}
			do{
				long phno= table.getLong(1);
				String name= table.getString(2);
				String area= comboArea.getSelectionModel().getSelectedItem();
				String hawker= table.getString(3);
				String news= table.getString(4);
				
				CustomerBean obj= new CustomerBean(phno, name, area, hawker, news );
				
				System.out.println(table.getLong(1)+ table.getString(2)+comboArea.getSelectionModel().getSelectedItem()+table.getString(3)+ table.getString(4)+ table.getDate(5) );
				list.add(obj);
			}while(table.next());
			
			tblView.setItems(list);
			
		} catch (SQLException e) {
			// TODO Auto-generatSed catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doPaperSearch(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	if(comboPaper.getSelectionModel().getSelectedItem().isEmpty()) {
    		new showAlert("Select Newspaper", "Newspaper not selected. Select one and try again");
    		return;
    	}
    	String ppr= comboPaper.getSelectionModel().getSelectedItem();
    	try {
			pst= conn.prepareStatement("SELECT  Phone, Name, Hawker, Area, DateStart FROM coustomers WHERE SelePaper LIKE '%"+ppr+"%'");
			table= pst.executeQuery();
			if(!table.next()) {
				new showAlert("No record found", "No record found for the newspaper "+ppr);
				return;
			}
			do{
				long phno= table.getLong(1);
				String name= table.getString(2);
				String paper= comboPaper.getSelectionModel().getSelectedItem();
				String hawker= table.getString(3);
				String area= table.getString(4);
				
				CustomerBean obj= new CustomerBean(phno, name, area, hawker, paper );
				
				System.out.println(table.getLong(1)+ table.getString(2)+comboArea.getSelectionModel().getSelectedItem()+table.getString(3)+ table.getString(4)+ table.getDate(5) );
				list.add(obj);
			}while(table.next());
			tblView.setItems(list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
//Phone    Name    Address    Area    Hawker    SelePaper    TotlPrice    DateStart    CostList

    @FXML
    void btnShowAll(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	try {
			pst= conn.prepareStatement("SELECT  Phone, Name, Hawker, SelePaper, DateStart, Area FROM coustomers");
			table= pst.executeQuery();
			if(!table.next()) {
	    		new showAlert("No records found", "No records are saved in customers table.");
	    	}
			do {
				long phno= table.getLong(1);
				String name= table.getString(2);
				String paper= table.getString(3);
				String hawker= table.getString(4);
				String area= table.getString(6);
				
				CustomerBean obj= new CustomerBean(phno, name, area, paper, hawker );
				
				System.out.println(table.getLong(1)+ table.getString(2)+comboArea.getSelectionModel().getSelectedItem()+table.getString(3)+ table.getString(4)+ table.getDate(5) );
				list.add(obj);
				
				
			}while(table.next());
			tblView.setItems(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
  
    @FXML
    void initialize() {
    	conn= DatabaseConnector.getConnectionnn();
    	doFill();
        addCols();
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
    
    void doFill() {
    	ResultSet resultList;
    	try {
			pst=conn.prepareStatement("SELECT Area FROM area_master");
			resultList= pst.executeQuery();
			
			while(resultList.next()) {
				comboArea.getItems().add(resultList.getString(1));
			}
			
			pst=conn.prepareStatement("SELECT Name FROM newspaper_master");
			resultList= pst.executeQuery();
			
			while(resultList.next()) {
				comboPaper.getItems().add(resultList.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    void addCols() {
    	
    	TableColumn<CustomerBean, Long> mobCol= new TableColumn<CustomerBean, Long>("Mobile");
    	mobCol.setCellValueFactory(new PropertyValueFactory<CustomerBean, Long>("mobno"));
    	mobCol.setMinWidth(120);
    	
    	TableColumn<CustomerBean, String> nameCol= new TableColumn<CustomerBean, String>("Name");
    	nameCol.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("name"));
    	nameCol.setMinWidth(120);
    	
    	TableColumn<CustomerBean, String> areaCol= new TableColumn<CustomerBean, String>("Area");
    	areaCol.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("area"));
    	areaCol.setMinWidth(100);
    	
    	TableColumn<CustomerBean, String> hawkerCol= new TableColumn<CustomerBean, String>("Hawker");
    	hawkerCol.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("hawker"));
    	hawkerCol.setMinWidth(100);
    	
    	TableColumn<CustomerBean, String> selPaperCol= new TableColumn<CustomerBean, String>("Selected Paper");
    	selPaperCol.setCellValueFactory(new PropertyValueFactory<CustomerBean, String>("selPaper"));
    	selPaperCol.setMinWidth(120);
    	
    	tblView.getColumns().addAll(mobCol, nameCol, areaCol, hawkerCol, selPaperCol);
    	
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
