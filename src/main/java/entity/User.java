package entity;

import com.google.gson.annotations.Expose;
import security.IUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "SEED_USER")
public class User implements IUser, Serializable{
 
  //You will need to change this to save a Hashed/salted password
  @Expose(serialize = false)
  @Column(length = 255, name = "PASSWORD_HASH",nullable = false)
  private String passwordHash;

  @Expose
  @Id
  @Column(length = 35, name = "USER_NAME",nullable = false)
  private String userName;

  @Expose
  @ManyToMany
  List<Role> roles;
 
  public User() {
  }

  public User(String userName, String password) {
    this.userName = userName;
    this.passwordHash = password;
  }
  
  
  public void addRole(Role role){
    if(roles == null){
      roles = new ArrayList();
    }
    roles.add(role);
    role.addUser(this);
  }
  
  public List<Role> getRoles(){
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
    
  @Override
  public List<String> getRolesAsStrings() {
   if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
  }
 
  @Override
  public String getPassword() {
    return passwordHash;
  }
  

  public void setPassword(String password) {
    this.passwordHash = password;
  }

  @Override
  public String getUserName() {
    return userName;
  }
     
}