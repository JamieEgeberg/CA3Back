/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entity.Role;
import security.PasswordStorage;
import utils.Utility;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author plaul1
 */
@Path("demoall")
public class All {

  private EntityManagerFactory emf;

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of A
   */
  public All() {
    emf = Persistence.createEntityManagerFactory("pu_development");
  }

  /**
   * Retrieves representation of an instance of rest.All
   * @return an instance of java.lang.String
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getText() {
    return " {\"message\" : \"result for all\"}";
  }


  @GET
  @Path("hack")
  public String hack() {
    try {
      Role userRole = new Role("User");
      Role adminRole = new Role("Admin");
      Utility.persist(emf.createEntityManager(), userRole);
      Utility.persist(emf.createEntityManager(), adminRole);
      entity.User peter = new entity.User("Peter", PasswordStorage
              .createHash("test"));
      peter.addRole(userRole);
      entity.User anne = new entity.User("Anne", PasswordStorage
              .createHash("test"));
      anne.addRole(adminRole);
      Utility.persist(emf.createEntityManager(), peter);
      Utility.persist(emf.createEntityManager(), anne);
      return "Success";
    } catch (Exception ignored) {
      return "Failed";
    }
  }

}
