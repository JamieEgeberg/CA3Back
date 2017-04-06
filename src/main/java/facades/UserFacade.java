package facades;

import entity.Role;
import entity.User;
import security.IUser;
import security.IUserFacade;
import security.PasswordStorage;
import utils.Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserFacade implements IUserFacade {

    EntityManagerFactory emf;
    IRoleFacade roleFacade;

    public UserFacade(EntityManagerFactory emf) {
        this.emf = emf;
        roleFacade = new RoleFacade(emf);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public IUser getUserByUserId(String id) {
        return find(id);
    }

    @Override
    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM SEED_USER u",
                                                User.class);
        return query.getResultList();
    }

    @Override
    public User deleteUser(String id) {
        EntityManager em = emf.createEntityManager();
        User toBeRemoved = null;
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
    public User addUser(User user) {
        try {
            doRoleStuff(user, false);
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            Utility.persist(getEntityManager(), user);
        } catch (PasswordStorage.CannotPerformOperationException ignored) {
        }
        return user;
    }

    @Override
    public User editUser(User user) {
        User oldUser = find(user.getUserName());
        user.setPassword(oldUser.getPassword());
        doRoleStuff(user, true);
        Utility.merge(getEntityManager(), user);
        return user;
    }

    private void doRoleStuff(User user, boolean isEdit) {
        List<Role> roles = roleFacade.getRoles();
        if(isEdit) {
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                List<User> users = role.getUsers();
                for (int j = 0; j < users.size(); j++) {
                    User user2 = users.get(j);
                    if (user2.getUserName().equals(user.getUserName())) {
                        role.getUsers().remove(j);
                    }
                }
            }
        }
        List<Role> newRoles = new ArrayList<>();
        for (int i = 0; i < user.getRoles().size(); i++) {
            for (Role role : roles) {
                if (user.getRoles().get(i).getRoleName().equals(role.getRoleName())) {
                    newRoles.add(role);
                    role.addUser(user);
                    break;
                }
            }
        }
        user.setRoles(newRoles);
    }

    /**
     * Find Person by Id
     *
     * @param id Identity Id
     * @return Person with given Id
     */
    private User find(String id) {
        EntityManager em = getEntityManager();
        return em.find(User.class, id);
    }

    /*
    Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        IUser user = getUserByUserId(userName);
        try {
            return user != null &&
                    PasswordStorage.verifyPassword(password, user.getPassword
                            ()) ? user.getRolesAsStrings() : null;
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE,
                                                             null, ex);
        } catch (PasswordStorage.InvalidHashException ex) {
            System.out.println("Password error");
        }
        return null;
    }

}