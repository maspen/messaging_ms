package com.matt.ms.auto_sender.foo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

	/** Old way */
	public List<Author> getAllAuthorsAlphabeticallyOldWay(List<Book> books) {
		List<Author> authors = new ArrayList<Author>();
		
		for(Book book : books) {
			Author author = book.getAuthor();
			if(!authors.contains(author)) {
				authors.add(author);
			}
		}
		
		Collections.sort(authors, new Comparator<Author>() {
			public int compare(Author o1, Author o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		
		return authors;
	}
	
	/** Revision using Java 8 */
	public List<Author> getAllAuthorsAlphabeticallyJava8(List<Book> books) {
		return books.stream()
				.map(book -> book.getAuthor())
				.distinct()
				.sorted((o1, o2) -> 
					o1.getLastName().compareTo(o2.getLastName()))
				.collect(Collectors.toList());
	}
	
	/** Further revision using Java 8 */
	public List<Author> getAllAuthorsAlphabeticallyJava8V2(List<Book> books) {
		return books.stream()
				.map(Book::getAuthor)
				.distinct()
				.sorted(Comparator.comparing(Author::getLastName))
				.collect(Collectors.toList());
	}
	
	/** Using java 8 'Optional' type - 'i might have a value, or it might be null' 
	 * @throws BookNotFoundException */
	public Book findBookByTitle(List<Book> books, String title) throws BookNotFoundException {
		Optional<Book> foundBook = books.stream()
				.filter(book -> book.getTitle()
				.equals(title))
				.findFirst();
		
		return foundBook.orElseThrow(() -> 
			new BookNotFoundException("Did not find book with title " + title));
	}
	
	public Book findBookByTitleOrReturnAnother(List<Book> books, String title) throws BookNotFoundException {
		Optional<Book> foundBook = books.stream()
				.filter(book -> book.getTitle()
				.equals(title))
				.findFirst();
		
		return foundBook.orElseGet(() -> getFirstBook(books));
	}
	
	private Book getFirstBook(List<Book> books) {
		return books.get(0);
	}
}
