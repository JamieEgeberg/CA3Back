package utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */
public class Utility {

    public static void persist(EntityManager em, Object o) {
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch (PersistenceException ignored) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void merge(EntityManager em, Object o) {
        try {
            em.getTransaction().begin();
            em.merge(o);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

}
