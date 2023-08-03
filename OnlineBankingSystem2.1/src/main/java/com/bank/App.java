package com.bank;

//Import statements...
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Online Banking using Java, OOPs Concepts SQL and Hibernate.
 **/

//1. Create a method to add new user ✔️
//2. Create a method for DOB validation and rest info of user ✔️
//3. Create a method for registered user ✔️
//4. Create a method for main page ✔️
//5. Create a method to delete user ✔️
//6. Create a method to deposit money ✔️
//7. Create a method for viewing balance ✔️
//8. Create a method to check for registered user ✔️
//9. Create method to send money ✔️

//Main class
public class App {

	public static void main(String[] args) throws SQLException {
		// Configuration and setup for Hibernate
		// ***Create a Hibernate SessionFactory***
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		// open a session
		Session session = factory.openSession();
		// Sending user to landing page
		landing_page(factory, session);
		// Thanks giving to the user for choosing
		System.out.println("********** Thank You For Choosing Us :) ***********");
		// Close the Hibernate Session and SessionFactory
		session.close();
		factory.close();

	}

	// Method for landing page for user
	public static void landing_page(SessionFactory factory, Session session) throws SQLException {
		System.out.println("*****WELCOME TO INDIAN BANKING*****");
		try (Scanner sc = new Scanner(System.in)) {
			// Main logic for user action handling based on input choice
			System.out.println("a) New User \nb) Registered User \ne) Exit");
			char type = Character.toLowerCase(sc.next().charAt(0));
			switch (type) {
			case 'a':
				System.out.println("**********Welcome**********");
				Add_user(factory, session);
				break; // Return here to exit the loop after Add_user() is done
			case 'b':
				Registred_user(factory, session);
				break; // Return here to exit the loop after Registred_user() is done
			case 'e':
				return; // Exit the method and program here
			default:
				System.out.println("Choose a correct option to proceed");
				System.out.println("**Session Expired**");
				// Continue the loop to prompt the user again
			}

		}
	}

	// Method to add a new user to the database
	public static void Add_user(SessionFactory factory, Session session) throws SQLException {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter your First Name : ");
			String Fname = sc.nextLine();
			System.out.print("Enter your Last Name : ");
			String Lname = sc.nextLine();
			DOB_val(factory, session, Fname, Lname);
		}
	}

	// Gather user information and validate the date of birth (DOB) entered by the  user
	public static void DOB_val(SessionFactory factory, Session session, String Fname, String Lname)
			throws SQLException {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Enter your DOB (dd/mm/yyyy): ");
			String DOB = sc.nextLine();
			String[] parts = DOB.split("/");
			int day, month, year;
			try {
				day = Integer.parseInt(parts[0]);
				month = Integer.parseInt(parts[1]);
				year = Integer.parseInt(parts[2]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid input format.");
				DOB_val(factory, session, Fname, Lname);
				return;
			}
			if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0) {
				System.out.println("Error:Invalid DOB. Please re-enter the DOB in the format ");
				DOB_val(factory, session, Fname, Lname);
			}
			if (month == 2) {
				if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
					if (day < 1 || day > 29) {
						System.out.println("Error:Invalid DOB. Please re-enter the DOB in the format ");
						DOB_val(factory, session, Fname, Lname);
					}
				} else {
					if (day < 1 || day > 28) {
						System.out.println("Error:Invalid DOB. Please re-enter the DOB in the format ");
						DOB_val(factory, session, Fname, Lname);
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day < 1 || day > 30) {
					System.out.println("Error:Invalid DOB. Please re-enter the DOB in the format ");
					DOB_val(factory, session, Fname, Lname);
				}
			}
			Year currentYear = Year.now();
			if (currentYear.getValue() == year || currentYear.getValue() > year) {
				if (currentYear.getValue() - year == 10 || currentYear.getValue() - year > 10) {
					System.out.print("Your Address : ");
					String Address = sc.nextLine();
					System.out.print("Create a password : ");
					String MyPass = sc.next();
					// Creating object of BankUser
					BankUser user = new BankUser();
					user.setFname(Fname);
					user.setLname(Lname);
					user.setAddress(Address);
					user.setDOB(DOB);
					user.setDOJ(new Date());
					user.setPassword(MyPass);

					// Begin a transaction
					Transaction tx = session.beginTransaction();

					// Save the objects to the database
					session.save(user);

					// Commit the transaction
					tx.commit();

					// Access the ID of the newly added entity
					Integer newId = user.getId();

					System.out.println(
							"Your user id is: " + newId + " You can use this id to log in back to your account");

//					Redirect To the registered user page 		
					Registred_user(factory, session);
				} else {
					System.out.println("You must be at least 10 years old.");
					return;
				}
			} else {
				System.out.println("Invalid year: year of birth can't be in the future.");
				DOB_val(factory, session, Fname, Lname);
			}
		}

	}

	// Method to handle registered user login and options
	public static void Registred_user(SessionFactory factory, Session session) throws SQLException {
		System.out.print("Enter you user id : ");
		try (Scanner sc = new Scanner(System.in)) {
			int reg_id = sc.nextInt();
			int count = 0;
			if (Check_user(factory, session, reg_id)) {
				count++;
//				Get the first name using hibernate 
				BankUser user = session.get(BankUser.class, reg_id);
				System.out.println("**********Welcome Back " + user.getFname() + "**********");
				System.out.print("Enter the Password :");
				String pass = sc.next();
				if (pass.equals(user.getPassword())) {

//					Begin the transaction 
					Transaction tx = session.beginTransaction();
					BankUser user1 = session.get(BankUser.class, reg_id);

					// Create a SimpleDateFormat object with the desired format
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					// Get the current timestamp
					Date currentDate = new Date();

//					Update the Timestamp
					user1.setLDA(dateFormat.format(currentDate));

					// Save the objects to the database
					session.save(user1);

					// Commit the transaction
					tx.commit();
					Main_page( factory, session,reg_id);
				} else {
					System.out.println("Incorrect password please try again");
				}
			}
			if (count == 0)

			{
				System.out.println("You are not registered please register your self");
				Add_user(factory, session);
			}
		}

	}

	// Method to check if a user exists based on their ID
	public static boolean Check_user(SessionFactory factory, Session session, int reg_id) throws SQLException {

		// Get the user with the given ID
		BankUser user = session.get(BankUser.class, reg_id);
		if (user != null) {
			return true;
		}
		return false;

	}

	// Method to handle the main page and user options
	public static void Main_page( SessionFactory factory, Session session,int reg_id) throws SQLException {
		System.out.println("Options:\n" + "a) Cash Deposit\n" + "b) View Balance\n" + "c) Withdraw Money\n"
				+ "s) Send Money\n" + "f) Update Name\n" + "g) Change Password\n" + "h) Delete Account\n" + "e) Exit");
		try (Scanner sc = new Scanner(System.in)) {
			char Update = Character.toLowerCase(sc.next().charAt(0));
			switch (Update) {
			case 's':
				Send_money(factory, session,reg_id);
				break;
			case 'a':
//				Get the total balance before adding 
				Double Total = Available_Bal( factory, session,reg_id);
				System.out.print("Add money to add in your account : ");
				Double AddBal = sc.nextDouble();
				if (AddBal > 0) {
					Total = Total + AddBal;
					Deposite_Money( factory, session,reg_id, Total);
					System.out.println("Total balance is :" + Total);
					Main_page( factory, session,reg_id);
				} else {
					System.out.println("Money can not be in negative");
					Main_page( factory, session,reg_id);
				}
				break;
			case 'b':
				System.out.println("Availabe Balance is : " + Available_Bal(factory, session,reg_id));
				Main_page( factory, session,reg_id);
				break;
			case 'c':
				System.out.print("Enter the amount : ");
				Double amount = sc.nextDouble();
				Withdwarl(factory, session, reg_id, amount);
				Main_page( factory, session,reg_id);
				break;
			case 'f':

//				Begin the transaction 
				Transaction tx = session.beginTransaction();

//				Get the user from its id 
				BankUser user = session.get(BankUser.class, reg_id);
				System.out.print("Enter your First Name : ");
				String Fname = sc.next();
				System.out.print("Enter your Last Name :");
				String Lname = sc.next();

//				Update the First name & last name 
				user.setFname(Fname);
				user.setLname(Lname);

				// Save the objects to the database
				session.saveOrUpdate(user);

				// Commit the transaction
				tx.commit();

				System.out.println("******Succesful******");

				Main_page( factory, session,reg_id);
				break;
			case 'g':
//				Open a session 
				Session session1 = factory.openSession();
//				Begin the transaction 
				Transaction tx1 = session1.beginTransaction();
//				Get the user from its id 
				BankUser user1 = session1.get(BankUser.class, reg_id);
//				Update the password 
				System.out.print("Enter your new password : ");
				String pass = sc.next();
				user1.setPassword(pass);
				// Save the objects to the database
				session1.saveOrUpdate(user1);

				// Commit the transaction
				tx1.commit();

				// Finally, close the session
				session1.close();

				System.out.println("******Succesful******");
				Main_page( factory, session,reg_id);
				break;
			case 'h':
				System.out.println("Are you sure to delete your bank account");
				System.out.print("Y/N\nTell us your desicion : ");
				char des = Character.toLowerCase(sc.next().charAt(0));
				if (des == 'y') {
					delete_ac( factory, session,reg_id);
				} else if (des == 'n') {
					System.out.println("We glad to have you :)");
					Main_page( factory, session,reg_id);
				} else {
					System.out.println("Choose a correct option to proceed");
					Main_page( factory, session,reg_id);
				}
				break;
			case 'e':
				return;
			default:
				System.out.println("Choose a correct option to proceed");
				Main_page( factory, session,reg_id);
			}
		}

	}

	// Method to delete a user account
	public static void delete_ac( SessionFactory factory, Session session,int reg_id) {

		Transaction transaction = session.beginTransaction();
		BankUser user = session.get(BankUser.class, reg_id);
		session.delete(user);
		transaction.commit();
		System.out.println("Account deleted successfully.");

	}

	// Method to check the available balance of a user's account
	public static double Available_Bal( SessionFactory factory, Session session,int reg_id) throws SQLException {

//		Get the user from its id 
		BankUser user = session.get(BankUser.class, reg_id);
//		Storing the balance before closing the session 
		Double userBalance = user.getBalance();
//		Return the total balance 
		return userBalance;

	}

	// Method to deposit money into a user's account
	public static void Deposite_Money( SessionFactory factory, Session session,int rec_id, Double rec_money)
			throws SQLException {
		// Begin the transaction
		Transaction tx = session.beginTransaction();
		// Get the receiver user from its ID
		BankUser receiver = session.get(BankUser.class, rec_id);
		// Update the balance
		receiver.setBalance(rec_money);

		// Save the object to the database
		session.saveOrUpdate(receiver);

		// Commit the transaction
		tx.commit();

		System.out.println("****** Successful ******");
	}

	// Method to withdraw money from a user's account
	public static void Withdwarl(SessionFactory factory, Session session, int reg_id, Double amount)
			throws SQLException {
		if (amount >= 0) {
			Double Ava_bal = Available_Bal( factory, session,reg_id);
			if (amount <= Ava_bal) {
				Double updatedBal = Ava_bal - amount;
//	            Begin the transaction 
				Transaction tx = session.beginTransaction();
				BankUser user = session.get(BankUser.class, reg_id);
				user.setBalance(updatedBal);
//	            Save the object to the database
				session.saveOrUpdate(user);
//	            Commit the transaction
				tx.commit();
				System.out.println("Available balance is: " + updatedBal);
			} else {
				System.out.println("Insufficient Balance!!");
				Main_page(factory, session,reg_id);
			}

		} else {
			System.out.println("Invalid input!!");
			Main_page( factory, session,reg_id);
		}

	}

	// Method to send money from one user's account to another
	public static void Send_money( SessionFactory factory, Session session,int reg_id) throws SQLException {
		System.out.print("Enter the recipient's ID : ");
		try (Scanner sc = new Scanner(System.in)) {
			int rec_id = sc.nextInt();
			if (rec_id == reg_id) {
				System.out.println("You cannot transfer money to your own account.");
				Send_money( factory, session,reg_id);
			} else {

				// Get the user from its ID
				BankUser user = session.get(BankUser.class, reg_id);
//				if user is not pointing null then only allow to send money 
				if (user != null) {
					// To add the money in the receiver bank account
					BankUser receiver = session.get(BankUser.class, rec_id);
					if (receiver != null) {
						System.out.print("Enter the amount to send : ");
						Double money = sc.nextDouble();
						Withdwarl(factory, session, reg_id, money);
						System.out.println("Withdrwal is done now processing for reciever.....");
						Double rec_money = receiver.getBalance();
						rec_money = rec_money + money;
						Deposite_Money(factory, session,rec_id, rec_money);
						session.saveOrUpdate(receiver);
						Main_page(factory, session,reg_id);
					} else {
						System.out.println("Recipient ID does not exist. Please check and try again!");
						Main_page(factory, session,reg_id);
					}
				} else {
					System.out.println("Invalid ID. Please check and try again!");
					Main_page(factory, session,reg_id);
				}
			}
		}

	}

}
