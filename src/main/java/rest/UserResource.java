package rest;

import com.google.gson.Gson;
import facades.IRoleFacade;
import facades.RoleFacade;
import facades.UserFacade;
import security.IUserFacade;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Niki on 2017-04-05.
 *
 * @author Niki
 */
@Path("user")
public class UserResource {

    private IUserFacade facade;
    private IRoleFacade roleFacade;
    private EntityManagerFactory emf;
    private static Gson gson = new Gson();

    /**
     * Creates a new instance of PersonResource
     */
    public UserResource() {
        emf = Persistence.createEntityManagerFactory("pu_development");
        facade = new UserFacade(emf);
        roleFacade = new RoleFacade(emf);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return gson.toJson(facade.getUsers());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getById(@PathParam("id") String id) {
        return gson.toJson(facade.getUserByUserId(id));
    }

    @GET
    @Path("roles")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRoles() {
        return gson.toJson(roleFacade.getRoles());
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String add(String json) {
        entity.User user = gson.fromJson(json, entity.User.class);
        facade.addUser(user);
        return gson.toJson(user);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String edit(String json) {
        entity.User user = gson.fromJson(json, entity.User.class);
        facade.editUser(user);
        return gson.toJson(user);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        return gson.toJson(facade.deleteUser(id));
        //return "{\"message\":\"Deleted book with id: \"" + id + "}";
    }

}
