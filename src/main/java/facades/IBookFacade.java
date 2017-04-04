package facades;

import entity.Book;

import java.util.List;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */
public interface IBookFacade {

    /**
     * Get a single Book by it's Identity id
     *
     * @param id Identity id
     * @return single Book by id
     */
    Book getBook(long id);

    /**
     * Get all books
     *
     * @return all books
     */
    List<Book> getBooks();

    /**
     * Add book to database and return it
     *
     * @param book Book
     * @return book added
     */
    Book addBook(Book book);

    /**
     * Delete book from database with given Id
     *
     * @param id Identity Id
     * @return Book deleted
     */
    Book deleteBook(long id);

    /**
     * Update a book in the database
     *
     * @param book Book
     * @return book updated
     */
    Book editBook(Book book);
}
