/**
 * 
 */
package edu.ncsu.csc216.pack_library.loan_system;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the LoanSystem class.
 * 
 * @author Benjamin Zich
 *
 */
public class LoanSystemTest {
	/** The LoanSystem to test. */
	private LoanSystem system;

	/**
	 * Sets up the LoanSystem to test.
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		system = new LoanSystem("books-example.txt");
	}

	/**
	 * Tests the login and logout methods. Additionally
	 * tests the addNewPatron method because it is needed
	 * to test login functionality. Logs in as the Admin
	 * to add a Patron. Then logs out of the Admin account. Logs
	 * back in as the Patron that was created. Tests the exception 
	 * conditions by attempting to log in as the Admin when the Patron
	 * is logged in and vice verse. 
	 */
	@Test
	public void testLoginLogout() {
		assertEquals("", system.getCurrentUserId());
		try {
			system.addNewPatron("patron1", "pw1", 1);
		} catch (IllegalStateException e) {
			assertEquals("", system.getCurrentUserId());
		}
		
		
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 1);
		system.addNewPatron("patron2", "pw2", 3);
		assertEquals("admin", system.getCurrentUserId());
		
		try {
			system.login("patron1", "pw1");
			fail();
		} catch(IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		
		system.logout();
		assertEquals("", system.getCurrentUserId());
		
		system.login("patron1", "pw1");
		assertEquals("patron1", system.getCurrentUserId());
		
		try {
			system.login("admin", "admin");
			fail();
		} catch(IllegalStateException e) {
			assertEquals("patron1", system.getCurrentUserId());
		}
		try {
			system.login("ding", "admin");
			fail();
		} catch(IllegalStateException e) {
			assertEquals("patron1", system.getCurrentUserId());
		}
		system.logout();
		
		system.login("admin", "admin");
		system.cancelAccount("patron1");
		system.cancelAccount("patron2");
		
		try {
			system.cancelAccount("this");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
	}
	
	/**
	 * Tests the showInventory method. Ensures that the correct String
	 * of books from the inventory is displayed in alphabetical order
	 * and with a newline character inserted after every title.
	 */
	@Test
	public void testShowInventory() {
		String expected = "A Breath of Snow and Ashes by Diana Gabaldon" + "\n" +
				"Charlie and the Great Glass Elevator by Roald Dahl" + "\n" +
				"* Christine by Stephen King" + "\n" +
				"The Dark Is Rising by Susan Cooper" + "\n" +
				"Dead and Gone by Charlaine Harris" + "\n" +
				"The Dharma Bums by Jack Kerouac" + "\n" +
				"The Elements of Style (4th Edition) by Strunk and White" + "\n" +
				"* Fathers and Sons by Ivan Turgenev" + "\n" +
				"Hearts in Atlantis by Stephen King" + "\n" +
				"The Hero with a Thousand Faces by Joseph Campbell" + "\n" +
				"High Five by Janet Evanovich" + "\n" +
				"The House of the Seven Gables by Nathaniel Hawthorne" + "\n" +
				"The Kitchen God's Wife by Amy Tan" + "\n" +
				"Love in the Time of Cholera by Gabriel Garcia Marquez" + "\n" +
				"Meditations by Marcus Aurelius" + "\n" +
				"Mona Lisa Overdrive by William Gibson" + "\n" +
				"Mrs. Frisby and the Rats of NIMH by Robert C. O'Brien" + "\n" +
				"Perelandra by C. S. Lewis" + "\n" +
				"The Pickwick Papers by Charles Dickens" + "\n" +
				"Pride and Prejudice by Jane Austin" + "\n" +
				"Roll of Thunder, Hear My Cry by Mildred D. Taylor" + "\n" +
				"Shadow of the Hegemon by Orson Scott Card" + "\n" +
				"Skeleton Crew by Stephen King" + "\n" +
				"Special Topics in Calamity Physics by Marisha Pessl" + "\n" +
				"The Tale of Peter Rabbit by Beatrix Potter" + "\n" +
				"Wintersmith by Terry Pratchett" + "\n";
		
		assertEquals(expected, system.showInventory());
	}
	
	/**
	 * Tests the reserveItem method. Reserves several books as a Patron
	 * so that the CheckedOut queue is filled and then several books are 
	 * reserved to the Reserve queue. Tests the exception conditions
	 * by attempting to call the reserve and traverse methods when 
	 * a Patron is not logged in.
	 */
	@Test
	public void testReserveItem() {
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 2);
		try{
			system.traverseCheckedOut();
			fail();
		} catch (IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		try{
			system.traverseReserveQueue();
			fail();
		} catch (IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		system.logout();
		system.login("patron1", "pw1");
		
		system.reserveItem(2);
		assertEquals("Christine by Stephen King\n", system.traverseReserveQueue());
		assertEquals("", system.traverseCheckedOut());
		system.reserveItem(0);
		assertEquals("Christine by Stephen King\n", system.traverseReserveQueue());
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon\n", system.traverseCheckedOut());
		system.reserveItem(1);
		assertEquals("Christine by Stephen King\n", system.traverseReserveQueue());
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon\n"
				+ "Charlie and the Great Glass Elevator by Roald Dahl\n", system.traverseCheckedOut());
		system.reserveItem(4);
		assertEquals("Christine by Stephen King\nDead and Gone by Charlaine Harris\n", system.traverseReserveQueue());
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon\n"
				+ "Charlie and the Great Glass Elevator by Roald Dahl\n", system.traverseCheckedOut());
		
	}
	
	/**
	 * Tests the reserveMoveAheadOne method. Fills the Reserve queue of a Patron
	 * with three books. Then moves a book from the back of the queue to the front.
	 * Tests the exception condition by attempting to call the method as
	 * the Admin.
	 */
	@Test
	public void testReserveMoveAheadOne() {
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 1);
		try{
			system.reserveMoveAheadOne(0);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		system.logout();
		system.login("patron1", "pw1");
		
		system.reserveItem(0);
		system.reserveItem(1);
		system.reserveItem(2);
		system.reserveItem(3);
		assertEquals("Charlie and the Great Glass Elevator by Roald Dahl\n"
				+ "Christine by Stephen King\nThe Dark Is Rising by Susan Cooper\n",
				system.traverseReserveQueue());
		system.reserveMoveAheadOne(2);
		assertEquals("Charlie and the Great Glass Elevator by Roald Dahl\n"
				+ "The Dark Is Rising by Susan Cooper\nChristine by Stephen King\n",
				system.traverseReserveQueue());
		system.reserveMoveAheadOne(1);
		assertEquals("The Dark Is Rising by Susan Cooper\n"
				+ "Charlie and the Great Glass Elevator by Roald Dahl\n"
				+ "Christine by Stephen King\n",
				system.traverseReserveQueue());
		
	}
	
	/**
	 * Tests the removeSelectedFromReserve method. Fills a patrons
	 * Reserve queue with three books, then removes the books
	 * from the reserve queue. First the back of the queue is removed,
	 * then the front, then the last item is removed. The exception
	 * conditino of the method is tested by attempting to remove
	 * a book fromt he reserve when an Admin is logged in.
	 */
	@Test
	public void testRemoveSelectedFromReserve() {
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 1);
		try{
			system.removeSelectedFromReserves(0);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		system.logout();
		system.login("patron1", "pw1");
		
		system.reserveItem(0);
		system.reserveItem(1);
		system.reserveItem(2);
		system.reserveItem(3);
		assertEquals("Charlie and the Great Glass Elevator by Roald Dahl\n"
				+ "Christine by Stephen King\nThe Dark Is Rising by Susan Cooper\n",
				system.traverseReserveQueue());
		system.removeSelectedFromReserves(2);
		assertEquals("Charlie and the Great Glass Elevator by Roald Dahl\n"
				+ "Christine by Stephen King\n",
				system.traverseReserveQueue());
		system.removeSelectedFromReserves(0);
		assertEquals("Christine by Stephen King\n", system.traverseReserveQueue());
		system.removeSelectedFromReserves(0);
		assertEquals("", system.traverseReserveQueue());
		
	}
	
	/**
	 * Tests the returnItem method. Puts two books in a Patron's 
	 * CheckedOut queue, then removes the back item, which is replaced
	 * by a Book from the Reserve Queue. Then the Patron removes the 
	 * front item, which is not replaced by a Book because the only
	 * book in the Reserve queue is not available. The exception
	 * conditino for the method is tested by attempting to call the method
	 * when the Admin is logged in.
	 */
	@Test
	public void testReturnItem() {
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 2);
		try{
			system.returnItem(0);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("admin", system.getCurrentUserId());
		}
		system.logout();
		system.login("patron1", "pw1");
		
		system.reserveItem(0);
		system.reserveItem(1);
		system.reserveItem(2);
		system.reserveItem(3);
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon" + "\n" +
				"Charlie and the Great Glass Elevator by Roald Dahl\n", system.traverseCheckedOut());
		assertEquals("Christine by Stephen King" + "\n" +
				"The Dark Is Rising by Susan Cooper" + "\n", system.traverseReserveQueue());
		system.returnItem(1);
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon" + "\n" +
				"The Dark Is Rising by Susan Cooper" + "\n", system.traverseCheckedOut());
		assertEquals("Christine by Stephen King" + "\n", system.traverseReserveQueue());
		system.returnItem(0);
		assertEquals("The Dark Is Rising by Susan Cooper" + "\n", system.traverseCheckedOut());
		assertEquals("Christine by Stephen King" + "\n", system.traverseReserveQueue());
		
	}
	
	/**
	 * Test the cancelAccount method. Creates a Patron account that has several
	 * books checked out to it in the CheckedOut list. The Admin cancels the
	 * account and the Book catalog is checked to ensure that the books were 
	 * returned. The exception condition is tested by attempting to cancel
	 * the account of a Patron that does nto exist in the system.
	 */
	@Test
	public void testCancelAccount() {
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 2);
		system.logout();
		system.login("patron1", "pw1");
		
		system.reserveItem(13);
		system.reserveItem(4);
		
		String expected1 = "A Breath of Snow and Ashes by Diana Gabaldon" + "\n" +
				"Charlie and the Great Glass Elevator by Roald Dahl" + "\n" +
				"* Christine by Stephen King" + "\n" +
				"The Dark Is Rising by Susan Cooper" + "\n" +
				"* Dead and Gone by Charlaine Harris" + "\n" +
				"The Dharma Bums by Jack Kerouac" + "\n" +
				"The Elements of Style (4th Edition) by Strunk and White" + "\n" +
				"* Fathers and Sons by Ivan Turgenev" + "\n" +
				"Hearts in Atlantis by Stephen King" + "\n" +
				"The Hero with a Thousand Faces by Joseph Campbell" + "\n" +
				"High Five by Janet Evanovich" + "\n" +
				"The House of the Seven Gables by Nathaniel Hawthorne" + "\n" +
				"The Kitchen God's Wife by Amy Tan" + "\n" +
				"* Love in the Time of Cholera by Gabriel Garcia Marquez" + "\n" +
				"Meditations by Marcus Aurelius" + "\n" +
				"Mona Lisa Overdrive by William Gibson" + "\n" +
				"Mrs. Frisby and the Rats of NIMH by Robert C. O'Brien" + "\n" +
				"Perelandra by C. S. Lewis" + "\n" +
				"The Pickwick Papers by Charles Dickens" + "\n" +
				"Pride and Prejudice by Jane Austin" + "\n" +
				"Roll of Thunder, Hear My Cry by Mildred D. Taylor" + "\n" +
				"Shadow of the Hegemon by Orson Scott Card" + "\n" +
				"Skeleton Crew by Stephen King" + "\n" +
				"Special Topics in Calamity Physics by Marisha Pessl" + "\n" +
				"The Tale of Peter Rabbit by Beatrix Potter" + "\n" +
				"Wintersmith by Terry Pratchett" + "\n";
		
		assertEquals(expected1, system.showInventory());
		
		try {
			system.cancelAccount("patron1");
			fail();
		} catch (IllegalStateException e) {
			assertEquals("patron1", system.getCurrentUserId());
		}
		
		system.logout();
		system.login("admin", "admin");
		system.cancelAccount("patron1");
		String expected2 = "A Breath of Snow and Ashes by Diana Gabaldon" + "\n" +
				"Charlie and the Great Glass Elevator by Roald Dahl" + "\n" +
				"* Christine by Stephen King" + "\n" +
				"The Dark Is Rising by Susan Cooper" + "\n" +
				"Dead and Gone by Charlaine Harris" + "\n" +
				"The Dharma Bums by Jack Kerouac" + "\n" +
				"The Elements of Style (4th Edition) by Strunk and White" + "\n" +
				"* Fathers and Sons by Ivan Turgenev" + "\n" +
				"Hearts in Atlantis by Stephen King" + "\n" +
				"The Hero with a Thousand Faces by Joseph Campbell" + "\n" +
				"High Five by Janet Evanovich" + "\n" +
				"The House of the Seven Gables by Nathaniel Hawthorne" + "\n" +
				"The Kitchen God's Wife by Amy Tan" + "\n" +
				"Love in the Time of Cholera by Gabriel Garcia Marquez" + "\n" +
				"Meditations by Marcus Aurelius" + "\n" +
				"Mona Lisa Overdrive by William Gibson" + "\n" +
				"Mrs. Frisby and the Rats of NIMH by Robert C. O'Brien" + "\n" +
				"Perelandra by C. S. Lewis" + "\n" +
				"The Pickwick Papers by Charles Dickens" + "\n" +
				"Pride and Prejudice by Jane Austin" + "\n" +
				"Roll of Thunder, Hear My Cry by Mildred D. Taylor" + "\n" +
				"Shadow of the Hegemon by Orson Scott Card" + "\n" +
				"Skeleton Crew by Stephen King" + "\n" +
				"Special Topics in Calamity Physics by Marisha Pessl" + "\n" +
				"The Tale of Peter Rabbit by Beatrix Potter" + "\n" +
				"Wintersmith by Terry Pratchett" + "\n";
		
		assertEquals(expected2, system.showInventory());
		system.logout();
		try {
			system.login("patron1", "pw1");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("", system.getCurrentUserId());
		}
	}

}
