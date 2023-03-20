package models;

public class Child {
	String name,school;
	int age;
	String class1;
//	public Child(String name, String school, String age, String class1) {
//		this.name = name;
//		this.school = school;
//		this.age = age;
//		this.class1 = class1;
//	}
//	public Child() {
//		// TODO Auto-generated constructor stub
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int i) {
		this.age = i;
	}
	public String getClass1() {
		return class1;
	}
	public void setClass(String class1) {
		this.class1 = class1;
	}
}
