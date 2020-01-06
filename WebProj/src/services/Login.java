package services;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import model.Korisnici;
import model.Korisnik;
import model.enums.Uloga;
import model.kendo.UserToVerify;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/** 
 * @author Veljko
 * @since 21.12.2019.
 */
@Path("/webproj")
public class Login {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/login/{param1}/{param2}")
	public Response login(@PathParam("param1") String username, @PathParam("param2") String password) {
 		String output = "Username : " + username + ", Password: " + password + ".";
 		return Response.status(200).entity(output).build();
 	}

	@GET
	@Path("/getJustUsers")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Return all users from korisnici.json.
	 * 
	 * Ovu metodu necu koristiti za logovanje. Metoda je namenjena za one koji ce se baviti korisnicima
	 * kao sto su to admin i superadmin.
	 * 
	 * @return List<Korisnik>4
	 */
	public List<Korisnik> getJustUsers(){
		Korisnici k = new Korisnici();
		k.setPutanja();  //set .json files path before reading from them.
		if (k.UcitajKorisnike()) {
			return k.getListaKorisnici();
		}else {
			System.out.println("Nije ucitao ni jendog korisnika.");
		}
		return null;
	}
	
	@POST
	@Path("/verifyUser")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Method which check are username(email) and password correct.
	 * 
	 * @param u is UserToVerify object with email and password as attributes.
	 * @return Boolean
	 */
	public boolean verifyUser(UserToVerify u) {
		Korisnici k = new Korisnici();
		k.setPutanja(); 
		// TODO: Add verification for password, because we don't have password attribute in Korisnik.
		if (k.UcitajKorisnike()) {
			for (Korisnik korisnik : k.getListaKorisnici()) {
				if (korisnik.getEmail().equals(u.email)) {
					System.out.println("ima korisnik sa tim mejlom.");
					return true;
				}
			}
			System.out.println("Nema korisnik sa tim email-om.");
			return false;
		}
		return false;
	}
	@POST
	@Path("/checkRole")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Check for user's role.
	 * 
	 * @param u
	 * @return user's role as string
	 */
	public String checkRole(UserToVerify u) {
		Korisnici k = new Korisnici();
		k.setPutanja();
		if (k.UcitajKorisnike()) {
			for (Korisnik korisnik : k.getListaKorisnici()) {
				if (korisnik.getEmail().equals(u.email)) {
					System.out.println("Uloga: " + korisnik.getUloga().name().toLowerCase());
					return korisnik.getUloga().name().toLowerCase();
				}
			}
		}
		return null;
	}
	
}
