/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Korisnici;
import model.Korisnik;

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
			return getListOfUsersByEmail(korisnik.getOrganizacija().getListaKorisnika());
		}
		
		return null;
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
		return korList;
	}
}
