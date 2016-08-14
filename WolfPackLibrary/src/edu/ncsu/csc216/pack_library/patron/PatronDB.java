/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import edu.ncsu.csc216.pack_library.util.Constants;

/**
 * Defines the behavior for the PatronDB class.
 * The PatronDB object stores and manages the
 * information of patrons of the loan program.
 * 
 * @author Benjamin Zich
 *
 */
public class PatronDB {
	/** The maximum number of patrons the system can support. */
	public static final int MAX_SIZE = 20;
	/** The number of patrons currently in the system. */
	private int size;
	/**The list of patrons currently in the system. */
	private Patron[] list;
	
	/**
	 * Creates an empty PatronDB object.
	 */
	public PatronDB() {
		list = new Patron[MAX_SIZE];
	}
	
	/** Returns the patron in the list whose id matches the first
	 * parameter and password matches the second.
	 * 
	 * @param id the id of the patron to be verified
	 * @param password the password of the patron to verified
	 * @return the patron the matches the id and password
	 */
	public Patron verifyPatron(String id, String password) {
		if (id == null || password == null) {
			throw new IllegalArgumentException(Constants.EXP_INCORRECT);
		} 
		Patron matchingPatron = null;
		for (int i = 0; i < size; i++) {
			if (list[i].getId().equalsIgnoreCase(id)) {
				if (!list[i].verifyPassword(password)) {
					throw new IllegalArgumentException(Constants.EXP_INCORRECT);
				} else {
					matchingPatron = list[i];
				}
			}
		}
		if (matchingPatron == null) {
			throw new IllegalArgumentException(Constants.EXP_INCORRECT);
		}
		return matchingPatron;
	}
	
	/**
	 * Used for testing. Returns a string of ids of patrons in the list
	 * in list order. Successive ids are separated by newlines, including
	 * trailing newline.
	 * 
	 * @return the string of ids of patrons in the list in list order.
	 */
	public String listAccounts() {
		String ids = "";
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				ids = ids + list[i].getId() + "\n";
			}
		}
		return ids;
	}
	
	/**
	 * Adds a new patron to the list.
	 * 
	 * @param id the id of the new patron
	 * @param password the password of the new patron
	 * @param maxCheckedOut the maximum number of books the new patron
	 * can check out
	 */
	public void addNewPatron(String id, String password, int maxCheckedOut) {
		if (size >= MAX_SIZE) {
			throw new IllegalStateException(Constants.EXP_PATRON_DB_FULL);
		}
		if (!isNewPatron(id)) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_DB_ACCOUNT_EXISTS);
		}
		insert(new Patron(id, password, maxCheckedOut));
		
	}
	
	/**
	 * Removes an account from the list and returns any books
	 * that patron has checked out to the inventory.
	 * 
	 * @param id the id of the account to be canceled.
	 */
	public void cancelAccount(String id) {
		int index = findMatchingAccount(id);
		if (index == -1) {
			throw new IllegalArgumentException(Constants.EXP_INCORRECT);
		}
		list[index].closeAccount();
		for(int i = index; i < size; i++) {
			if(i == MAX_SIZE - 1) {
				list[i] = null;
			} else {
				list[i] = list[i + 1];
			}
		}
		size--;
	}
	
	/**
	 * Determines if a patron id is unique.
	 * 
	 * @param id to be compared to existing patrons
	 * @return true if the patron id is unique
	 */
	private boolean isNewPatron(String id) {
		if (size != 0) {
			for(int i = 0; i < size; i++) {
				if (list[i].getId().equals(id)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Inserts Patrons into list in alphabetical order.
	 * 
	 * @param patron is the patron to be inserted.
	 */
	private void insert(Patron patron) {
		size++;
		int count = 0;
		Patron[] newList = new Patron[MAX_SIZE];
		
		for (int i = 0; i < size; i++) {
			if (count != size - 1 && (count != i || list[count].compareTo(patron) < 0)) {
				newList[i] = list[count];
				count++;
			} else {
				newList[i] = patron;
			}
		}
		list = newList;
	}
	
	/**
	 * Finds a matching account id or password in the list of patrons and
	 * returns the index of the account in the list that has a matching
	 * id or password.
	 * 
	 * @param id is the id of the patron to find
	 * @return the index of the matching patron, -1 if there is no match
	 */
	private int findMatchingAccount(String id) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (list[i].getId().equals(id)) {
				index = i;
			}
		}
		return index;
	}

}
