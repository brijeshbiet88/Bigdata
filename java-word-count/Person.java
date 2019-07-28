package com.dataframe.demo;

import java.io.Serializable;

public class Person {
	
	  public Person() {
		super();
	}
	public Person(long age, String city, String hobby, String name) {
		super();
		this.age = age;
		this.city = city;
		this.hobby = hobby;
		this.name = name;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private long age;
	  private String city;
	  private String hobby;
	  private String name;
	  
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	}