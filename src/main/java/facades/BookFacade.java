package facades;

import entity.Book;
import utils.Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */
public class BookFacade implements IBookFacade {

    EntityManagerFactory emf;

    public BookFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Book getBookByBookId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Book getBook(long id) {
        return find(id);
    }

    @Override
    public List<Book> getBooks() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ",
                                                Book.class);
        return query.getResultList();
    }

    @Override
    public Book addBook(Book book) {
        Utility.persist(getEntityManager(), book);
        return book;
    }

    @Override
    public Book deleteBook(long id) {

        EntityManager em = emf.createEntityManager();
        Book toBeRemoved = null;
        try {
            em.getTransaction().begin();
            toBeRemoved = em.merge(find(id));
            em.remove(toBeRemoved);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return toBeRemoved;
    }

    @Override
    public Book editBook(Book book) {
        Utility.merge(getEntityManager(), book);
        return book;
    }

    /**
     * Find Person by Id
     *
     * @param id Identity Id
     * @return Person with given Id
     */
    private Book find(Long id) {
        EntityManager em = getEntityManager();
        return em.find(Book.class, id);
    }

}
