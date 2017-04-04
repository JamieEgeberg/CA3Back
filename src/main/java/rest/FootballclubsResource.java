/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
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
@Path("footballclubs")
@RolesAllowed("User")
public class FootballclubsResource {

    Gson gson = new Gson();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FootballclubsResource
     */
    public FootballclubsResource() {
    }

    /**
     * Retrieves representation of an instance of rest.FootballclubsResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "[{\"name\":\"Liverpool\", \"url\":\"http://www.liverpoolfc.com\"},\n"
                + " {\"name\":\"Manchester United\",\"url\" : \"http://www.manutd.com/\"}]";
    }

    /**
     * PUT method for updating or creating an instance of FootballclubsResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
