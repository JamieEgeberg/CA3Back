package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Book;
import facades.BookFacade;
import facades.IBookFacade;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */

@Path("book")
@RolesAllowed("User")
public class BookResource {

    private IBookFacade facade;
    private EntityManagerFactory emf;
    private static GsonBuilder builder;
    private static Gson gson;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public BookResource() {
        builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gson = builder.create();
        emf = Persistence.createEntityManagerFactory("pu_development");
        facade = new BookFacade(emf);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return gson.toJson(facade.getBooks());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getById(@PathParam("id") int id) {
        return gson.toJson(facade.getBook(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String add(String json) {
        Book book = gson.fromJson(json, Book.class);
        facade.addBook(book);
        return gson.toJson(book);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String edit(String json) {
        Book book = gson.fromJson(json, Book.class);
        facade.editBook(book);
        return gson.toJson(book);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
        return gson.toJson(facade.deleteBook(id));
        //return "{\"message\":\"Deleted book with id: \"" + id + "}";
    }

}
