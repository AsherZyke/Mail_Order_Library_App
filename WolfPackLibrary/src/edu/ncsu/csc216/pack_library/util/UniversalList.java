/**
 * 
 */
package edu.ncsu.csc216.pack_library.util;

import java.util.NoSuchElementException;

/**
 * Defines the behavior for a UniversalList object. The list
 * contains ordered items stored in a zero based index.
 * 
 * @author Benjamin Zich
 *@param <T> the object type that will define this object's 
 *instantiation.
 */
public class UniversalList<T> {
	/** Size of the list */
	private int size;
	/** The front of the list. */
	private Node head;
	/** Variable to iterate through the list. */
	private Node iterator;
	
	
	/**
	 * Constructs an empty list.
	 */
	public UniversalList() {
		size = 0;
	}
	
	/**
	 * Sets the iterator to the front of the
	 * list.
	 */
	public void resetIterator() {
		iterator = head;
	}
	
	/**
	 * Looks at the iterator's next element to determine
	 * if there is an other list item.
	 * 
	 * @return true whenever iterator is pointing to a list
	 * element.
	 */
	public boolean hasNext() {
		return iterator != null;
	}
	
	/**
	 * Returns the element the iterator is pointing to and
	 * moves the iterator to point to the next
	 * element in the list.
	 * 
	 * @return the element that the iterator is pointing to.
	 */
	public T next() {
		if (iterator == null || iterator.data == null) {
			throw new NoSuchElementException(Constants.EXP_NO_MORE_VALUES_IN_LIST);
		}
		T element = iterator.data;
		iterator = iterator.next;
		return element;
	}
	
	/**
	 * Adds an element at a given position. 
	 * 
	 * @param index is the index where the 
	 * element is inserted.
	 * @param element is the element to be inserted
	 * at the given index.
	 */
	public void insertItem(int index, T element) {
		if (element == null) {
			throw new NullPointerException(Constants.EXP_LIST_ITEM_NULL);
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		resetIterator();
		if (index == 0) {
			head = new Node(element, head);
		} else {
			while (index != 1) {
				iterator = iterator.next;
				index--;
			}
			if (iterator.next == null) {
				iterator.next = new Node(element, null);
			} else {
				iterator.next = new Node(element, iterator.next);
			}
		}
		size++;
		resetIterator();
	}
	
	/**
	 * Checks to see if the list is empty.
	 * 
	 * @return true if the list contains no elements.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns an item at a given index. Does not remove
	 * or shift the item in the list. All items remain in the
	 * same order.
	 * 
	 * @param index the index of the item in the list to return.
	 * @return the item at the given index.
	 */
	public T lookAtItemN(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		resetIterator();
		while (index != 0) {
			iterator = iterator.next;
			index--;
		}
		T item = iterator.data;
		resetIterator();
		return item;
	}
	
	/**
	 * Adds an element to the rear of the list.
	 * 
	 * @param element is the element to insert at the end of
	 * the list.
	 * 
	 */
	public void insertAtRear(T element) {
		if (element == null) {
			throw new NullPointerException(Constants.EXP_LIST_ITEM_NULL);
		}
		resetIterator();
		if (iterator == null) {
			head = new Node(element, null);
		} else {
			while (iterator.next != null) {
				iterator = iterator.next;
			}
			iterator.next = new Node(element, null);
		}
		size++;
		resetIterator();
	}
	
	/**
	 * Removes and returns the element in the given position.
	 * 
	 * @param index the position of the element to remove and
	 * return
	 * @return the removed element at the given index.
	 */
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		resetIterator();
		T item;
		if (index == 0) {
			item = head.data;
			head = head.next;
		} else {
			while (index != 1) {
				iterator = iterator.next;
				index--;
			}
			item = iterator.next.data;
			iterator.next = iterator.next.next;
		}
		resetIterator();
		size--;
		return item;
	}
	
	/**
	 * Moves the element at the given position ahead one position
	 * in the list. Does nothing if the element is already at the 
	 * front of the list.
	 * 
	 * @param index the position of the element to shift forward.
	 */
	public void shiftForwardOne(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		resetIterator();
		if (index != 0) {
			while (index > 1) {
				iterator = iterator.next;
				index--;
			}
			T temp = iterator.data;
			iterator.data = iterator.next.data;
			iterator.next.data = temp;
		}
		
	}
	
	/**
	 * Gets the number of elements in the list.
	 * 
	 * @return the size of the list.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Defines the behavior for nodes in the list.
	 * Each node contains data and link to the next 
	 * node. 
	 * 
	 * @author Benjamin Zich
	 *
	 */
	public class Node {
		/** The data stored in the node. */
		public T data;
		/** The link to the next node in the list. */
		public Node next;
		
		/**
		 * Constructs the Node object with stored data 
		 * and a link to the next node in the list.
		 * 
		 * @param data the data that is stored in the node
		 * @param next the link to the next node in the list.
		 */
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

}
