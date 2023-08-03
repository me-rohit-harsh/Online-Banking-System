package com.bank;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "UserData")
public class BankUser {
	@Id
	@Column(name = "user_id")
	@GeneratedValue
	private int id; // User ID

	@Column(name = "First_name")
	private String Fname; // User first name

	@Column(name = "Last_name")
	private String Lname; // User last name

//	@Column(name="Password")
	private String Password; // User password

//	@Column(name="Balance")
	private double Balance; // User balance

	@Column(name = "Date_of_birth")
	private String DOB; // User date of birth

	@Column(name = "Full_Address")
	private String Address; // User full address

	@Column(name = "Date_of_opening")
	@Temporal(TemporalType.DATE)
	private Date DOJ; // User date of opening the account

	@Column(name = "Last_session")
	private String LDA; // User last session timestamp

	public BankUser(int id, String fname, String lname, String password, double balance, String dOB, String address,
			Date dOJ, String lDA) {
		super();
		this.id = id;
		Fname = fname;
		Lname = lname;
		Password = password;
		Balance = balance;
		DOB = dOB;
		Address = address;
		DOJ = dOJ;
		LDA = lDA;
	}

	/**
	 * Default constructor
	 */
	public BankUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Date getDOJ() {
		return DOJ;
	}

	public void setDOJ(Date date) {
		DOJ = date;
	}

	public String getLDA() {
		return LDA;
	}

	public void setLDA(String timestamp) {
		LDA = timestamp;
	}

	@Override
	public String toString() {
		return "BankUser [id=" + id + ", Fname=" + Fname + ", Lname=" + Lname + ", Password=" + Password + ", Balance="
				+ Balance + ", DOB=" + DOB + ", Address=" + Address + ", DOJ=" + DOJ + ", LDA=" + LDA + "]";
	}

}
