/**
 * 
 */
package edu.ncsu.csc216.pack_library.catalog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_library.util.Constants;
import edu.ncsu.csc216.pack_library.util.UniversalList;

/**
 * Defines the behavior for the BookDB class. The BookDB class
 * is used to store an inventory of books to potentially loan
 * to patrons.
 * 
 * @author Benjamin Zich
 *
 */
public class BookDB {
	/** An inventory list of books. */
	private UniversalList<Book> books;
	
	/**
	 * Constructs the book library database from a file.
	 * 
	 * @param fileName the file to read the individual
	 * book data from.
	 */
	public BookDB(String fileName) {
		books = new UniversalList<Book>();
		try {
			readFromFile(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException(Constants.EXP_BAD_FILE);
		}
	}
	
	/**
	 * Gets a string corresponding to the books in the database in the proper
	 * order. Strings for successive books are separated
	 * by new lines, including the last book in the list.
	 * 
	 * @return the string of books separated by new lines
	 */
	public String traverse() {
		books.resetIterator();
		String booklist = "";
		while (books.hasNext()) {
			booklist = booklist + books.next() + "\n";
		}
		books.resetIterator();
		return booklist;
	}
	
	/**
	 * Returns the book at the given position.
	 * 
	 * @param index the position of the book in the list to return
	 * @return the book at the given index
	 */
	public Book findItemAt(int index) {
		if (index < 0 || index >= books.size()) {
			throw new IndexOutOfBoundsException(Constants.EXP_INDEX_OUT_OF_BOUNDS);
		}
		return books.lookAtItemN(index);
	}
	
	
	/**
	 * Rreads the books from a file.
	 * 
	 * @param fileName the file to read books from.
	 */
	private void readFromFile(String fileName) {
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			while (fileScanner.hasNextLine()) {
				insertInOrder(new Book(fileScanner.nextLine()));
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Add a book in alphabetical.
	 * 
	 * @param book the book to be added to the book list.
	 */
	private void insertInOrder(Book book) {
		if (books.isEmpty()) {
			books.insertItem(0, book);
		} else if (book.compareTo(books.next()) <= 0) {
			books.insertItem(0, book);
		} else {
			int counter = -1;
			int measurer = 1;
			books.resetIterator();
			while (books.hasNext() && measurer > 0) {
				measurer = book.compareTo(books.next());
				counter++;
			}
			if (!books.hasNext() && measurer > 0) {
				books.insertAtRear(book);
			} else {
				books.insertItem(counter, book);
			}
		}	
	}
}
