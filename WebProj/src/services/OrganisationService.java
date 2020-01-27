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
}
