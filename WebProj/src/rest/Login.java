package rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Korisnik;

/** 
 * @author Veljko
 * @since 21.12.2019.
 */
@Path("/webproj")
public class Login {
	@POST
	@Path("/adduser")
	public void saveStudent(Korisnik selectedUser) {
		System.out.println("saving student: " + selectedUser);
	}
	
}
