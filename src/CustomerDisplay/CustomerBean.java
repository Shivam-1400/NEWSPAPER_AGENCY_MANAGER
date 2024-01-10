package CustomerDisplay;

public class CustomerBean {
	long mobno;
	String name, area, hawker, selPaper;
	
	public long getMobno() {
		return mobno;
	}
	public void setMobno(long mobno) {
		this.mobno = mobno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHawker() {
		return hawker;
	}
	public void setHawker(String hawker) {
		this.hawker = hawker;
	}
	public String getSelPaper() {
		return selPaper;
	}
	public void setSelPaper(String selPaper) {
		this.selPaper = selPaper;
	}
	
	public CustomerBean(long mobno, String name, String area, String hawker, String selPaper) {
		super();
		this.mobno = mobno;
		this.name = name;
		this.area = area;
		this.hawker = hawker;
		this.selPaper = selPaper;
//		this.doStart = doStart;
	}
	
}
