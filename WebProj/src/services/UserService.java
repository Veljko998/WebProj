/**
 * 
 */
package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.enums.Uloga;
import model.kendo.UserToAdd;
import model.kendo.UserToEdit;
import model.kendo.UserToGetData;
import model.kendo.UserToRegister;

/** 
 * @author Veljko
 * @since 12.01.2020.
 */
@Path("/userService")
public class UserService {
	
	@POST
	@Path("/editUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param ute
	 * @return
	 */
	public boolean editUser(UserToEdit ute) {
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();

		if (!ute.password.equals(ute.passwordToConfirm)) {
			System.out.println("Nemoguce... //editUser");
			return false;
		}
		for (Korisnik korisnik : korisnici.getListaKorisnici()) {
			if (korisnik.getEmail().equals(ute.oldEmail)) {
				korisnik.setIme(ute.name);
				korisnik.setLozinka(ute.password);
				korisnik.setPrezime(ute.surname);
				korisnik.setUloga(Uloga.valueOf(ute.role));
				korisnici.UpisiKorisnike();
				break;
			}
		}
		return true;
	}
	
	@GET
	@Path("/getPassword/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param mejl
	 * @return password of user with forwarded email.
	 */
	public String getPassword(@PathParam("param1") String mejl) {
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();

		try {
			return korisnici.getMapaKorisnici().get(mejl).getLozinka();
		} catch (Exception e) {
			System.out.println("Ovde ne ulazi. userService/getPassword");
			return null;
		}
		
	}
	
	@GET
	@Path("/deleteUser/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Zapamtimo ime organizacije korisnika
	 * Brisemo korisnika iz korisnika
	 * Brisemo mejl korisnika iz liste korisnike u organizaciji koja je korisnikova
	 * 
	 * @param mejl
	 * @return true if user is deleted.
	 */
	public boolean deleteUser(@PathParam("param1") String mejl) {
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();

		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		Organizacija org = korisnici.getKorisnik(mejl).getOrganizacija();

		Korisnik userToDel = korisnici.getKorisnik(mejl);
		
		korisnici.getListaKorisnici().remove(userToDel);
		korisnici.UpisiKorisnike();
		
		for (Organizacija organizacija : organizacije.getListaOrganizacije()) {
			for (String korIme : organizacija.getListaKorisnika()) {
				if (korIme.equals(mejl)) {
					organizacija.getListaKorisnika().remove(mejl);
				}
			}
		}
		organizacije.UpisiOrganizacije();
		
		return true;
	}
	
	@POST
	@Path("/getUserOrganisationName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param ugt - attributes are role and name
	 * @return name of users organisation
	 */
	public String getUsersOrganisationName(UserToGetData ugt) {
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		System.out.println(ugt.email);
		
		try {
			System.out.println("ime organizacije koju vracam je: " + korisnici.getMapaKorisnici().get(ugt.email).getOrganizacija().getIme());
			return korisnici.getMapaKorisnici().get(ugt.email).getOrganizacija().getIme();
		} catch (Exception e) {
			System.out.println("Neuspesno vracanje naziva organizacije admina.");
			return null;
		}
	}
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	/**
	 * Consumes - If a resource is unable to consume the MIME type of a client request, 
	 * the Jersey runtime sends back an HTTP “415 Unsupported Media Type” error.
	 * 
	 * @param utr
	 * @return true if user is written in
	 */
	public boolean registerUser(UserToRegister utr) {
		try {
			Korisnici korisnici = new Korisnici();
			korisnici.setPutanja();
			korisnici.UcitajKorisnike();
			
			
			Organizacije organizacije = new Organizacije();
			organizacije.setPutanja();
			organizacije.UcitajOrganizacije();

			Organizacija organizacija = organizacije.getMapaOrganizacije().get(utr.organisationName);

			Korisnik korisnik = new Korisnik();
			korisnik.setEmail(utr.email);
			korisnik.setIme(utr.name);
			korisnik.setLozinka(utr.password);
			korisnik.setPrezime(utr.surname);
			korisnik.setUloga(Uloga.valueOf(utr.role.toUpperCase()));
			korisnik.setOrganizacija(organizacija);

			if (korisnici.dodajKorisnika(korisnik)) {
				/*
				 * First handle organisation then user
				 */
				organizacije.getMapaOrganizacije().get(utr.organisationName).getListaKorisnika().add(utr.email);
				organizacije.UpisiOrganizacije();
				
				korisnici.UpisiKorisnike();
				System.out.println("Korisnik je uspesno upisan");
				return true;
			} 
			return false;
		} catch (Exception e) {
			System.out.println("Something went wrong in UserService/registerUser. returnniing false.");
			System.out.println(e);
			return false;
		}
	}
	
	@GET
	@Path("/getUser/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @param mejl
	 * @return user by mail
	 */
	public Korisnik getUser(@PathParam("param") String mejl){
		Korisnici k = new Korisnici();
		k.setPutanja();
		
		if(k.UcitajKorisnike()){
			return k.getKorisnik(mejl);
		}
		return null;
	}
	
	
	
	@GET
	@Path("/userAlreadyExists/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean userAlreadyExists(@PathParam("param1") String mejl, @PathParam("param2") String unetiMejl){
		if(mejl.equals(unetiMejl)){
			return false; //korisnik je ostavio svoj mejl
		}
		Korisnici k = new Korisnici();
		k.setPutanja();
		
		if(k.UcitajKorisnike()){
			if(k.getMapaKorisnici().containsKey(unetiMejl)){
				return true;
			}
		}
		return false;
		
	}
	
	@POST
	@Path("/addUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addUser(UserToAdd newUser) {
		Korisnici k = new Korisnici();
		k.setPutanja();
		if(!k.UcitajKorisnike()){
			return false;
		}
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		
		if(!organizacije.UcitajOrganizacije()){
			return false;
		}
		
		Organizacija organizacija = organizacije.getMapaOrganizacije().get(newUser.organisationName);
		Korisnik noviKorisnik = new Korisnik(newUser.email, newUser.password, newUser.name, newUser.surname, organizacija, Uloga.valueOf(newUser.role.toUpperCase()));
		
		k.dodajKorisnika(noviKorisnik, newUser.previousEmail);
		k.UpisiKorisnike();
		
		return true;
	}
	
	@GET
	@Path("/test/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @param mejl
	 * @return user by mail
	 */
	public Organizacija test(@PathParam("param") String mejl){
		Korisnici k = new Korisnici();
		k.setPutanja();
		
		if(k.UcitajKorisnike()){
			String ime = k.getKorisnik(mejl).getOrganizacija().getIme();
			Organizacije o = new Organizacije();
			o.setPutanja();
			if(o.UcitajOrganizacije()){
				Organizacija novaOrg = new Organizacija();
				if((novaOrg = o.getMapaOrganizacije().get(ime)) != null){
					return novaOrg;
				}
			}
			return null;
			
		}
		return null;
	}
	
}
