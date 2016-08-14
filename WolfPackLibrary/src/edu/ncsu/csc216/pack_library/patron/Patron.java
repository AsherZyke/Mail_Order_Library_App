/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import edu.ncsu.csc216.pack_library.catalog.Book;
import edu.ncsu.csc216.pack_library.util.Constants;
import edu.ncsu.csc216.pack_library.util.UniversalList;

/**
 * Defines the behavior for the Patron class. 
 * A patron is able to check out books and 
 * 
 * @author Benjamin Zich
 *
 */
public class Patron extends User {
	/**Maximum number of books a patron can check out.*/
	private int maxCheckedOut;
	/**Number of books the patron has checked out currently.*/
	private int nowCheckedOut;
	/**Books the patron currently has checked out. Elements are added to the end of the list. */
	private UniversalList<Book> checkedOut;
	/**Books in the patron's reserve queue in the order in which they were reserved. Elements
	 * are added to the end of the list.
	 */
	private UniversalList<Book> reserveQueue;
	
	/**
	 * Constructs the patron object.
	 * 
	 * @param id the patron's id
	 * @param password the patron's password
	 * @param maxCheckedOut the patron's maximum number of books the patron can 
	 * check out at one time.
	 */
	public Patron(String id, String password, int maxCheckedOut) {
		super(id, password);
		if (id.equals(Constants.ADMIN)) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_ADMIN);
		}
		if (maxCheckedOut < 1) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_MAX);
		}
		
		this.maxCheckedOut = maxCheckedOut;
		checkedOut = new UniversalList<Book>();
		reserveQueue = new UniversalList<Book>();
		nowCheckedOut = 0;
	}
	
	/**
	 * Returns a string of books in the reserve queue in the order
	 * in which they were reserved. Books are shown by info
	 * and successive books are separated by newlines, including
	 * a trailing newline.
	 * 
	 * @return the list of books in the reserve queue in the order
	 * in which they were reserved.
	 */
	public String traverseReserveQueue() {
		return traverseQueue(reserveQueue);
	}
	
	/**
	 * Gets a string of checked out books in the order in which they were checked
	 * out. Books are shown by info and successive books are separated by newlines,
	 * including a trailing newline.
	 * 
	 * @return the string of checked out books in the order in which they
	 * were checked out.
	 */
	public String traverseCheckedOut() {
		return traverseQueue(checkedOut);
	}
	
	/**
	 * Closes this account and returns all checked out books to the library.
	 */
	public void closeAccount() {
		while (reserveQueue.hasNext()) {
			unReserve(0);
		}
		while (checkedOut.hasNext()) {
			returnBook(0);
		}
	}
	
	/**
	 * Removes the book in the given position from the 
	 * checked out list and returns it to the library inventory.
	 * 
	 * @param index the position of the book in the list of checked
	 * out books to return 
	 */
	public void returnBook(int index) {
		if (index < 0 || index >= checkedOut.size()) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		checkedOut.remove(index).backToInventory();
		nowCheckedOut--;
		checkOut();
	}
	
	/**
	 * Moves the book in the given position ahead one position
	 * in the reserve queue.
	 * 
	 * @param index is the index of the book to move ahead.
	 */
	public void moveAheadOneInReserves(int index) {
		if (index < 0 || index >= reserveQueue.size()) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		reserveQueue.shiftForwardOne(index);
	}
	
	/**
	 * Removes the book in the given position from the reserve queue.
	 * 
	 * @param index is the position of the book in the reserve list
	 * to remove.
	 */
	public void unReserve(int index) {
		if (index < 0 || index >= reserveQueue.size()) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		reserveQueue.remove(index);
	}
	
	/**
	 * Places the book at the end of the reserve queue.
	 * 
	 * @param book the book to reserve.
	 */
	public void reserve(Book book) {
		if (book == null) {
			throw new IllegalArgumentException(Constants.EXP_PATRON_NULL_BOOK);
		}
		if (nowCheckedOut < maxCheckedOut && book.isAvailable()) {
			checkedOut.insertAtRear(book);
			book.removeOneCopyFromInventory();
			nowCheckedOut++;
		} else {
			reserveQueue.insertAtRear(book);
		}
	}
	
	/**
	 * Traverses a list of books and returns a string of their info.
	 * 
	 * @param x is the list of books to traverse.
	 * @return the string of book info from the list.
	 */
	private String traverseQueue(UniversalList<Book> x) {
		String queue = "";
		x.resetIterator();
		while (x.hasNext()) {
			queue = queue + x.next().getInfo() + "\n";
		}
		x.resetIterator();
		return queue;
	}
	
	/**
	 * Check out the next available book.
	 */
	private void checkOut(){
		if (checkedOut.size() < maxCheckedOut && !reserveQueue.isEmpty()) {
			Book nextRental = removeFirstAvailable();
			if (nextRental != null) {
				reserve(nextRental);
			}
		}
	}
	
	/**
	 * Remove a book from the queue that is available for checkout.
	 * 
	 * @return the book that is removed from the reservequeue
	 */
	private Book removeFirstAvailable() {
		Book firstAvailable = null;
		int index = 0;
		reserveQueue.resetIterator();
		while (reserveQueue.hasNext() && firstAvailable == null) {
			Book test = reserveQueue.next();
			if (test.isAvailable()) {
				firstAvailable = test;
				reserveQueue.remove(index);
			}
			index++;
		}
		reserveQueue.resetIterator();
		return firstAvailable;
	}

}
