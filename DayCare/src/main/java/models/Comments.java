package models;

public class Comments {
	String comment;
	int star;
	String date;
	Mother givenBy;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Mother getGivenBy() {
		return givenBy;
	}
	public void setGivenBy(Mother givenBy) {
		this.givenBy = givenBy;
	}
}
