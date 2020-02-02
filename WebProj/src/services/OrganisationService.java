/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.Diskovi;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.enums.Uloga;
import model.kendo.OrganisationDetails;
import model.kendo.OrganisationToAdd;
import model.kendo.OrganisationToEdit;
import model.kendo.UserToAdd;
import model.kendo.VMToDelete;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/organisationService")
public class OrganisationService {

	@GET
	@Path("/getOrganisationByName/{param1}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @return list of names of all organisations.
	 */
	public Organizacija getOrganisationByName(@PathParam("param1") String name) {
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		return organizacije.getOrganizacija(name);
	}
	
	@POST
	@Path("/getAllOrganisations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @return list of names of all organisations.
	 */
	public List<String> getAllOrganisations() {
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		try {
			return new ArrayList<String>(organizacije.getMapaOrganizacije().keySet());
		} catch (Exception e) {
			System.out.println("Neuspesno vracanje liste imena organizacija. /organisationService/getAllOrganisations");
			return null;
		}
	}
	
	@GET
	@Path("/getOrganisations/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Organizacija> getOrganisations(@PathParam("param1") String uloga, @PathParam("param2") String mejl){
		Organizacije o = new Organizacije();
		o.setPutanja(); 
	
		if (o.UcitajOrganizacije()) {
			switch(uloga){
			case "superadmin": 
				return o.getListaOrganizacije();
			case "admin": 
				Korisnici k = new Korisnici();
				k.setPutanja();
				if(k.UcitajKorisnike()){
					Korisnik korisnik = k.getMapaKorisnici().get(mejl);
					ArrayList<Organizacija> lista = new ArrayList<Organizacija>();
					lista.add(korisnik.getOrganizacija());
					return lista;
				}
				break;
			case "noRole": 
				break;
			default: break;
			}
		}else {
			System.out.println("Nije ucitao ni jendnu organizaciju.");
		}
		return null;
	}
	
	@GET
	@Path("/getResources/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getResources(@PathParam("param1") String uloga, @PathParam("param2") String mejl){
		Diskovi d = new Diskovi();
		d.setPutanja();
		VirtuelneMasine vm = new VirtuelneMasine();
		vm.setPutanja();
		ArrayList<String> resources = new ArrayList<String>();
		
		if(d.UcitajDiskove()){
			for(Disk disk : d.getListaDiskovi()){
				resources.add(disk.getIme());
			}
		}
		
		if(vm.UcitajVirtuelneMasine()){
			for(VirtuelnaMasina virtm : vm.getListaVirtuelnihMasina()){
				resources.add(virtm.getIme());
			}
		}
		return resources;
	}
	
	@POST
	@Path("/getOrganisation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Organizacija getOrganisation(OrganisationDetails org) {
		Organizacije o = new Organizacije();
		o.setPutanja();
		if(o.UcitajOrganizacije()){
			Organizacija novaOrg = new Organizacija();
			if((novaOrg = o.getMapaOrganizacije().get(org.name)) != null){
				return novaOrg;
			}
		}
		return null;
	}
	
	@GET
	@Path("/organisationAlreadyExists/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean organisationAlreadyExsists(@PathParam("param1") String imeOrg){
		Organizacije o = new Organizacije();
		o.setPutanja();
		if(o.UcitajOrganizacije()){
			if(o.getMapaOrganizacije().containsKey(imeOrg)){
				return true;
			}
		}
		return false;
	}
	
	@POST
	@Path("/addOrganisation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addOrganisation(OrganisationToAdd newOrg) {
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		
		if(!organizacije.UcitajOrganizacije()){
			return false;
		}
		ArrayList<String> listaKorisnika = new ArrayList<String>();
		ArrayList<String> listaResursa = new ArrayList<String>();
		String logo = "logos/default.jpg";
		
		if(newOrg.logo != null){
			//ovde se namesta druga slika
		}
		
		if (newOrg.details == null) {
			newOrg.details = null;
		}
		
		Organizacija org = new Organizacija(newOrg.name, newOrg.details, logo, listaKorisnika, listaResursa);
				
		organizacije.dodajOrganizaciju(org);
		organizacije.UpisiOrganizacije();
		return true;
	}
	
	@POST
	@Path("/editOrganisation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param ote
	 * @return true if organisation is edited successfully.
	 */
	public boolean editOrganisation(OrganisationToEdit ote) {
		if (ote.details == null) {
			
		}else if (ote.details.equals("")) {
			ote.details = null;
		}
		
		if (ote.logo == null || ote.logo.equals("")) {
			ote.logo = "logos/default.jpg";
		}
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		if(!korisnici.UcitajKorisnike()){
			System.out.println("nije");
			return false;
		}
		
		Organizacija oldOrg = new Organizacija();
		oldOrg = organizacije.getMapaOrganizacije().get(ote.oldName);
		String oldName = oldOrg.getIme();
		
		Organizacija orgToAdd = new Organizacija(ote.name, ote.details, ote.logo, oldOrg.getListaKorisnika(), oldOrg.getListaResursa());
		
		organizacije.getListaOrganizacije().remove(oldOrg);
		organizacije.getListaOrganizacije().add(orgToAdd);
		organizacije.getMapaOrganizacije().remove(ote.oldName);
		organizacije.getMapaOrganizacije().put(orgToAdd.getIme(), orgToAdd);
		organizacije.UpisiOrganizacije();
		
		for (Korisnik kor : korisnici.getListaKorisnici()) {
			if (kor.getOrganizacija().getIme().equals(oldName)) {
				kor.setOrganizacija(orgToAdd);
			}
		}
		
		korisnici.UpisiKorisnike();
		return true;
	}
}
