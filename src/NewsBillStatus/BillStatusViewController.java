package NewsBillStatus;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;



public class BillStatusViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radioPaid;

    @FXML
    private ToggleGroup fetchOption;

    @FXML
    private RadioButton radioUnpaid;

    @FXML
    private RadioButton radioAll;

    @FXML
    private ComboBox<String> comboContact;

    @FXML
    private TableView<BillBean> tblView;
    
    Connection conn;
    PreparedStatement pst;
    ResultSet table;
    ObservableList<BillBean> list;

    @FXML
    void doFetch(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	if(radioAll.isSelected()) {
    		try {
				pst= conn.prepareStatement("SELECT * FROM bills");
				table=pst.executeQuery();
				if(!table.next()) {
					new showAlert("No bill to show", "Error showing bills. Generate some bills and try again");
					return;
				}
				do {
					long ph= table.getLong(2);
					Date start= table.getDate(3);
					Date end= table.getDate(4);
					float amount= table.getFloat(5);
					String status= getStrStatus(table.getInt(6));
//					System.out.println(ph+" "+start+" "+end+" "+amount+" "+status);
					BillBean obj= new BillBean(ph, start, end, amount, status);
					list.add(obj);
					
				}while(table.next());
				tblView.setItems(list);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	else if(radioPaid.isSelected()) {
    		try {
				pst= conn.prepareStatement("SELECT * FROM bills WHERE Status=1");
				table=pst.executeQuery();
				if(!table.next()) {
					new showAlert("No bill to show", "Error showing bills. Generate some bills and try again");
					return;
				}
				do {
					long ph= table.getLong(2);
					Date start= table.getDate(3);
					Date end= table.getDate(4);
					float amount= table.getFloat(5);
					String status= getStrStatus(table.getInt(6));
//					System.out.println(ph+" "+start+" "+end+" "+amount+" "+status);
					BillBean obj= new BillBean(ph, start, end, amount, status);
					list.add(obj);
					
				}while(table.next());
				tblView.setItems(list);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	else if(radioUnpaid.isSelected()) {
    		try {
				pst= conn.prepareStatement("SELECT * FROM bills WHERE Status=0");
				table=pst.executeQuery();
				if(!table.next()) {
					new showAlert("No bill to show", "Error showing bills. Generate some bills and try again");
					return;
				}
				do {
					long ph= table.getLong(2);
					Date start= table.getDate(3);
					Date end= table.getDate(4);
					float amount= table.getFloat(5);
					String status= getStrStatus(table.getInt(6));
//					System.out.println(ph+" "+start+" "+end+" "+amount+" "+status);
					BillBean obj= new BillBean(ph, start, end, amount, status);
					list.add(obj);
					
				}while(table.next());
				tblView.setItems(list);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	else {
    		new showAlert("Field empty", "Select Paid, Un-Paid or All bills to show. And try again");
    	}

    }

    @FXML
    void doShowBill(ActionEvent event) {
    	list= FXCollections.observableArrayList();
    	long phn= Long.parseLong(comboContact.getSelectionModel().getSelectedItem());
    	try {
			pst= conn.prepareStatement("SELECT * FROM bills WHERE  Phone=?");
			pst.setLong(1, phn);
			table=pst.executeQuery();
			if(!table.next()) {
				new showAlert("No bill to show", "Error showing bills. Generate some bills and try again");
				return;
			}
			do {
				long ph= table.getLong(2);
				Date start= table.getDate(3);
				Date end= table.getDate(4);
				float amount= table.getFloat(5);
				String status= getStrStatus(table.getInt(6));
//				System.out.println(ph+" "+start+" "+end+" "+amount+" "+status);
				BillBean obj= new BillBean(ph, start, end, amount, status);
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
    
    void doFill() {
    	try {
			pst= conn.prepareStatement("SELECT DISTINCT Phone FROM coustomers");
			table= pst.executeQuery();
			while(table.next()) {
				comboContact.getItems().add(table.getLong(1)+"");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    String getStrStatus(int a) {
    	if (a==0) 
			return ("Un-Paid");
		return ("Paid");
    }
    void addCols() {		
//    	long mobno;
//    	String status;
//    	Date startt, endd;
//    	float amount;
		TableColumn<BillBean, Long>mobCol= new TableColumn<BillBean, Long>("Mobile");
		mobCol.setCellValueFactory(new PropertyValueFactory<BillBean, Long>("mobno"));
		mobCol.setMinWidth(104);
		
		TableColumn<BillBean, Date>strtt= new TableColumn<BillBean, Date>("Start Date");
		strtt.setCellValueFactory(new PropertyValueFactory<BillBean, Date>("startt"));
		strtt.setMinWidth(90);
		
		TableColumn<BillBean, Date>end= new TableColumn<BillBean, Date>("End Date");
		end.setCellValueFactory(new PropertyValueFactory<BillBean, Date>("endd"));
		end.setMinWidth(90);
		
		TableColumn<BillBean, Float>amnt= new TableColumn<BillBean, Float>("Amount");
		amnt.setCellValueFactory(new PropertyValueFactory<BillBean, Float>("amount"));
		amnt.setMinWidth(90);
		
		TableColumn<BillBean, String>status= new TableColumn<BillBean, String>("Status");
		status.setCellValueFactory(new PropertyValueFactory<BillBean, String>("status"));
		status.setMinWidth(80);
		
		
		tblView.getColumns().addAll(mobCol, strtt, end, amnt, status);
		
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
