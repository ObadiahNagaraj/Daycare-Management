package models;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;
//import java.util.List;


public class Daycare{
	String id;
	private String name,phoneNo,region;
	int fee;
	Child child;
	Status state=Status.NOT_ENROLLED;
	List<Comments> commentList;
//	public Daycare(String id, String name, String phoneNo, String region, int fee, List<Child> childList, Status state) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.phoneNo = phoneNo;
//		this.region = region;
//		this.fee = fee;
//		this.childList = childList;
//		this.state = state;
//	}
//	public Daycare() {
//		// TODO Auto-generated constructor stub
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public Child getChild() {
		return child;
	}
	public void setChild(Child child) {
		this.child = child;
	}
	public Status getState() {
		return state;
	}
	public void setState(Status state) {
		this.state = state;
	}	
}
