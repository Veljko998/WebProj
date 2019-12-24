package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Korisnici;
import model.Korisnik;

@Path("/data")  
public class GetData {
	/*
	@GET
	@Path("/getallusers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getKorisnici() {
		Korisnici k = new Korisnici();
		//Korisnici k = new Korisnici("C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json");
		return k.UpisiKorisnike();
		//return k.UcitajKorisnike();
	}
	*/
}
