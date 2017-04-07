package security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import facades.UserFacade;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Path("login")
public class Login {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String gt() {
    return "{\"txt\" : \"TEST\"}";
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(String jsonString) throws JOSEException {
    try {
      JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
      String username = json.get("username").getAsString();
      String password = json.get("password").getAsString();
      JsonObject responseJson = new JsonObject();
      List<String> roles;

      if ((roles = authenticate(username, password)) != null) {
        String token = createToken(username, roles);
        responseJson.addProperty("username", username);
        responseJson.addProperty("token", token);
        return Response.ok(new Gson().toJson(responseJson)).build();
      }
    } catch (Exception e) {
      if (e instanceof JOSEException) {
        throw e;
      }
    }
    throw new NotAuthorizedException("Invalid username or password. Please try again", Response.Status.UNAUTHORIZED);
  }

  private List<String> authenticate(String userName, String password) {
    IUserFacade facade = UserFacadeFactory.getInstance();
    return facade.authenticateUser(userName, password);
  }

  private String createToken(String subject, List<String> roles) throws JOSEException {
    StringBuilder res = new StringBuilder();
    for (String string : roles) {
      res.append(string);
      res.append(",");
    }
    String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    String issuer = "semester3demo-cphbusiness.dk-computerScience";

    
    if (Secret.SHARED_SECRET == null) {
      /*
       A (much) better solution would be to have a fixed "secret" stored somewhere safe. 
       This solution will render all issued tokens invalid, if the server is restarted
       It's done like this, to force you to know what you do. If you have a "fixed" secret, stored in a 
       public Git repository, you have NO security at all.
      */
      
      // Generate random 256-bit (32-byte) shared secret
      SecureRandom random = new SecureRandom();
      Secret.SHARED_SECRET = new byte[32];
      random.nextBytes(Secret.SHARED_SECRET);


      EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_development");
      IUserFacade facade = new UserFacade(emf);
      if(facade.getUserByUserId("Anne") == null) {
        utils.makeTestUsers.cheatingStuff(); // add the damn users and stuff
      }
    }
    JWSSigner signer = new MACSigner(Secret.SHARED_SECRET);
    Date date = new Date();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(subject)
            .claim("username", subject)
            .claim("roles", roles)
            .claim("issuer", issuer)
            .issueTime(date)
            .expirationTime(new Date(date.getTime() + 1000 * 60 * 60))
            .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    return signedJWT.serialize();
  }
}
