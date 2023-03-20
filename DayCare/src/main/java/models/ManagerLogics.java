package models;

import java.awt.image.DataBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;

import connections.DbConnection;

public class ManagerLogics {
	public JSONArray viewMothers(String region) {
		System.out.println(region);
		JSONArray arr=new JSONArray();
		List<Mother> mothersList=new ArrayList<Mother>();
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Mother where region like ?");
			psmt.setString(1, region);
			ResultSet rs=psmt.executeQuery();
			while(rs.next()) {
				Mother mom=new Mother();
				mom.setRegNo(rs.getString(1));
				mom.setName(rs.getString(2));
				mom.setPhoneNo(rs.getString(3));
				mom.setRegion(rs.getString(4));
				List<Child> childList=new ArrayList<Child>();
				PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo like ?");
				psmt1.setString(1, mom.getRegNo());
				ResultSet rs1=psmt1.executeQuery();
				while(rs1.next()) {
					Child child=new Child();
					child.setName(rs1.getString(2));
					child.setAge(Period.between(LocalDate.parse(rs1.getString(4)), LocalDate.now()).getYears());
					child.setClass(rs1.getString(5));
					child.setSchool(rs1.getString(3));
					childList.add(child);
				}
				mom.setChildList(childList);
				PreparedStatement psmt2=DbConnection.dbConnection.prepareStatement("select * from Daycare where dayCareId like ?");
				psmt2.setString(1,rs.getString(5));
				ResultSet rs2=psmt2.executeQuery();
				if(rs2.next()) {
					Daycare daycare=new Daycare();
					daycare.setId(rs2.getString(1));
					mom.setDayCare(daycare);
				}
				mothersList.add(mom);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for(int i=0;i<mothersList.size();i++) {
			JSONObject obj=new JSONObject();
			obj.put("name", mothersList.get(i).getName());
			obj.put("registerNumber", mothersList.get(i).getRegNo());
			obj.put("number", mothersList.get(i).getPhoneNo());
			obj.put("region", mothersList.get(i).getRegion());
			if(mothersList.get(i).getDayCare()!=null)
				obj.put("daycare", mothersList.get(i).getDayCare().getId());
			else
				obj.put("daycare", mothersList.get(i).getDayCare());
			JSONArray arr2=new JSONArray();
			for(int j=0;j<mothersList.get(i).childList.size();j++) {
				JSONObject obj2=new JSONObject();
				obj2.put("childName"+j, mothersList.get(i).childList.get(j).getName());
				obj2.put("childAge"+j, mothersList.get(i).childList.get(j).getAge());
				obj2.put("childClass"+j, mothersList.get(i).childList.get(j).getClass1());
				obj2.put("childSchool"+j, mothersList.get(i).childList.get(j).getSchool());
				arr2.add(obj2);
			}
			obj.put("childList", arr2);
			arr.add(obj);	
		}
		return arr;
	}
	public JSONArray viewDaycares(String userId) {
		JSONArray  arr=new JSONArray();
		String registeredOrNot="";
		Mother mom=null;
		List<Daycare> daycareList=new ArrayList<Daycare>();
		try {
			PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo=?");
			psmt1.setString(1, userId);
			ResultSet rs1=psmt1.executeQuery();
			if(rs1.next()) {
				registeredOrNot=rs1.getString(5);
				arr.add(registeredOrNot);
				mom=new Mother();
				mom.setRegion(rs1.getString(4));
			}
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Daycare where status='NOT_ENROLLED' and region like ?");
			psmt.setString(1, mom.getRegion());
			ResultSet rs=psmt.executeQuery();
			while(rs.next()) {
				Daycare daycare=new Daycare();
				daycare.setId(rs.getString(1));
				daycare.setName(rs.getString(2));
				daycare.setPhoneNo(rs.getString(3));
				daycare.setRegion(rs.getString(4));
				daycare.setFee(rs.getInt(5));
				PreparedStatement psmt2=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo=?");
				psmt2.setString(1, daycare.getId());
				ResultSet rs2=psmt2.executeQuery();
				if(rs2.next()) {
					Child child=new Child();
					child.setName(rs2.getString(2));
					child.setAge(Period.between(LocalDate.parse(rs2.getString(4)), LocalDate.now()).getYears());
					child.setClass(rs2.getString(5));
					child.setSchool(rs2.getString(3));
					daycare.setChild(child);
				}
				daycareList.add(daycare);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Daycare i:daycareList) {
			JSONObject obj=new JSONObject();
			obj.put("registerNo", i.getId());
			obj.put("name", i.getName());
			obj.put("number", i.getPhoneNo());
			obj.put("region", i.getRegion());
			obj.put("fee", i.getFee());
			obj.put("childName", i.getChild().getName());
			obj.put("childAge", i.getChild().getAge());
			obj.put("childClass", i.getChild().getClass1());
			obj.put("childSchool", i.getChild().getSchool());
			arr.add(obj);
		}
		return arr;
	}
	public JSONArray viewComments(String daycareId,String userId) {
		JSONArray  arr=new JSONArray();
		List<Comments> commentsList=new ArrayList<Comments>();
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Comments where dayCareId like ?");
			psmt.setString(1, daycareId);
			ResultSet rs=psmt.executeQuery();
			while(rs.next()) {
				Comments comment=new Comments();
				comment.setComment(rs.getString(3));
				comment.setStar(rs.getInt(4));
				Mother mom=new Mother();
				mom.setRegNo(rs.getString(5));
				PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select name from Mother where regNo=?");
				psmt1.setString(1, mom.getRegNo());
				ResultSet rs1=psmt1.executeQuery();
				if(rs1.next()) {
					mom.setName(rs1.getString(1));
				}
				comment.setGivenBy(mom);
//				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
//				Date date = sdf1.format(rs.getDate(6));  
				SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy");
				comment.setDate(sdf2.format(rs.getDate(6)));
				commentsList.add(comment);
			}
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		for(Comments i:commentsList) {
			JSONObject obj=new JSONObject();
			obj.put("star", i.getStar());
			obj.put("comment", i.getComment());
			obj.put("givenBy", i.getGivenBy().getRegNo().equals(userId)?"You":i.getGivenBy().getName());
			obj.put("date", i.getDate());
			arr.add(obj);
		}
		return arr;
	}
	
	public JSONArray viewDaycares(String role,String region) {
		if(role.equals("admin")) {
			JSONArray  arr=new JSONArray();
			List<Daycare> daycareList=new ArrayList<Daycare>();
			try {
				PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Daycare where region like ?");
				psmt.setString(1, region);
				ResultSet rs=psmt.executeQuery();
				while(rs.next()) {
					Daycare daycare=new Daycare();
					daycare.setId(rs.getString(1));
					daycare.setName(rs.getString(2));
					daycare.setPhoneNo(rs.getString(3));
					daycare.setRegion(rs.getString(4));
					daycare.setFee(rs.getInt(5));
					daycare.setState(Status.valueOf(rs.getString(6)));
					PreparedStatement psmt2=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo=?");
					psmt2.setString(1, daycare.getId());
					ResultSet rs2=psmt2.executeQuery();
					if(rs2.next()) {
						Child child=new Child();
						child.setName(rs2.getString(2));
						child.setAge(Period.between(LocalDate.parse(rs2.getString(4)), LocalDate.now()).getYears());
						child.setClass(rs2.getString(5));
						child.setSchool(rs2.getString(3));
						daycare.setChild(child);
					}
					daycareList.add(daycare);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(Daycare i:daycareList) {
				JSONObject obj=new JSONObject();
				obj.put("registerNo", i.getId());
				obj.put("name", i.getName());
				obj.put("number", i.getPhoneNo());
				obj.put("region", i.getRegion());
				obj.put("fee", i.getFee());
				obj.put("status", i.getState()+"");
				obj.put("childName", i.getChild().getName());
				obj.put("childAge", i.getChild().getAge());
				obj.put("childClass", i.getChild().getClass1());
				obj.put("childSchool", i.getChild().getSchool());
				arr.add(obj);
			}
			return arr;
		}
		return null;	
	}
	
	public JSONArray viewMyDetails(String registerNo) {
		JSONArray  arr=new JSONArray();
		JSONObject obj=new JSONObject();
		Mother mom=null;
		String dob="";
		List<Child> childList=new ArrayList<Child>();
		try {
			PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo like ?");
			psmt1.setString(1, registerNo);
			ResultSet rs1=psmt1.executeQuery();
			if(rs1.next()) {
				mom=new Mother();
				mom.setRegNo(rs1.getString(1));
				mom.setName(rs1.getString(2));
				mom.setPhoneNo(rs1.getString(3));
				mom.setRegion(rs1.getString(4));
			}
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo like ?");
			psmt.setString(1, mom.getRegNo());
			ResultSet rs=psmt.executeQuery();
			while(rs.next()) {
				Child child=new Child();
				child.setName(rs.getString(2));
				child.setAge(Period.between(LocalDate.parse(rs.getString(4)), LocalDate.now()).getYears());
				dob=rs.getString(4);
				child.setClass(rs.getString(5));
				child.setSchool(rs.getString(3));
				childList.add(child);
			}
			mom.setChildList(childList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obj.put("registerNo", mom.getRegNo());
		obj.put("name", mom.getName());
		obj.put("number", mom.getPhoneNo());
		obj.put("region", mom.getRegion());
		arr.add(obj);
		for(Child i:mom.getChildList()) {
			obj=new JSONObject();
			obj.put("childName", i.getName());
			obj.put("childAge", i.getAge());
			obj.put("childClass", i.getClass1());
			obj.put("childSchool", i.getSchool());
			obj.put("dob", dob);
			arr.add(obj);
		}
		return arr;
	}
	public void rateDayCare(String registerNo,Comments comment) {
		Daycare daycare=null;
		Mother mom=new Mother();
		mom.setRegNo(registerNo);
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select dayCareId from Mother where regNo like ?");
			psmt.setString(1, mom.getRegNo());
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {
				daycare=new Daycare();
				daycare.setId(rs.getString(1));
				PreparedStatement pstmt1=DbConnection.dbConnection.prepareStatement("update Daycare set status='NOT_ENROLLED' where dayCareId=?");
				pstmt1.setString(1, daycare.getId());
				pstmt1.executeUpdate();
				psmt=DbConnection.dbConnection.prepareStatement("insert into Comments(dayCareId,comment,star,mother_regNo,time) values(?,?,?,?,?)");
				psmt.setString(1, daycare.getId());
				psmt.setString(2, comment.getComment());
				psmt.setInt(3, comment.getStar());
				psmt.setString(4, mom.getRegNo());
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				LocalDateTime currentTimeDate = LocalDateTime.now();
				comment.setDate(dtf.format(currentTimeDate));
				psmt.setString(5, comment.getDate());
				psmt.execute();
				PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("update Mother set dayCareId=? where regNo=?");
				pstmt.setNull(1, Types.VARCHAR);
				pstmt.setString(2, mom.getRegNo());
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	public JSONArray myDaycare(String registerNo) {
		JSONArray arr=new JSONArray();
		Mother mom=new Mother();
		Daycare daycare=null;
		mom.setRegNo(registerNo);
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select dayCareId from Mother where regNo like ?");
			psmt.setString(1, mom.getRegNo());
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {
				daycare=new Daycare();
				daycare.setId(rs.getString(1));
				PreparedStatement pstmt1=DbConnection.dbConnection.prepareStatement("select * from Daycare where dayCareId=?");
				pstmt1.setString(1, daycare.getId());
				ResultSet rs1=pstmt1.executeQuery();
				if(rs1.next()) {
					daycare.setName(rs1.getString(2));
					daycare.setPhoneNo(rs1.getString(3));
					daycare.setRegion(rs1.getString(4));
					daycare.setFee(rs1.getInt(5));
					pstmt1=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo=?");
					pstmt1.setString(1, daycare.getId());
					rs1=pstmt1.executeQuery();
					if(rs1.next()) {
						Child child=new Child();
						child.setName(rs1.getString(2));
						child.setAge(Period.between(LocalDate.parse(rs1.getString(4)), LocalDate.now()).getYears());
						child.setClass(rs1.getString(5));
						child.setSchool(rs1.getString(3));
						daycare.setChild(child);
					}
					JSONObject obj=new JSONObject();
					obj.put("name", daycare.getName());
					obj.put("number", daycare.getPhoneNo());
					obj.put("region", daycare.getRegion());
					obj.put("fee", daycare.getFee());
					System.out.println(obj.toJSONString());
					arr.add(obj);
					obj=new JSONObject();
					obj.put("childName", daycare.getChild().getName());
					obj.put("childAge", daycare.getChild().getAge());
					obj.put("childClass", daycare.getChild().getClass1());
					obj.put("childSchool", daycare.getChild().getSchool());
					arr.add(obj);
				}	
			}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return arr;
	}
	public JSONArray viewHistory(String userId) {
		JSONArray arr=new JSONArray();
		Mother mom=new Mother();
		mom.setRegNo(userId);
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select dayCareId,time from Relations where mother_regNo like ? order by time desc");
			psmt.setString(1, userId);
			ResultSet rs=psmt.executeQuery();
			while(rs.next()) {
				Daycare daycare=null;
				JSONObject obj=new JSONObject();
				PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select name,num,fee from Daycare where dayCareId like ?");
				psmt1.setString(1, rs.getString(1));
				ResultSet rs1=psmt1.executeQuery();
				if(rs1.next()) {
					daycare=new Daycare();
					daycare.setName(rs1.getString(1));
					daycare.setPhoneNo(rs1.getString(2));
					daycare.setFee(rs1.getInt(3));
				}
				obj.put("name", daycare.getName());
				obj.put("number", daycare.getPhoneNo());
				obj.put("fee", daycare.getFee());
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				obj.put("enrolledTime", sdf.format(rs.getDate(2)));
				arr.add(obj);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return arr;
	}
	public JSONArray getNotifications() {
		JSONArray arr=new JSONArray();
		try {
			PreparedStatement pstmt=DbConnection.dbConnection.prepareStatement("select * from Notification");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				JSONObject obj=new JSONObject();
				obj.put("motherRegno", rs.getString(2));
				obj.put("daycareId", rs.getString(3));
				SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy");
				obj.put("date", sdf2.format(rs.getDate(4)));
				obj.put("region", rs.getString(5));
				arr.add(obj);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return arr;
	}
}
