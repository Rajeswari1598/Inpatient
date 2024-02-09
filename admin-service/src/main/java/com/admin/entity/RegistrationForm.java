package com.admin.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "registration")
public class RegistrationForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="firstName")
	private String firstName;
	@Column(name="lastName")
	private String lastName;
	@Column(name="birthDay")
	private Date birthDay;
	@Column(name="email")
	private String email;
	@Column(name="gender")
	private String gender;
	@Column(name="phoneNumber")
	private long phoneNumber;
	@Column(name="serviceType")
	private String serviceType;
	
	public RegistrationForm() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	public RegistrationForm(int id, String firstName, String lastName, Date birthDay, String email, String gender,
			long phoneNumber, String serviceType) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.email = email;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.serviceType = serviceType;
	}





	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	@Override
	public String toString() {
		return "RegistrationForm [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDay="
				+ birthDay + ", email=" + email + ", gender=" + gender + ", phoneNumber=" + phoneNumber
				+ ", serviceType=" + serviceType + "]";
	}

	
}