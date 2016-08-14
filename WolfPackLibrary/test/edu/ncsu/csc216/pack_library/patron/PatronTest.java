/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_library.catalog.Book;

/**
 * Tests the Patron class.
 * 
 * @author Benjamin Zich
 *
 */
public class PatronTest {

	/**
	 * Tests the Patron constructor.
	 */
	@Test
	public void testPatron() {
		Patron patron1 = new Patron("one", "pw1", 3);
		Patron patron2 = new Patron("two", "pw2", 2);
		assertEquals("one", patron1.getId());
		assertTrue(patron1.verifyPassword("pw1"));
		assertTrue(patron1.compareTo(patron2) < 0);
		
		//Test exceptions
		try {
			String foo = null;
			@SuppressWarnings("unused")
			Patron patron3 = new Patron(foo, "pw3", 3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("one", patron1.getId());
		}
		try {
			String foo = "";
			@SuppressWarnings("unused")
			Patron patron3 = new Patron("four", foo, 3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("one", patron1.getId());
		}
		try {
			@SuppressWarnings("unused")
			Patron patron5 = new Patron("fi ve", "pw 5", 3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("one", patron1.getId());
		}
		
	}
	
	/**
	 * Tests the reserve method. Tests by first adding books to the CheckedOut list until
	 * the limit reached. After the limit is reached, the books that are reserved are put
	 * in the reserve queue. The books put in the reserve queue do not have their quantity
	 * decremented. The exception condition is tested by passing in a null object to the
	 * reserve method.
	 */
	@Test
	public void testReserve() {
		Patron patron1 = new Patron("one", "pw1", 2);
		Book book1 = new Book("2 Pride and Prejudice by Jane Austen");
		Book book2 = new Book("1 Ulysses by James Joyce");
		Book book3 = new Book(" 2 A Fake Book by Ben Zich");
		Book book4 = new Book(" 1 Lolita by Vladimir Nabokov");
		
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		patron1.reserve(book1);
		patron1.reserve(book2);
		patron1.reserve(book3);
		patron1.reserve(book4);
		assertEquals("Pride and Prejudice by Jane Austen\nUlysses by James Joyce\n", patron1.traverseCheckedOut());
		assertEquals("A Fake Book by Ben Zich\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		
		//Test going directly into reserve queue
		Patron patron2 = new Patron("two", "pw2", 2);
		assertEquals("", patron2.traverseCheckedOut());
		patron2.reserve(book2);
		assertEquals("", patron2.traverseCheckedOut());
		assertEquals("Ulysses by James Joyce\n", patron2.traverseReserveQueue());
		patron2.reserve(book4);
		assertEquals("Lolita by Vladimir Nabokov\n", patron2.traverseCheckedOut());
		assertEquals("Ulysses by James Joyce\n", patron2.traverseReserveQueue());
		
		try {
			Book foo = null;
			patron2.reserve(foo);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Ulysses by James Joyce\n", patron2.traverseReserveQueue());
		}
		
	}
	
	/**
	 * Tests the returnBook method. Returns a book when the reserve queue has available
	 * books in it so that the returned book should be replaced by the available book
	 * in the reserve queue. Tests the exception condition by passing in an index that is
	 * out of bounds.
	 */
	@Test
	public void testReturnBook() {
		//From testReserve
		Patron patron1 = new Patron("one", "pw1", 2);
		Book book1 = new Book("2 Pride and Prejudice by Jane Austen");
		Book book2 = new Book("1 Ulysses by James Joyce");
		Book book3 = new Book(" 2 A Fake Book by Ben Zich");
		Book book4 = new Book(" 1 Lolita by Vladimir Nabokov");
		
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		patron1.reserve(book1);
		patron1.reserve(book2);
		patron1.reserve(book3);
		patron1.reserve(book4);
		assertEquals("Pride and Prejudice by Jane Austen\nUlysses by James Joyce\n", patron1.traverseCheckedOut());
		assertEquals("A Fake Book by Ben Zich\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		
		patron1.returnBook(0);
		assertEquals("Ulysses by James Joyce\nA Fake Book by Ben Zich\n", patron1.traverseCheckedOut());
		assertEquals("Lolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		patron1.returnBook(1);
		assertEquals("Ulysses by James Joyce\nLolita by Vladimir Nabokov\n", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		
		try {
			patron1.returnBook(2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("", patron1.traverseReserveQueue());
		}
	}
	
	/**
	 * Test method moveAheadOneInReserves. Moves a book from the last position to the first
	 * position in the reserve queue when there are three books in the reserve queue. Tests
	 * the reserve queue exception condition by passing in an invalid index.
	 */
	@Test
	public void testMoveAheadOneInReserves() {
		Patron patron1 = new Patron("one", "pw1", 1);
		Book book1 = new Book("2 Pride and Prejudice by Jane Austen");
		Book book2 = new Book("1 Ulysses by James Joyce");
		Book book3 = new Book(" 2 A Fake Book by Ben Zich");
		Book book4 = new Book(" 1 Lolita by Vladimir Nabokov");
		
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		patron1.reserve(book1);
		patron1.reserve(book2);
		patron1.reserve(book3);
		patron1.reserve(book4);
		assertEquals("Pride and Prejudice by Jane Austen\n", patron1.traverseCheckedOut());
		assertEquals("Ulysses by James Joyce\nA Fake Book by Ben Zich\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		
		patron1.moveAheadOneInReserves(2);
		assertEquals("Ulysses by James Joyce\nLolita by Vladimir Nabokov\nA Fake Book by Ben Zich\n", patron1.traverseReserveQueue());
		patron1.moveAheadOneInReserves(1);
		assertEquals("Lolita by Vladimir Nabokov\nUlysses by James Joyce\nA Fake Book by Ben Zich\n", patron1.traverseReserveQueue());
		patron1.moveAheadOneInReserves(0);
		assertEquals("Lolita by Vladimir Nabokov\nUlysses by James Joyce\nA Fake Book by Ben Zich\n", patron1.traverseReserveQueue());
		
		try {
			patron1.moveAheadOneInReserves(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Lolita by Vladimir Nabokov\nUlysses by James Joyce\nA Fake Book by Ben Zich\n", patron1.traverseReserveQueue());
		}
	}
	
	/**
	 * Test the unReserve method. Removes a book from the reserve queue that is populated
	 * by three books and removes all of them. First the back of the queue is removed, 
	 * then the front, then the remaining book is removed. The exception condition is 
	 * tested by passing an invalid index to the object.
	 */
	@Test
	public void testUnReserve() {
		Patron patron1 = new Patron("one", "pw1", 1);
		Book book1 = new Book("2 Pride and Prejudice by Jane Austen");
		Book book2 = new Book("1 Ulysses by James Joyce");
		Book book3 = new Book(" 2 A Fake Book by Ben Zich");
		Book book4 = new Book(" 1 Lolita by Vladimir Nabokov");
		
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		patron1.reserve(book1);
		patron1.reserve(book2);
		patron1.reserve(book3);
		patron1.reserve(book4);
		assertEquals("Pride and Prejudice by Jane Austen\n", patron1.traverseCheckedOut());
		assertEquals("Ulysses by James Joyce\nA Fake Book by Ben Zich\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		
		patron1.unReserve(1);
		assertEquals("Ulysses by James Joyce\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		patron1.unReserve(1);
		assertEquals("Ulysses by James Joyce\n", patron1.traverseReserveQueue());
		patron1.unReserve(0);
		assertEquals("", patron1.traverseReserveQueue());
		try {
			patron1.unReserve(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("", patron1.traverseReserveQueue());
		}
	}
	
	/**
	 * Tests the closeAccount method. Closes the account of a Patron to see if the books
	 * are returned to the library catalog. 
	 */
	@Test
	public void testCloseAccount() {
		Patron patron1 = new Patron("one", "pw1", 2);
		Book book1 = new Book("1 Pride and Prejudice by Jane Austen");
		Book book2 = new Book("1 Ulysses by James Joyce");
		Book book3 = new Book(" 1 A Fake Book by Ben Zich");
		Book book4 = new Book(" 1 Lolita by Vladimir Nabokov");
		
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		patron1.reserve(book1);
		patron1.reserve(book2);
		patron1.reserve(book3);
		patron1.reserve(book4);
		assertEquals("Pride and Prejudice by Jane Austen\nUlysses by James Joyce\n", patron1.traverseCheckedOut());
		assertEquals("A Fake Book by Ben Zich\nLolita by Vladimir Nabokov\n", patron1.traverseReserveQueue());
		assertFalse(book1.isAvailable());
		assertFalse(book2.isAvailable());
		assertTrue(book3.isAvailable());
		assertTrue(book4.isAvailable());
		
		patron1.closeAccount();
		assertEquals("", patron1.traverseCheckedOut());
		assertEquals("", patron1.traverseReserveQueue());
		assertTrue(book1.isAvailable());
		assertTrue(book2.isAvailable());
		assertTrue(book3.isAvailable());
		assertTrue(book4.isAvailable());
	}

}
