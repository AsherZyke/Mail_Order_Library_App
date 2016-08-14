package edu.ncsu.csc216.pack_library.loan_system;

/**
 * Interface for a lending system where the available items are stored in an inventory
 *   and where there are different users. Items can be reserved, checked out, 
 *   and returned to the inventory. Items in the the inventory, reserves,
 *   and checked-out items at home can be located by position.
 * 
 * @author Jo Perry
 *
 */
public interface LoanManager {
		
	/**
	 * Traverse all items in the inventory.
	 * @return the string representing the items in the inventory
	 */
	public String showInventory();
	
	/**
	 * Set the user for the current context to a given value.
	 * @param id user's id
	 * @param password user's password
	 * @throws IllegalStateException if a patron or the admin is already logged in
	 * @throws IllegalArgumentException if the patron account does not exist
	 */
	public void login(String id, String password);
	
	/**
	 * Logs the current user out of the system.
	 */
	public void logout();
	
	/**
	 * Add a new account to the patron database. The administrator must be logged in.
	 * @param id new patron's id
	 * @param password new patron's password
	 * @param num number/max limit associated with this patron
	 * @throws IllegalStateException if the database is full or the administrator is not logged in
	 * @throws IllegalArgumentException if patron cannot be added to the patron database
	 */
	public void addNewPatron(String id, String password, int num);
	
	/**
	 * Returns the current user's id.  If there is no user logged in, an
	 * empty string is returned.
	 * @return the current user's id.
	 */
	public String getCurrentUserId();
	
	/**
	 * Cancel a patron account. 
	 * @param id patron's id
	 * @throws IllegalStateException if the administrator is not logged in
	 * @throws IllegalArgumentException if patron cannot be removed due to some error
	 */
	public void cancelAccount(String id);
	
	/**
	 * Reserve the selected item for the reserve queue. 
	 * @param position position of the selected item in the inventory
	 * @throws IllegalStateException if no patron is logged in
	 * @throws IndexOutOfBoundsException if position is out of bounds
	 */
	public void reserveItem(int position);

	/**
	 * Move the item in the given position up 1 in the reserve queue. 
	 * @param position current position of item to move up one
	 * @throws IllegalStateException if no patron is logged in
	 * @throws IndexOutOfBoundsException if position is out of bounds
	 */
	public void reserveMoveAheadOne(int position);

	/**
	 * Remove the item in the given position from the reserve queue.
	 * @param position position of the item in the queue
	 * @throws IllegalStateException if no patron is logged in
	 * @throws IndexOutOfBoundsException if position is out of bounds
	 */
	public void removeSelectedFromReserves(int position);

	/**
	 * Traverse all items in the reserve queue.
	 * @return string representation of items in the queue
	 * @throws IllegalStateException if no patron is logged in
	 */
	public String traverseReserveQueue() ;
	
	/**
	 * Traverse all items in the list of items checked out.
	 * @return string representation of checked out items
	 * @throws IllegalStateException if no patron is logged in
	 */
	public String traverseCheckedOut() ;

	/**
	 * Return the selected item to the inventory.
	 * @param position location in the list of items checked out of the item to return
	 * @throws IllegalStateException if no patron is logged in
	 * @throws IndexOutOfBoundsException if position is out of bounds
	 */
	public void returnItem(int position);
	
}
