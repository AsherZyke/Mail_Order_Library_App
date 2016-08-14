/**
 * 
 */
package edu.ncsu.csc216.pack_library.patron;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the PatronDB class.
 * 
 * @author Benjamin Zich
 *
 */
public class PatronDBTest {

	/**
	 * Tests the Patron constructor.
	 */
	@Test
	public void testPatron() {
		PatronDB patronCatalog = new PatronDB();
		assertEquals("", patronCatalog.listAccounts());
	}
	
	/**
	 * Tests the addNewPatron method. Adds three Patrons to a list and 
	 * ensures that they are ordered in lexicographical order. The exception 
	 * condition for adding a new patron is tested by passing in a patron
	 * with the exact same id as one that already exists in the system.
	 */
	@Test
	public void testAddNewPatron() {
		PatronDB patronCatalog = new PatronDB();
		assertEquals("", patronCatalog.listAccounts());
		
		patronCatalog.addNewPatron("carl", "pw1", 3);
		patronCatalog.addNewPatron("Bob", "pw2", 3);
		patronCatalog.addNewPatron("Zed", "pw3", 3);
		assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		
		try{
			patronCatalog.addNewPatron("carl", "pw4", 2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		}
		
	}
	
	/**
	 * Tests the verifyPatron method. Checks to see if a patron
	 * with the same id and password as one that is already in the system is
	 * considered equivalent to it. Tests the exception conditions by passing in
	 * a patron id that is the same but a patron password that is different from
	 * one that is already in the system. Additionally tests the exception condition
	 * by passing in an id that is different from any in the system but has a password
	 * that matches a patron in the system. 
	 */
	@Test
	public void testVerifyPatron() {
		PatronDB patronCatalog = new PatronDB();
		assertEquals("", patronCatalog.listAccounts());
		Patron patron1 = new Patron("carl", "pw1", 3);
		
		patronCatalog.addNewPatron("carl", "pw1", 3);
		patronCatalog.addNewPatron("Bob", "pw2", 3);
		patronCatalog.addNewPatron("Zed", "pw3", 3);
		assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		
		assertTrue(patron1.compareTo(patronCatalog.verifyPatron("carl", "pw1")) == 0);
		try {
			patronCatalog.verifyPatron("carl", "pw3");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		}
		try {
			patronCatalog.verifyPatron("joe", "pw3");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		}
	}
	
	/**
	 * Tests the cancelAccount method. Creates a list of patrons, then ensures
	 * that the patron that is canceled is removed from the account. The exception 
	 * condition is tested by attempting to remove a patron that is not in the system
	 * list.
	 */
	@Test
	public void testCancelAccount() {
		PatronDB patronCatalog = new PatronDB();
		assertEquals("", patronCatalog.listAccounts());
		
		patronCatalog.addNewPatron("carl", "pw1", 3);
		patronCatalog.addNewPatron("Bob", "pw2", 3);
		patronCatalog.addNewPatron("Zed", "pw3", 3);
		assertEquals("Bob\ncarl\nZed\n", patronCatalog.listAccounts());
		
		patronCatalog.cancelAccount("Zed");
		assertEquals("Bob\ncarl\n", patronCatalog.listAccounts());
		patronCatalog.cancelAccount("Bob");
		assertEquals("carl\n", patronCatalog.listAccounts());
		
		try {
			patronCatalog.cancelAccount("fake");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("carl\n", patronCatalog.listAccounts());
		}
		
	}

}
