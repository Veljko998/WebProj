package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Korisnik;

public class GetData {
	@GET
	@Path("/getallusers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getKorisnici() {
		
	}
}
