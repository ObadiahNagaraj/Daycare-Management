package models;

import java.util.List;

public class Mother {
	private String regNo;
	private String name,phoneNo,region;
	List<Child> childList;
	Daycare dayCare;
//	public Mother(String regNo, String name, String phoneNo, String region,List<Child> childList) {
//		this.regNo = regNo;
//		this.name = name;
//		this.phoneNo = phoneNo;
//		this.region = region;
//		this.childList=childList;
//	}
//	public Mother() {
//		// TODO Auto-generated constructor stub
//	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}
	public List<Child> getChildList() {
		return childList;
	}
	public Daycare getDayCare() {
		return dayCare;
	}
	protected void setDayCare(Daycare dayCare) {
		this.dayCare = dayCare;
	}
}
