package rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/** 
 * @author Veljko
 * @since 21.12.2019.
 */
@Path("/user")
public class Login {
	@POST
	@Path("/login")
	public Response logIn(
		@FormParam("username") String username,
		@FormParam("password") String password,
		@FormParam("remember") Boolean remember) {
		return Response.status(200).entity("Username: " + username + ", i sifra i pamcenje").build();
	}
	
}
