package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.Diskovi;
import model.KategorijeVM;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.Tuple;
import model.VM;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.enums.TipDiska;
import model.enums.Uloga;

@Path("/data")  
public class GetData {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/make")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getKorisnici() {
		Korisnici k = new Korisnici();
		k.setPutanja();
		List<Tuple<LocalDateTime, LocalDateTime>> lista = new ArrayList<Tuple<LocalDateTime, LocalDateTime>>();
		//Korisnici k = new Korisnici("C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json");
		Korisnik k1 = new Korisnik("email1", "lozinka1", "ime1", "prezime1",
				new Organizacija(), Uloga.KORISNIK,
				lista);
		Korisnik k2 = new Korisnik("email2", "lozinka2", "ime2", "prezime2",
				new Organizacija(), Uloga.KORISNIK,
				lista);
		k.dodajKorisnika(k1);
		k.dodajKorisnika(k2);
		k.UpisiKorisnike();
		return k.UcitajKorisnike();
		//return k.UpisiKorisnike(); 
		/*
		Diskovi d = new Diskovi();
		d.setPutanja();
		Disk d1 = new Disk("disk1", TipDiska.HDD, 1, "vm1" );
		Disk d2 = new Disk("disk2", TipDiska.SSD, 2, "vm2");
		d.dodajDisk(d1);
		d.dodajDisk(d2);
		d.UpisiDiskove();
		return d.UcitajDiskove();
		*/
		/*
		Organizacije o = new Organizacije();
		ArrayList<String> lista = new ArrayList<String>();
		o.setPutanja();
		Organizacija o1 = new Organizacija("org1", "opis1", "logoputanja1",lista, lista);
		Organizacija o2 = new Organizacija("org2", "opis2", "logoputanja2",lista, lista);
		o.dodajOrganizaciju(o1);
		o.dodajOrganizaciju(o2);
		o.UpisiOrganizacije();
		return o.UcitajOrganizacije();*/
		/*
		KategorijeVM k = new KategorijeVM();
		k.setPutanja();
		VM v1 = new VM("vm1", 1, 1, 1);
		VM v2 = new VM("vm2", 2, 2, 2);
		k.dodajKategorijuVM(v1);
		k.dodajKategorijuVM(v2);
		k.UpisiKategorijeVM();
		k.UcitajKategorijeVM();
		*/
		/*
		VirtuelneMasine m = new VirtuelneMasine();
		m.setPutanja();
		ArrayList<String> lista = new ArrayList<String>();
		VirtuelnaMasina vm1 = new VirtuelnaMasina("vm1", new VM("vm1", 1, 1, 1), lista, 1, 2, 3);
		VirtuelnaMasina vm2 = new VirtuelnaMasina("vm2", new VM("vm1", 1, 1, 1), lista, 1, 2, 3);
		m.dodajVirtuelnuMasinu(vm1);
		m.dodajVirtuelnuMasinu(vm2);
		m.UpisiVirtuelneMasine();
		return m.UcitajVirtuelneMasine();
		*/
	}
	
	@GET
	@Path("/getOrganisations/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Organizacija> getOrganisations(@PathParam("param1") String uloga, @PathParam("param2") String mejl){
		Organizacije o = new Organizacije();
		o.setPutanja(); 
	
		if (o.UcitajOrganizacije()) {
			//return o.getListaOrganizacije();
		
			switch(uloga){
			case "superadmin": 
				return o.getListaOrganizacije();
			case "admin": 
				Korisnici k = new Korisnici();
				k.setPutanja();
				Korisnik korisnik = k.getMapaKorisnici().get(mejl);
				ArrayList<Organizacija> lista = new ArrayList<Organizacija>();
				lista.add(korisnik.getOrganizacija());
				return lista;
			case "korisnik": 
				return null;
			default: break;
			}
		}else {
			System.out.println("Nije ucitao ni jendnu organizaciju.");
		}
		return null;
	}
	/*
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
	}*/
	
}
