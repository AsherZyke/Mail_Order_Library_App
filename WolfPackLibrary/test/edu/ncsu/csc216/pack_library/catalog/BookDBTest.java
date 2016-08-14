/**
 * 
 */
package edu.ncsu.csc216.pack_library.catalog;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BookDB class.
 * 
 * @author Benjamin Zich
 *
 */
public class BookDBTest {
	/** The book library. */
	BookDB library;

	/**
	 * Sets up the BookDB class.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		library = new BookDB("books-example.txt");
	}

	/**
	 * Tests the BookDB constructor. Makes sure that all of the books in the file
	 * were added in the proper alphabetical order. Tests the exception conditions
	 * by passing in a null object and by attempting to find an item at an index
	 * that does not contain an object.
	 */
	@Test
	public void testBookDB() {
		assertEquals("A Breath of Snow and Ashes by Diana Gabaldon", library.findItemAt(0).getInfo());
		assertEquals("Charlie and the Great Glass Elevator by Roald Dahl", library.findItemAt(1).getInfo());
		assertEquals("Christine by Stephen King", library.findItemAt(2).getInfo());
		assertEquals("The Dark Is Rising by Susan Cooper", library.findItemAt(3).getInfo());
		assertEquals("Dead and Gone by Charlaine Harris", library.findItemAt(4).getInfo());
		assertEquals("The Dharma Bums by Jack Kerouac", library.findItemAt(5).getInfo());
		assertEquals("The Elements of Style (4th Edition) by Strunk and White", library.findItemAt(6).getInfo());
		assertEquals("Fathers and Sons by Ivan Turgenev", library.findItemAt(7).getInfo());
		assertEquals("Hearts in Atlantis by Stephen King", library.findItemAt(8).getInfo());
		assertEquals("The Hero with a Thousand Faces by Joseph Campbell", library.findItemAt(9).getInfo());
		assertEquals("High Five by Janet Evanovich", library.findItemAt(10).getInfo());
		assertEquals("The House of the Seven Gables by Nathaniel Hawthorne", library.findItemAt(11).getInfo());
		assertEquals("The Kitchen God's Wife by Amy Tan", library.findItemAt(12).getInfo());
		assertEquals("Love in the Time of Cholera by Gabriel Garcia Marquez", library.findItemAt(13).getInfo());
		assertEquals("Meditations by Marcus Aurelius", library.findItemAt(14).getInfo());
		assertEquals("Mona Lisa Overdrive by William Gibson", library.findItemAt(15).getInfo());
		assertEquals("Mrs. Frisby and the Rats of NIMH by Robert C. O'Brien", library.findItemAt(16).getInfo());
		assertEquals("Perelandra by C. S. Lewis", library.findItemAt(17).getInfo());
		assertEquals("The Pickwick Papers by Charles Dickens", library.findItemAt(18).getInfo());
		assertEquals("Pride and Prejudice by Jane Austin", library.findItemAt(19).getInfo());
		assertEquals("Roll of Thunder, Hear My Cry by Mildred D. Taylor", library.findItemAt(20).getInfo());
		assertEquals("Shadow of the Hegemon by Orson Scott Card", library.findItemAt(21).getInfo());
		assertEquals("Skeleton Crew by Stephen King", library.findItemAt(22).getInfo());
		assertEquals("Special Topics in Calamity Physics by Marisha Pessl", library.findItemAt(23).getInfo());
		assertEquals("The Tale of Peter Rabbit by Beatrix Potter", library.findItemAt(24).getInfo());
		assertEquals("Wintersmith by Terry Pratchett", library.findItemAt(25).getInfo());
		
		
		//File does not exist
		BookDB fakeDB = null;
		try {
			fakeDB = new BookDB("123fakestreet.txt");
		} catch (IllegalArgumentException e) {
			assertNull(fakeDB);
		}
		
		//Out of bounds findItem
		try {
			library.findItemAt(30);
		} catch (IndexOutOfBoundsException e) {
			assertNull(fakeDB);
		}

	}
	
	/**
	 * Tests the traverse method. Checks to see whether the display inserts a newline
	 * character after each title in the list.
	 */
	@Test
	public void testTraverse() {
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
		
		assertEquals(expected, library.traverse());
				
	}
	
	

}
