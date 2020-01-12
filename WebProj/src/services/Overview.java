/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.kendo.UserToGetData;

/** 
 * @author Veljko
 * @since 09.01.2020.
 */
@Path("/overview")
public class Overview {
	
	//getAllVM
	
	@POST
	@Path("/getAllVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Superadmin take all Virtual machines from organisation from all users.
	 * Admin and user take VM from theirs organisation
	 * 
	 * @param utgt
	 * @return list of organisations
	 */
	public List<VirtuelnaMasina> getAllVM(UserToGetData utgt){
		List<VirtuelnaMasina> orgs = new ArrayList<>();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		try {
			if (utgt.role.toLowerCase().equals("superadmin")) {
				for (Korisnik kor : korisnici.getListaKorisnici()) {
					if (kor.getOrganizacija().getListaResursa() != null) {
						for (VirtuelnaMasina virtuelnaMasina : getListOfVirtualMachines(kor.getOrganizacija().getListaResursa())) {
							orgs.add(virtuelnaMasina);
						}
					}
				}
				if (orgs.isEmpty()) {
					return null;
				} return orgs;
			}else { // korisnik i admin
				Korisnik korisnik = new Korisnik();
				korisnik = korisnici.getMapaKorisnici().get(utgt.email);
				if (korisnik.getOrganizacija().getListaResursa() == null) {
					return null;
				}else {
					orgs = getListOfVirtualMachines(korisnik.getOrganizacija().getListaResursa());
					return orgs;
				}
				
				
			}
		} catch (Exception e) {
			System.out.println("Vraca null sto se ne bi smelo desiti. Overview/getAllVM");
			return null;
		}
	}
	
	
	public List<VirtuelnaMasina> getListOfVirtualMachines(List<String> listaResursa){
		List<VirtuelnaMasina> vmsList = new ArrayList<VirtuelnaMasina>();
		HashMap<String, VirtuelnaMasina> vmsMap = new HashMap<>();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		vmsMap = virtuelneMasine.getMapaVirtuelnihMasina();
		for (String nameOfVM : listaResursa) {
			vmsList.add(vmsMap.get(nameOfVM));
		}
		
		return vmsList;
	}
	
	
	
	@GET
	@Path("/getJustUsers/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Korisnik> getJustUsers(@PathParam("param1") String role, @PathParam("param2") String email){
		
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
//				if (listOfOrganisationsByIdName.isEmpty()) {
//					System.out.println("Lista organizacije za admina je prazna...");
//				}else if (listOfOrganisationsByIdName.size() == 1) {
//					System.out.println("Ima tacno jedna organizacija...");
//				}
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
