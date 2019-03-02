package com.cassandradb.demo;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String registrationNumber;
	private String branch;
	private Date dateOfBirth;
	//private Timestamp post_Date;
	

	//public Timestamp getPost_Date() {
	//	return new Timestamp(dateOfBirth.getTime());
	//}

	//public void setPost_Date(Timestamp post_Date) {
	//	this.post_Date = new Timestamp(dateOfBirth.getTime());
	//}

	public Student(String firstName, String registrationNumber, String branch, java.util.Date dateOfBirth) {
		super();
		this.firstName = firstName;
		this.registrationNumber = registrationNumber;
		this.branch = branch;
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}

	public java.util.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.util.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
