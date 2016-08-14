package edu.ncsu.csc216.pack_library.util;

/**
 * Contains constants for the WolfPackLibrary project.  These constants can be used
 * in the program and for testing.
 * @author Sarah Heckman
 * @author Jason King
 */
public class Constants {
	
	/** Added to the beginning of a Book's information if the Book is not available. */
	public static final String CURRENTLY_UNAVAILABLE = "* ";
	
	/** String for admin user name and password */
	public static final String ADMIN = "admin";
	
	/** Exception message if the book is unavailable. */
	public static final String EXP_BOOK_UNAVAILABLE = "The book is unavailable.";
	
	/** Exception message if cannot compare two items. */
	public static final String EXP_CANNOT_COMPARE = "Cannot compare.";
	
	/** Exception message if there are no more values in a list. */
	public static final String EXP_NO_MORE_VALUES_IN_LIST = "No more values in list.";
	
	/** Exception message if the item is null when adding to a list. */
	public static final String EXP_LIST_ITEM_NULL = "Item is null";
	
	/** Exception message if the index is out of bounds in a list. */
	public static final String EXP_INDEX_OUT_OF_BOUNDS = "Index out of bounds.";
	
	/** Exception message if a user id of "admin" is entered. */
	public static final String EXP_PATRON_ADMIN = "A user id cannot be \"admin\".";

	/** Exception message for a max number of books less than one */
	public static final String EXP_PATRON_MAX = "A patron must have the capacity to borrow at least one book.";
	
	/** Exception message if patron id or password are null */
	public static final String EXP_PATRON_NULL = "Id and password cannot be null.";
	
	/** Exception message if patron id or password are empty */
	public static final String EXP_PATRON_EMPTY = "Id and password may not be left empty.";
	
	/** Exception message if patron id or password contain whitespace */
	public static final String EXP_PATRON_WHITESPACE = "Id and password may not contain whitespace.";

	/** Exception message if the book to reserve is null */
	public static final String EXP_PATRON_NULL_BOOK = "Book not specified.";

	/** Exception message when user name or password is incorrect. */
	public static final String EXP_INCORRECT = "Account information is incorrect.";
	
	/** Exception message if PatronDB is full */
	public static final String EXP_PATRON_DB_FULL = "There is no room for additional patrons.";
	
	/** Exception message if Patron already has an account */
	public static final String EXP_PATRON_DB_ACCOUNT_EXISTS = "Patron already has an account.";
	
	/** Exception message if a user is already logged into the system */
	public static final String EXP_LAS_USER_ALREADY_LOGGED_IN = "Current patron or admin must first log out.";

	/** Exception message if a patron/admin attemps an action they are not allowed to do */
	public static final String EXP_ACCESS_DENIED = "Access denied.";
	
	/** Exception message when a patron isn't logged in */
	public static final String EXP_LLS_PATRON_NOT_LOGGED_IN = "No patron is logged in.";

	/** Exception message when the book inventory cannot be created due to a bad file. */
	public static final String EXP_BAD_FILE = "Bad file";


}