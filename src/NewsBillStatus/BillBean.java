package NewsBillStatus;

import java.sql.Date;

public class BillBean {
	long mobno;
	String status;
	Date startt, endd;
	float amount;
	public long getMobno() {
		return mobno;
	}
	public void setMobno(long mobno) {
		this.mobno = mobno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartt() {
		return startt;
	}
	public void setStartt(Date startt) {
		this.startt = startt;
	}
	public Date getEndd() {
		return endd;
	}
	public void setEndd(Date endd) {
		this.endd = endd;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public BillBean(long mobno, Date startt, Date endd, float amount, String status) {
		super();
		this.amount=amount;
		this.endd=endd;
		this.startt=startt;
		this.mobno= mobno;
		this.status=status;
	}

}
