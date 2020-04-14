package com.martin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Database {
	private static SessionFactory sessionFactory;

	/**
	 * Sets up the connection to the databaseand intialize the
	 * <i>SessionFactory</i>
	 */
	private static void setUp() {
		try {
			// Initialize the session factory
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (Throwable t) {
			System.err.println("Unable to initialize the database connection.");
			throw new ExceptionInInitializerError(t);
		}
	}

	/**
	 * @return a session opened with the <i>SessionFactory</i>
	 */
	public static Session getSession() {
		// If the SessionFactory isn't initialized, initialize it
		if (sessionFactory == null)
			setUp();
		// Then return a new session
		return sessionFactory.openSession();
	}

	/**
	 * This method saves (or persists) the object in parameter.
	 * 
	 * @param obj    the object to persist.
	 * @param commit should be true if you want to commit at once
	 */
	public static Transaction saveAndCommit(Object obj, boolean commit) {
		// Create a session and a transaction
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		session.save(obj);
		if (commit)
			tx.commit();
		return tx;
	}
}