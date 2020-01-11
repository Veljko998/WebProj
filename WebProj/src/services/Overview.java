/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;

/** 
 * @author Veljko
 * @since 09.01.2020.
 */
@Path("/overview")
public class Overview {
	
	@GET
	@Path("/getJustUsers/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getJustUsers(@PathParam("param1") String role, @PathParam("param2") String email){
		System.out.println(role);
		System.out.println(email);
		
		Korisnici k = new Korisnici();
		k.setPutanja();
		
		if (role.equals("superadmin")) {
			  //set .json files path before reading from them.
			if (k.UcitajKorisnike()) {
				return k.getListaKorisnici();
			}else {
				System.out.println("Nije ucitao ni jendog korisnika.");
			}
		}else if (role.equals("admin")) { //Vracamo samo korisnike iz njegove organizacije
			Korisnik korisnik = new Korisnik();
			korisnik = k.getMapaKorisnici().get(email);
			
			List<Korisnik> listOfUsersByEmail = new ArrayList<Korisnik>();
			try {
				listOfUsersByEmail = getListOfUsersByEmail(korisnik.getOrganizacija().getListaKorisnika());
				return listOfUsersByEmail;
			} catch (NullPointerException e) {
				return null;
			}
		}
		return null;
	}
	
	@GET
	@Path("/getJustOrganisations/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Organizacija> getJustOrganisations(@PathParam("param1") String role, @PathParam("param2") String email){
		Korisnici k = new Korisnici();
		k.setPutanja();
		k.UcitajKorisnike();
		
		Organizacije orgs = new Organizacije();
		orgs.setPutanja();
		
		if (role.equals("superadmin")) {
			  //set .json files path before reading from them.
			if (orgs.UcitajOrganizacije()) {
				return orgs.getListaOrganizacije();
			}else {
				System.out.println("Nije ucitao ni jednu organizaciju.");
			}
		}else if (role.equals("admin")) { //Vracamo samo korisnike iz njegove organizacije
			Korisnik korisnik = new Korisnik();
			korisnik = k.getMapaKorisnici().get(email);
			
			List<Organizacija> listOfOrganisationsByIdName = new ArrayList<Organizacija>();
			try {
				listOfOrganisationsByIdName = getListOfOrganisationsByIdName(email, korisnik);
				if (listOfOrganisationsByIdName.isEmpty()) {
					System.out.println("Lista organizacije za admina je prazna...");
				}else if (listOfOrganisationsByIdName.size() == 1) {
					System.out.println("Ima tacno jedna organizacija...");
				}
				System.out.println("Ovo vracam: " + listOfOrganisationsByIdName.get(0).getIme());
				return listOfOrganisationsByIdName;
			} catch (NullPointerException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * @param email
	 * @param korisnik
	 * @return list of just one organisation from user's email.
	 */
	private List<Organizacija> getListOfOrganisationsByIdName(String email, Korisnik korisnik) {
		List<Organizacija> listaOrg = new ArrayList<Organizacija>();
		listaOrg.add(korisnik.getOrganizacija());
		System.out.println("Velicina liste kkorisnika: " + listaOrg.size());
		
		return listaOrg;
	}

	/**
	 * @param listaKorisnika
	 * @return
	 */
	private List<Korisnik> getListOfUsersByEmail(List<String> listaKorisnika) {
		Korisnici k = new Korisnici();
		List<Korisnik> korList = new ArrayList<Korisnik>();
		for (Korisnik kor1 : k.getListaKorisnici()) {
			for (String kor2 : listaKorisnika) {
				if (kor1.getEmail().equals(kor2)) {
					korList.add(kor1);
				}
			}
		}
		if (korList.isEmpty()) {
			return null;
		}
		return korList;
	}
}
