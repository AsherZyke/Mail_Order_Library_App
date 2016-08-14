/**
 * 
 */
package edu.ncsu.csc216.pack_library.loan_system;

import edu.ncsu.csc216.pack_library.catalog.BookDB;
import edu.ncsu.csc216.pack_library.patron.AccountManager;
import edu.ncsu.csc216.pack_library.patron.AccountSystem;
import edu.ncsu.csc216.pack_library.util.Constants;

/**
 * Defines the behavior for the LoanSystem class.
 * The LoanSystem object manages the back end of the 
 * book loaning program.
 * 
 * @author Benjamin Zich
 *
 */
public class LoanSystem implements LoanManager {
	/** The patron account part of the system. */
	private AccountManager accounts;
	/** The database of books in the system. */
	private BookDB bookCatalog;
	
	/**
	 * Constructs the LoanSystem object.
	 * 
	 * @param fileName is the filename to read all of the books
	 * into the system from.
	 * 
	 */
	public LoanSystem(String fileName) {
		accounts = new AccountSystem();
		bookCatalog = new BookDB(fileName);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#showInventory()
	 */
	@Override
	public String showInventory() {
		return bookCatalog.traverse();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#login(java.lang.String, java.lang.String)
	 */
	@Override
	public void login(String id, String password) {
		accounts.login(id, password);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#logout()
	 */
	@Override
	public void logout() {
		accounts.logout();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#addNewPatron(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void addNewPatron(String id, String password, int num) {
		if (!accounts.isAdminLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		accounts.addNewPatron(id, password, num);

	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#getCurrentUserId()
	 */
	@Override
	public String getCurrentUserId() {
		if (accounts.isAdminLoggedIn()) {
			return Constants.ADMIN;
		} else if (accounts.isPatronLoggedIn()) {
			return accounts.getCurrentPatron().getId();
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#cancelAccount(java.lang.String)
	 */
	@Override
	public void cancelAccount(String id) {
		if (!accounts.isAdminLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		accounts.cancelAccount(id);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#reserveItem(int)
	 */
	@Override
	public void reserveItem(int position) {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
		}
		accounts.getCurrentPatron().reserve(bookCatalog.findItemAt(position));
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#reserveMoveAheadOne(int)
	 */
	@Override
	public void reserveMoveAheadOne(int position) {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
		}
		accounts.getCurrentPatron().moveAheadOneInReserves(position);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#removeSelectedFromReserves(int)
	 */
	@Override
	public void removeSelectedFromReserves(int position) {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
		}
		accounts.getCurrentPatron().unReserve(position);
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#traverseReserveQueue()
	 */
	@Override
	public String traverseReserveQueue() {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		return accounts.getCurrentPatron().traverseReserveQueue();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#traverseCheckedOut()
	 */
	@Override
	public String traverseCheckedOut() {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_ACCESS_DENIED);
		}
		return accounts.getCurrentPatron().traverseCheckedOut();
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.csc216.pack_library.loan_system.LoanManager#returnItem(int)
	 */
	@Override
	public void returnItem(int position) {
		if (!accounts.isPatronLoggedIn()) {
			throw new IllegalStateException(Constants.EXP_LLS_PATRON_NOT_LOGGED_IN);
		}
		accounts.getCurrentPatron().returnBook(position);
	}

}
