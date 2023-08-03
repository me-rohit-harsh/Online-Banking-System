package com.bank;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FetchDataofUser {

	public static void main(String[] args) {
		// Create a Hibernate SessionFactory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		
		// Open a new session
		Session session = factory.openSession();
		
		/*
		 * Some import will be needed when it will be uncommented 
		 * // Create CriteriaBuilder to build criteria queries CriteriaBuilder builder =
		 * session.getCriteriaBuilder();
		 * 
		 * // Create CriteriaQuery to define the query structure CriteriaQuery<Integer>
		 * criteriaQuery = builder.createQuery(Integer.class);
		 * 
		 * // Define the root entity (table) for the query Root<BankUser> root =
		 * criteriaQuery.from(BankUser.class);
		 * 
		 * // Define the columns to be selected
		 * criteriaQuery.multiselect(root.get("id"));
		 * 
		 * // Execute the query and retrieve the list of IDs List<Integer> idList =
		 * session.createQuery(criteriaQuery).getResultList();
		 * 
		 * // Print the retrieved IDs for (int id3 : idList) { System.out.println(id3);
		 * }
		 */
		
		Scanner sc = new Scanner(System.in);

		// Get information from a specific ID
		System.out.println("Enter the user ID to load the information:");
		int id = sc.nextInt();

		// Retrieve the user object from the database using the ID
		BankUser user = session.get(BankUser.class, id);

		// Combine the first name and last name of the user
		String name = user.getFname() + " " + user.getLname();
		System.out.println(name);

		/*
		 * // Get all the IDs from the BankUser table String hql =
		 * "SELECT id FROM BankUser"; Query query = session.createQuery(hql);
		 * List<Integer> idList1 = query.getResultList();
		 * 
		 * // Print the retrieved IDs for (int id3 : idList1) { System.out.println(id3);
		 * }
		 * 
		 * // Get the ID of the newly added data in the table String hql1 =
		 * "SELECT s.id FROM BankUser s ORDER BY s.id DESC"; Query query1 =
		 * session.createQuery(hql1); query1.setMaxResults(1); Integer id2 = (Integer)
		 * query1.uniqueResult(); System.out.print("Your user ID is: " + id2 + " ");
		 * 
		 * BankUser newUser = new BankUser(); // Set the properties of the new user
		 * object
		 * 
		 * // Save the new user to the table session.save(newUser);
		 * 
		 * // Access the ID of the newly added entity Integer id23 = newUser.getId();
		 * 
		 * System.out.println("Your user ID is: " + id23);
		 */
//		After doing the work from it we need to close all the things like session factory 
		// Finally, close the session
		session.close();

		// Close the Hibernate SessionFactory
		factory.close();
	}

}
