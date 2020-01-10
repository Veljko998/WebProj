package services;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Korisnici;
import model.Korisnik;
import model.kendo.LoginToEnsure;
import model.kendo.PathToLimit;
import model.kendo.UserToVerify;

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
	@Path("/getJustUsers2/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getJustUsers2(@PathParam("param1") String role, @PathParam("param2") String email){
		System.out.println(role);
		System.out.println(email);
		
		Korisnici k = new Korisnici();
		k.setPutanja();  //set .json files path before reading from them.
		if (k.UcitajKorisnike()) {
			return k.getListaKorisnici();
		}else {
			System.out.println("Nije ucitao ni jendog korisnika.");
		}
		return null;
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

	@GET
	@Path("/ensureLogin")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * This method should check is user logged in, to secure going to other page if not.
	 * 
	 * @return true if user is logged in correctly, or logged in at all.
	 */
	public boolean ensureLogin(){
		LoginToEnsure lte = new LoginToEnsure();
		//TODO: Implement logic.
		
		
		return !lte.isLogedIn; //return true initially while not implement logic.
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
	 * @param u - UserToVerify
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
	
	@POST
	@Path("/checkPath")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * If user is logged in limit his path. For example: If user's role is admin, he can go on .admin/..., but he can't go to /korisnik...
	 * 
	 * @param u
	 * @return users role if user is logged in, or null otherwise.
	 */
	public String checkPath(PathToLimit ptl) {
		System.out.println("Ovo je putanja: " + ptl.path);
		if (ptl.path.startsWith("/korisnik")) return "korisnik"; 
		else if (ptl.path.startsWith("/admin")) return "admin"; 
		else if (ptl.path.startsWith("/superadmin")) return "superadmin"; 
		else return "null"; 
	}
	
}
