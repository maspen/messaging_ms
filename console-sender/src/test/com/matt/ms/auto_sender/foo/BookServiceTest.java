package com.matt.ms.auto_sender.foo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

	static final String[] authorLastNameArray = {
			"andrews",
			"burt",
			"commons"
	};
	
	private List<Book> bookList;
	private BookService bookService;
	
	@Before
	public void createBookList() {		
		bookList = new ArrayList<Book>();
		bookList.add(new Book("book 2", new Author("bob", authorLastNameArray[1])));
		bookList.add(new Book("book 1", new Author("abe", authorLastNameArray[0])));
		bookList.add(new Book("book 3", new Author("charlie", authorLastNameArray[2])));
		
		bookService = new BookService();
	}
	
	//@Test
	public void getAllAuthorsAlphabeticallyOldWay() {
		List<Author> sortedAuthors = bookService.getAllAuthorsAlphabeticallyOldWay(bookList);
		Assert.assertNotNull(sortedAuthors);
		Assert.assertEquals(3, sortedAuthors.size());
		
		assertOrder(sortedAuthors);
	}
	
	//@Test
	public void getAllAuthorsAlphabeticallyJava8() {
		List<Author> sortedAuthors = bookService.getAllAuthorsAlphabeticallyJava8(bookList);
		Assert.assertNotNull(sortedAuthors);
		Assert.assertEquals(3, sortedAuthors.size());
		
		assertOrder(sortedAuthors);
	}
	
	//@Test
	public void getAllAuthorsAlphabeticallyJava8V2() {
		List<Author> sortedAuthors = bookService.getAllAuthorsAlphabeticallyJava8V2(bookList);
		Assert.assertNotNull(sortedAuthors);
		Assert.assertEquals(3, sortedAuthors.size());
		
		assertOrder(sortedAuthors);
	}
	
	private void assertOrder(List<Author> sortedAuthorList) {
		Assert.assertEquals(authorLastNameArray[0], sortedAuthorList.get(0).getLastName());
		Assert.assertEquals(authorLastNameArray[1], sortedAuthorList.get(1).getLastName());
		Assert.assertEquals(authorLastNameArray[2], sortedAuthorList.get(2).getLastName());
	}
	
	//@Test
	public void findBookByTitle() {
		Book foundBook = null;
		try {
			foundBook = bookService.findBookByTitle(bookList, "book 1");
		} catch (BookNotFoundException e) {
			Assert.assertNull(e);
		}
		
		Assert.assertNotNull(foundBook);
		Assert.assertEquals("book 1", foundBook.getTitle());
	}

	//@Test
	public void findBookByTitleThrowsException() {
		Book foundBook = null;
		try {
			foundBook = bookService.findBookByTitle(bookList, "book n");
		} catch (BookNotFoundException e) {
			Assert.assertNotNull(e);
		}
		
		Assert.assertNull(foundBook);
	}
	
	//@Test
	public void findBookByTitleOrReturnAnother() {
		Book foundBook = null;
		try {
			foundBook = bookService.findBookByTitleOrReturnAnother(bookList, "book n");
		} catch (BookNotFoundException e) {
			Assert.assertNull(e);
		}
		
		Assert.assertNotNull(foundBook);
		Assert.assertEquals(bookList.get(0).getTitle(), foundBook.getTitle());
	}
}
