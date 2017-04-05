package facades;

import entity.Role;
import utils.Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Niki on 2017-04-05.
 *
 * @author Niki
 */
public class RoleFacade implements IRoleFacade {

    EntityManagerFactory emf;

    public RoleFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Role getRoleByRoleId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Role getRole(long id) {
        return find(id);
    }

    @Override
    public List<Role> getRoles() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Role> query = em.createQuery("SELECT b FROM Book b ",
                                                Role.class);
        return query.getResultList();
    }

    @Override
    public Role addRole(Role role) {
        Utility.persist(getEntityManager(), role);
        return role;
    }

    @Override
    public Role deleteRole(long id) {

        EntityManager em = emf.createEntityManager();
        Role toBeRemoved = null;
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
    public Role editRole(Role role) {
        Utility.merge(getEntityManager(), role);
        return role;
    }

    /**
     * Find Person by Id
     *
     * @param id Identity Id
     * @return Person with given Id
     */
    private Role find(Long id) {
        EntityManager em = getEntityManager();
        return em.find(Role.class, id);
    }

}
