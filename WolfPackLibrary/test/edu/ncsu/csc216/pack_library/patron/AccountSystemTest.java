package edu.ncsu.csc216.pack_library.patron;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the AccountSystem class
 * 
 * @author Benjamin Zich
 *
 */
public class AccountSystemTest {
	
	/** The AccountSystem to test. */
	AccountSystem system;

	/**
	 * Sets up the tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		system = new AccountSystem();
	}

	/**
	 * Tests the AccountSystem constructor. Checks to make sure there
	 * is no Admin or Patron logged in. Tests to make sure that the 
	 * list of accounts is empty and that there is no Patron currently
	 * logged in.
	 */
	@Test
	public void testAccountSystem() {
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isPatronLoggedIn());
		assertEquals("", system.listAcounts());
		assertNull(system.getCurrentPatron());
	}
	
	/**
	 * Tests the login and logout methods. Additionally tests
	 * the addNewPatron feature because that is needed to 
	 * test the login and logout methods. First logs in as
	 * the Admin and creates a Patron. Logs out of Admin
	 * and logs back in as the Patron that was created. Tests
	 * the exception conditions by trying to log in as an
	 * Admin or Patron when the other is already logged in.
	 */
	@Test
	public void testLoginLogout() {
		Patron patron1 = new Patron("patron1", "pw1", 1);
		assertFalse(system.isAdminLoggedIn());
		try {
			system.addNewPatron("patron1", "pw1", 1);
		} catch (IllegalStateException e) {
			assertFalse(system.isAdminLoggedIn());
		}
		
		
		system.login("admin", "admin");
		system.addNewPatron("patron1", "pw1", 1);
		system.addNewPatron("patron2", "pw2", 3);
		assertTrue(system.isAdminLoggedIn());
		assertFalse(system.isPatronLoggedIn());
		
		try {
			system.login("patron1", "pw1");
			fail();
		} catch(IllegalStateException e) {
			assertTrue(system.isAdminLoggedIn());
		}
		
		system.logout();
		assertFalse(system.isAdminLoggedIn());
		assertFalse(system.isPatronLoggedIn());
		
		system.login("patron1", "pw1");
		assertTrue(system.isPatronLoggedIn());
		assertFalse(system.isAdminLoggedIn());
		assertTrue(patron1.compareTo(system.getCurrentPatron()) == 0);
		try {
			system.login("admin", "admin");
			fail();
		} catch(IllegalStateException e) {
			assertFalse(system.isAdminLoggedIn());
		}
		try {
			system.login("ding", "admin");
			fail();
		} catch(IllegalStateException e) {
			assertFalse(system.isAdminLoggedIn());
		}
		system.logout();
		
		assertEquals("patron1\npatron2\n", system.listAcounts());
		system.login("admin", "admin");
		system.cancelAccount("patron1");
		assertEquals("patron2\n", system.listAcounts());
		system.cancelAccount("patron2");
		assertEquals("", system.listAcounts());
		
		try {
			system.cancelAccount("this");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("", system.listAcounts());
		}
	}

}
