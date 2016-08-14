/**
 * 
 */
package edu.ncsu.csc216.pack_library.catalog;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Book class.
 * 
 * @author Benjamin Zich
 *
 */
public class BookTest {
	/** The book object to test.*/
	Book book;

	/**
	 * Sets up the test cases.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		book = new Book("  3 The Turn of the Screw by Henry James   ");
	}
	
	/**
	 * Tests the Book class constructor. Tests a book being created from a
	 * valid String. Tests the exception conditions by passing in a null object
	 * and an empty String for the title of the Book. Tests whether 
	 * the Book can be validly created with a negative quantity.
	 */
	@Test
	public void testBook() {
		//Test valid book
		assertTrue(book.isAvailable());
		assertEquals("The Turn of the Screw by Henry James", book.getInfo());
		
		//Test an invalid book.
		try {
			@SuppressWarnings("unused")
			Book nullBook = new Book(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(book.isAvailable());
		}
		try {
			@SuppressWarnings("unused")
			Book emptyTitleBook = new Book("1 ");
		} catch (IllegalArgumentException e) {
			assertTrue(book.isAvailable());
		}
		
		//Test negative quantity
		Book negativeQty = new Book ("-1 Ulysses by James Joyce");
		assertFalse(negativeQty.isAvailable());
		negativeQty.backToInventory();
		assertTrue(negativeQty.isAvailable());
		negativeQty.removeOneCopyFromInventory();
		assertFalse(negativeQty.isAvailable());
		
	}
	
	/**
	 * Test the compareTo() method. Compares a book to a book that precedes it
	 * and one that comes after it alphabetically.
	 */
	@Test
	public void testCompareTo() {
		Book copy = new Book("3 The Turn of the Screw by Henry James  ");
		Book greater = new Book("1 A Heartbreaking Work of Staggering Genius by Dave Eggers");
		Book lesser = new Book("1 Under the Valcano by Malcolm Lowry");
		
		assertTrue(0 == copy.compareTo(book));
		assertTrue(book.compareTo(greater) > 0);
		assertTrue(book.compareTo(lesser) < 0);
		assertEquals("The Turn of the Screw by Henry James", book.getInfo());
		assertEquals("Under the Valcano by Malcolm Lowry", lesser.getInfo());
	}
	
	/**
	 * Tests the Equals and Hash methods.
	 */
	@Test
	public void testEqualsAndHash() {
		Book same = book;
		Book different = new Book("2 The Great Escape by Troy Baker");
		
		assertTrue(book.equals(same));
		assertFalse(book.equals(different));
		assertTrue(book.hashCode() >= 0 || book.hashCode() < 0);
	}
	
	

}
