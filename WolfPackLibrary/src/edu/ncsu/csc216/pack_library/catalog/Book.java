/**
 * 
 */
package edu.ncsu.csc216.pack_library.catalog;

import java.util.Scanner;

import edu.ncsu.csc216.pack_library.util.Constants;

/**
 * Defines the behavior for the Book object. Books are
 * items that can be loaned out and checked in. They are
 * stored in an inventory when not checked out.
 * 
 * @author Benjamin Zich
 *
 */
public class Book {
	/** The title and author info for the book. */
	private String title;
	/** The number of books available for checkout. */
	private int numAvailable;
	
	/**
	 * Constructs the Book object. Checks the info to see 
	 * if it is correctly formatted.
	 * 
	 * @param info the book's title and number in stock
	 */
	public Book(String info) {
		if (info == null) {
			throw new IllegalArgumentException(Constants.EXP_LIST_ITEM_NULL);
		}
		Scanner infoScanner = new Scanner(info);
		try {
			numAvailable = infoScanner.nextInt();
			title = infoScanner.nextLine();
		} catch (Exception e) {
			infoScanner.close();
			throw new IllegalArgumentException(info);
		}
		infoScanner.close();
		
		title = title.trim();
		if (title.equals("")) {
			throw new IllegalArgumentException(info);
		}
		if (numAvailable < 0) {
			numAvailable = 0;
		}
	}
	
	/**
	 * Gets the book's title and author as they appear in the 
	 * input file.
	 * 
	 * @return the book info
	 */
	public String getInfo() {
		return title;
	}
	
	/**
	 * Returns the book info prepended by "* " if there
	 * are no copies of the book currently in the library catalog.
	 * 
	 * @return the properly formatted book info
	 */
	public String toString() {
		if (numAvailable == 0) {
			return Constants.CURRENTLY_UNAVAILABLE + title;
		}
		return title;
		
	}
	
	/**
	 * Returns whether there are copies of this book in stock
	 * in the inventory.
	 * 
	 * @return true if there are copies of the book in the inventory.
	 */
	public boolean isAvailable() {
		return numAvailable > 0;
	}
	
	/**
	 * Puts a copy of the book back into the inventory.
	 */
	public void backToInventory() {
		numAvailable++;
	}
	
	/**
	 * Removes a copy of the book from the inventory stock.
	 */
	public void removeOneCopyFromInventory() {
		if (numAvailable <= 0) {
			throw new IllegalStateException(Constants.EXP_BOOK_UNAVAILABLE);
		}
		numAvailable--;
	}
	
	/**
	 * Compares this book object with a second book to
	 * determine the correct order of the books.
	 * 
	 * @param book the book being compared to this book object.
	 * @return 1 if the book should be higher in the list, 0 if lower
	 */
	public int compareTo(Book book) {
		if (book == null) {
			throw new NullPointerException(Constants.EXP_CANNOT_COMPARE);
		}
		String title1 = chopTitle(title);
		String title2 = chopTitle(book.getInfo());
		return title1.compareToIgnoreCase(title2);
	}
	
	/**
	 * Gets rid of "The", "An", and "A" for title comparison with another
	 * book title.
	 * 
	 * @param fullTitle the full title of the book.
	 * @return the title with "The", "An", or "A" removed from the front.
	 */
	private String chopTitle(String fullTitle) {
		Scanner titleScanner = new Scanner(fullTitle);
		String firstWord = titleScanner.next();
		if (firstWord.equalsIgnoreCase("The") || firstWord.equalsIgnoreCase("A") 
				|| firstWord.equalsIgnoreCase("An")) {
			fullTitle = titleScanner.nextLine().trim();
			titleScanner.close();
			return fullTitle;
		} else {
			titleScanner.close();
			return fullTitle;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numAvailable;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		if (numAvailable != other.numAvailable) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
	
	

}
