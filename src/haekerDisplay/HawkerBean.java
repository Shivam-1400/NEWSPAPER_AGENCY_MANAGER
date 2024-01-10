package haekerDisplay;

import java.sql.Date;

public class HawkerBean {
	String name;
	long contact;
	String address;
	long aadhar;
	String areaAssigned;
	Date dateOfJoining;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getAadhar() {
		return aadhar;
	}
	public void setAadhar(long aadhar) {
		this.aadhar = aadhar;
	}
	public String getAreaAssigned() {
		return areaAssigned;
	}
	public void setAreaAssigned(String areaAssigned) {
		this.areaAssigned = areaAssigned;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public HawkerBean(String name, long contact, String address, long aadhar, String areaAssigned, Date dateOfJoining) {
		super();
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.aadhar = aadhar;
		this.areaAssigned = areaAssigned;
		this.dateOfJoining = dateOfJoining;
	}
	
}
