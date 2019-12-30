package rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

/** 
 * @author Veljko
 * @since 21.12.2019.
 */
@Path("/webproj")
public class Login {
	
	@GET
	@Path("/login/{param1}/{param2}")
	public Response getMsg(@PathParam("param1") String username, @PathParam("param2") String password) {
 		String output = "Username : " + username + ", Password: " + password + ".";
 		return Response.status(200).entity(output).build();
 	}
	
}
