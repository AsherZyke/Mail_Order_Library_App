/**
 * 
 */
package edu.ncsu.csc216.pack_library.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the UniversalList class.
 * 
 * @author Benjamin Zich
 *
 */
public class UniversalListTest {
	/** The list for the tests. */
	private UniversalList<String> list;

	/**
	 * Sets up the UniversalList tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new UniversalList<String>();
	}

	/**
	 * Tests the UniversalList constructor. Tests the exception
	 * condition of the constructor by attempting to look at an item
	 * with an invalid index and by attempting to remove and item
	 * with an invalid index.
	 */
	@Test
	public void testUniversalList() {
		assertEquals(0, list.size());
		try {
			list.lookAtItemN(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
	}
	
	/**
	 * Tests the insertItem and insertAtRear methods. Inserts an item
	 * when the list is empty, at the front of the list, inserting
	 * an item in the middle of a list, and at the end of a list. Tests
	 * the exception condition by attempting to insert an item at an
	 * invalid index and attempting to insert a null itm into the 
	 * list.
	 */
	@Test
	public void testInsertItem() {
		try {
			list.insertItem(1, "apple");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		//Add element to empty list
		list.insertItem(0, "apple");
		assertEquals(1, list.size());
		assertEquals("apple", list.lookAtItemN(0));
		
		//Add element to the front of a list
		list.insertItem(0, "front");
		assertEquals(2, list.size());
		assertEquals("front", list.lookAtItemN(0));
		assertEquals("apple", list.lookAtItemN(1));
		
		
		//Add element to the middle of a list
		list.insertItem(1, "middle");
		assertEquals(3, list.size());
		assertEquals("front", list.lookAtItemN(0));
		assertEquals("middle", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		
		
		//Add element to the back of a list
		list.insertItem(3, "back");
		assertEquals(4, list.size());
		assertEquals("front", list.lookAtItemN(0));
		assertEquals("middle", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("back", list.lookAtItemN(3));
		
		
		//Attempt to add a null element.  Check that the element
		//was not added.
		String foo = null;
		try {
			list.insertItem(4, foo);
			fail();
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
			assertEquals("front", list.lookAtItemN(0));
			assertEquals("middle", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("back", list.lookAtItemN(3));
		}
		
		
		//Attempt to add at index -1.  Check that the element was not
		//added.
		try {
			list.insertItem(-1, "negative");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("front", list.lookAtItemN(0));
			assertEquals("middle", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("back", list.lookAtItemN(3));
		}
		
		
		//Attempt to add at index 5 (since there are 4 elements in the list).
		//Check that the element was not added.
		try {
			list.insertItem(5, "too far");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("front", list.lookAtItemN(0));
			assertEquals("middle", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("back", list.lookAtItemN(3));
		}
		
		//Test the insertAtBack method
		list.insertAtRear("new back");
		assertEquals(5, list.size());
		assertEquals("front", list.lookAtItemN(0));
		assertEquals("middle", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("back", list.lookAtItemN(3));
		assertEquals("new back", list.lookAtItemN(4));
		
		//Test the null and empty String exceptions
		try {
			list.insertAtRear(foo);
			fail();
		} catch (NullPointerException e) {
			assertEquals(5, list.size());
			assertEquals("front", list.lookAtItemN(0));
			assertEquals("middle", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("back", list.lookAtItemN(3));
			assertEquals("new back", list.lookAtItemN(4));
		}
		
	}
	
	/**
	 * Tests that elements are correctly removed from the front, middle, and back of an 
	 * UnversalList.  Removing the last element should leave an empty list.  The bounds are
	 * checked for the appropriate exceptions.
	 */
	@Test
	public void testRemove() {
		//Attempt to remove an element from an empty list
		try {
			list.remove(0);
			fail();
		} catch(IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		
		//Add 4 elements to the list and test that the contents are correct.
		//Reuse code from your testAddIntE.
		list.insertItem(0, "orange");
		list.insertItem(1, "banana");
		list.insertItem(2, "apple");
		list.insertItem(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("banana", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("kiwi", list.lookAtItemN(3));
		
		//Test that IndexOutOfBoundsException is thrown when remove() is passed
		//a negative index.  Make sure the list is unchanged.
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.lookAtItemN(0));
			assertEquals("banana", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("kiwi", list.lookAtItemN(3));
		}
		
		
		//Test that IndexOutOfBoundsException is thrown when remove() is passed
		//an index > size() - 1.  Make sure the list is unchanged.
		try {
			list.remove(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
			assertEquals("orange", list.lookAtItemN(0));
			assertEquals("banana", list.lookAtItemN(1));
			assertEquals("apple", list.lookAtItemN(2));
			assertEquals("kiwi", list.lookAtItemN(3));
		}
		
		
		//Remove middle element.  Test that the removed element is correct and
		//that the remaining list is correct after the removal.
		String s1 = list.remove(1);
		assertEquals(s1, "banana");
		assertEquals(3, list.size());
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("apple", list.lookAtItemN(1));
		assertEquals("kiwi", list.lookAtItemN(2));
		
		//Remove first element
		String s2 = list.remove(0);
		assertEquals(s2, "orange");
		assertEquals(2, list.size());
		assertEquals("apple", list.lookAtItemN(0));
		assertEquals("kiwi", list.lookAtItemN(1));
		
		
		//Remove last element
		String s3 = list.remove(1);
		assertEquals(s3, "kiwi");
		assertEquals(1, list.size());
		assertEquals("apple", list.lookAtItemN(0));
		
		//Remove only element
		String s4 = list.remove(0);
		assertEquals(s4, "apple");
		assertEquals(0, list.size());
						
	}
	
	/**
	 * Tests the Next and hasNext methods. Verifies that
	 * the hasNext and next methods returns the correct items
	 * over a list of Strings. Tests the exception condition by
	 * attempting to call next where there is no element in the list.
	 */
	@Test
	public void testNext() {
		
		try {
			list.next();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(0, list.size());
		}
		
		//Add 4 elements to the list and test that the contents are correct.
		assertTrue(list.isEmpty());
		assertFalse(list.hasNext());
		list.insertItem(0, "orange");
		assertFalse(list.isEmpty());
		list.insertItem(1, "banana");
		list.insertItem(2, "apple");
		list.insertItem(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("banana", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("kiwi", list.lookAtItemN(3));
		
		//Test the hasNext and Next methods
		assertTrue(list.hasNext());
		assertEquals("orange", list.next());
		assertEquals("banana", list.next());
		assertEquals("apple", list.next());
		assertEquals("kiwi", list.next());
		assertFalse(list.hasNext());
		
		try {
			list.next();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(4, list.size());
		}
	}
	
	/**
	 * Test the shiftForwardOne method. Tests the shift forward method
	 * at the front of a list, then in the middle of the list, and lastly at the 
	 * back of a list. Tests the exception condition by attempting to shift an
	 * element forward at an invalid index position.
	 */
	@Test
	public void testShiftForwardOne() {
		//Add 4 elements to the list and test that the contents are correct.
		assertFalse(list.hasNext());
		list.insertItem(0, "orange");
		list.insertItem(1, "banana");
		list.insertItem(2, "apple");
		list.insertItem(3, "kiwi");
		assertEquals(4, list.size());
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("banana", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("kiwi", list.lookAtItemN(3));
		
		//Front of the list
		list.shiftForwardOne(0);
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("banana", list.lookAtItemN(1));
		assertEquals("apple", list.lookAtItemN(2));
		assertEquals("kiwi", list.lookAtItemN(3));
		
		//Middle of the list
		list.shiftForwardOne(2);
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("apple", list.lookAtItemN(1));
		assertEquals("banana", list.lookAtItemN(2));
		assertEquals("kiwi", list.lookAtItemN(3));
		
		//Back of the list
		list.shiftForwardOne(3);
		assertEquals("orange", list.lookAtItemN(0));
		assertEquals("apple", list.lookAtItemN(1));
		assertEquals("kiwi", list.lookAtItemN(2));
		assertEquals("banana", list.lookAtItemN(3));
		
		//Test exception
		try {
			list.shiftForwardOne(5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}

	}

}
