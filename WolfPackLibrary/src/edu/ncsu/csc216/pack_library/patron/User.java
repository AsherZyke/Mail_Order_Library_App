/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import edu.ncsu.csc216.pack_library.util.Constants;

/**
 * An abstract class that defines the general 
 * behavior for users of a given system that
 * requires usernames and passwords to login.
 * 
 * @author Benjamin Zich
 *
 */
public abstract class User {
	/** The user's username. */
	private String id;
	/** The user's password stored as the password's hashcode. */
	private int password;
	
	/**
	 * Creates the user object and validates the user id and password.
	 * 
	 * @param id the user's username.
	 * @param password the user's password.
	 */
	public User(String id, String password) {
		if (id == null || password == null) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_NULL);
		}
		if (id.equals("") || password.equals("")) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
		}
		String trimmedID = id.trim();
		String trimmedPassword = password.trim();
		if (trimmedID.equals("") || trimmedPassword.equals("")) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_EMPTY);
		}
		if (containsWhitespace(trimmedID) || containsWhitespace(trimmedPassword)) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
		}
		for(int i = 0; i < trimmedPassword.length(); i++) {
			if (!Character.isLetterOrDigit(trimmedPassword.charAt(i))) {
				throw new IllegalArgumentException(Constants.EXP_PATRON_WHITESPACE);
			}
		}
		this.id = trimmedID;
		this.password = trimmedPassword.hashCode();

	}
	
	/**
	 * Verifies the user's password by hashing the string and
	 * comparing the hash against a stored hashed password.
	 * 
	 * @param password the password to verify
	 * @return true if the hashed password matches the stored
	 * hash.
	 */
	public boolean verifyPassword(String password) {
		return this.password == password.hashCode();
	}
	
	/**
	 * Gets the user's id.
	 * 
	 * @return the user's id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Checks a username and password for invalid whitespace or 
	 * non-alphanumeric characters inside the String.
	 * 
	 * @param x is the String to be checked for whitespace
	 * @return true if there is whitespace in the String, false
	 * if there is no whitespace
	 */
	private boolean containsWhitespace(String x) {
		for(int i = 0; i < x.length(); i++) {
			if (x.charAt(i) == ' ') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A case-insensitive comparison of two users by id.
	 * 
	 * @param x the User to be compared to this object
	 * @return true if the user ids are the same, false
	 * if not.
	 */
	public int compareTo(User x) {
		if (x == null) {
			throw new IllegalArgumentException(Constants.EXP_CANNOT_COMPARE);
		}
		return id.compareToIgnoreCase(x.getId());
	}

}
