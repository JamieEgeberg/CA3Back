package facades;

import entity.User;
import security.IUser;
import security.IUserFacade;
import security.PasswordStorage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserFacade implements IUserFacade {

  EntityManagerFactory emf;

  public UserFacade(EntityManagerFactory emf) {
    this.emf = emf;   
  }

  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  @Override
  public IUser getUserByUserId(String id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(User.class, id);
    } finally {
      em.close();
    }
  }

  /*
  Return the Roles if users could be authenticated, otherwise null
   */
  @Override
  public List<String> authenticateUser(String userName, String password) {
    IUser user = getUserByUserId(userName);
      try {  
          return user != null &&  PasswordStorage.verifyPassword(password, user.getPassword()) ? user.getRolesAsStrings() : null;
      } catch (PasswordStorage.CannotPerformOperationException ex) {
          Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
      } catch (PasswordStorage.InvalidHashException ex) {
          System.out.println("Password error");
      }
      return null;
  }



}