package haekerDisplay;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<HawkerBean> tblView;
    
    Connection conn;
    PreparedStatement pst;
    ObservableList<HawkerBean> list;
    

    @FXML
    void btnShow(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	
    	try {
			pst= conn.prepareStatement("SELECT * FROM hawkers");
			ResultSet table= pst.executeQuery();
			
			while(table.next()) {
				String name= table.getString(1); 
				long mobile= table.getLong(2);
				String address= table.getString(4);
				long aadhar= table.getLong(5);
				String areaAsgn= table.getString(6);
				Date doj= table.getDate(7);
				HawkerBean obj= new HawkerBean(name, mobile, address, aadhar, areaAsgn, doj);
				System.out.println(name+"   "+ doj);
				list.add(obj);
			}
			tblView.setItems(list);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void initialize() {
    	conn= DatabaseConnector.getConnectionnn();
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
    
    void addCols() {
    	TableColumn<HawkerBean, String> nameCol= new TableColumn<HawkerBean, String>("Name kuch");
    	nameCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, String>("name"));
    	nameCol.setMaxWidth(100);
    	
    	tblView.getColumns().add(nameCol);
    	
    	TableColumn<HawkerBean, Long> mobileCol= new TableColumn<HawkerBean, Long>("Mobile no kuch");
    	mobileCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, Long>("contact"));
    	mobileCol.setMaxWidth(100);
    	
    	TableColumn<HawkerBean, String> addressCol= new TableColumn<HawkerBean, String>("Address kuch");
    	addressCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, String>("address"));
    	addressCol.setMaxWidth(100);
    	
    	TableColumn<HawkerBean, Long> aadharCol= new TableColumn<HawkerBean, Long>("Aadhar no kuch");
    	aadharCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, Long>("aadhar"));
    	aadharCol.setMaxWidth(100);
    	
    	TableColumn<HawkerBean, String> areaAssignedCol= new TableColumn<HawkerBean, String>("Area Assigned kuch");
    	areaAssignedCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, String>("areaAssigned"));
    	areaAssignedCol.setMaxWidth(100);
    	
//    	TableColumn<HawkerBean, Date> dateCol= new TableColumn<HawkerBean, Date>("Date of joining kuch");
//    	dateCol.setCellValueFactory(new PropertyValueFactory<HawkerBean, Date>("Date of joining"));
//    	dateCol.setMaxWidth(100);
//    	
    	tblView.getColumns().addAll(mobileCol, addressCol, aadharCol, areaAssignedCol);
    	
    	
    	
    	
//    	String name;
//    	long contact;
//    	String address;
//    	long aadhar;
//    	String areaAssigned;
//    	Date dateOfJoining;
    	
    }
}

/*
TableColumn<HawkerBean, Long> mobileCol= new Table


*/
