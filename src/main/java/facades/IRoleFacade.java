package facades;

import entity.Role;

import java.util.List;

/**
 * Created by Niki on 2017-04-05.
 *
 * @author Niki
 */
public interface IRoleFacade {
    Role getRole(long id);

    List<Role> getRoles();

    Role addRole(Role role);

    Role deleteRole(long id);

    Role editRole(Role role);
}
