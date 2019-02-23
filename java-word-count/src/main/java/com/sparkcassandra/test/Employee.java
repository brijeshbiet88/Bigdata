package com.sparkcassandra.test;

import java.io.Serializable;

public class Employee implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3800099928225655034L;
	private String first_name;
	private String last_name;
	private int age;
	private String company;
	private String tech;
	private String doj;
	
	public Employee(String first_name, String last_name, int age, String company, String tech, String doj) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.company = company;
		this.tech = tech;
		this.doj = doj;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	
	public String toString() {
		return first_name+" "+last_name+" "+company+" "+age+" "+company+" "+tech+" "+doj;
		
	}
	
}
