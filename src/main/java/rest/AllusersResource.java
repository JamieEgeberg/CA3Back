/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jamie
 */
@Path("allusers")
@RolesAllowed("Admin")
public class AllusersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AllusersResource
     */
    public AllusersResource() {
    }

    /**
     * Retrieves representation of an instance of rest.AllusersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "[{\"name\": \"Jan\",\"mail\":\"j@a.dk\"},"
                + "{\"name\":\"Ann\",\"mail\":\"a@a.dk\"},"
                + "{\"name\":\"Ib\",\"mail\":\"i@a.dk\"}]";
    }

    /**
     * PUT method for updating or creating an instance of AllusersResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
