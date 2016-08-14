/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import edu.ncsu.csc216.pack_library.util.Constants;

/**
 * Defines the behavior of the AccountSystem class.
 * The account system manages the various accounts of patrons
 * and of the admin.
 * 
 * @author Benjamin Zich
 *
 */
public class AccountSystem implements AccountManager {
	/** Database of library patrons. */
	private PatronDB patronList;
	/** The single administrator for the system. */
	private static Admin adminUser;
	/** The patron currently logged into the system. */
	private Patron currentPatron;
	/** True if and only if the administrator is logged into the system. */
	private boolean adminLoggedIn;
	/** True if and only if a patron is logged into the system.*/
	private boolean patronLoggedIn;
	
	/**
	 * Constructs an empty AccountSystem object.
	 */
	public AccountSystem() {
		adminUser = new Admin();
		adminLoggedIn = false;
		patronLoggedIn = false;
		currentPatron = null;
		patronList = new PatronDB();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#login(java.lang.String, java.lang.String)
	 */
	@Override
	public void login(String id, String password) {
		if (adminLoggedIn || patronLoggedIn) {
			throw new IllegalStateException(Constants.EXP_LAS_USER_ALREADY_LOGGED_IN);
		}
		if (adminUser.getId().equals(id) && adminUser.verifyPassword(password)) {
			adminLoggedIn = true;
		} else if (patronList.verifyPatron(id, password) != null) {
			currentPatron = patronList.verifyPatron(id, password);
			patronLoggedIn = true;
		}

	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#logout()
	 */
	@Override
	public void logout() {
		currentPatron = null;
		patronLoggedIn = false;
		adminLoggedIn = false;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#getCurrentPatron()
	 */
	@Override
	public Patron getCurrentPatron() {
		return currentPatron;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#isAdminLoggedIn()
	 */
	@Override
	public boolean isAdminLoggedIn() {
		return adminLoggedIn;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#isPatronLoggedIn()
	 */
	@Override
	public boolean isPatronLoggedIn() {
		return patronLoggedIn;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#addNewPatron(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void addNewPatron(String id, String password, int num) {
		if (!isAdminLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		patronList.addNewPatron(id, password, num);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#cancelAccount(java.lang.String)
	 */
	@Override
	public void cancelAccount(String id) {
		if (!isAdminLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		patronList.cancelAccount(id);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.patron.AccountManager#listAcounts()
	 */
	@Override
	public String listAcounts() {
		return patronList.listAccounts();
	}
	
	
	/**
	 * Defines the behavior for the Admin class.
	 * The admin is able to create and delete patrons.
	 * 
	 * @author Benjamin Zich
	 *
	 */
	public class Admin extends User {
		
		/**
		 * Constructs the admin object with it's default 
		 * username and password.
		 */
		public Admin() {
			super(Constants.ADMIN, Constants.ADMIN);
		}
	}

}
