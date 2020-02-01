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

import model.Disk;
import model.Diskovi;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.kendo.UserToGetData;
import model.kendo.VMToOverview;

/** 
 * @author Veljko
 * @since 09.01.2020.
 */
@Path("/overview")
public class Overview {
	
	//getAllVM
	
	@POST
	@Path("/getAllDiscs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * In case of superadmin we take all Discs.
	 * In case of other users we take just Discs from their organisation.
	 * 
	 * 
	 * @param utgt - email and role as parameters.
	 * @return List of Discs if not empty, otherwise null.
	 */
	public List<Disk> getAllDiscs(UserToGetData utgt){
		List<VirtuelnaMasina> vms = new ArrayList<>();
		List<Disk> diskoviToReturn = new ArrayList<Disk>();
		
		// All of users from korisnici.json
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		// All of organisations from organizacije.json
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		// All of discs from diskovi.json
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		try {
			// If role is SUPERADMIN take all Disck from file.
			if (utgt.role.toLowerCase().equals("superadmin")) {
				for (Disk disk : diskovi.getListaDiskovi()) {
					diskoviToReturn.add(disk);
				}
				
				if (diskoviToReturn.isEmpty()) {
					return null;
				} return diskoviToReturn;
				
			// Takes just disck from utgt's organisation
			}else { // KORISNIK and ADMIN
				Korisnik korisnik = new Korisnik();
				korisnik = korisnici.getMapaKorisnici().get(utgt.email);
				
				if (korisnik.getOrganizacija().getListaResursa() == null || korisnik.getOrganizacija().getListaResursa().isEmpty()) {
					return null;
				}else {
//					vms = getListOfVirtualMachines(korisnik.getOrganizacija().getListaResursa()); //sve virtuelne masine korisnika koji salje zahtev
//					for (VirtuelnaMasina vm : vms) {
//						for (String diskName : vm.getDiskovi()) {
//							diskoviToReturn.add(diskovi.getMapaDiskovi().get(diskName));
//						}
//					}
					for (String resurs : korisnik.getOrganizacija().getListaResursa()) {
						if (diskovi.getMapaDiskovi().containsKey(resurs)) {
							diskoviToReturn.add(diskovi.getDisk(resurs));
						}
					}
					
					if (diskoviToReturn.isEmpty()) { //Ovo mozda i nije potrebno jer smo vec proverili da li korisnik ima listu resursa
						return null;
					}return diskoviToReturn;
				}
			}
		} catch (Exception e) {
			System.out.println("Vraca null sto se ne bi smelo desiti. Overview/getAllVM");
			return null;
		}
	}
	
	@POST
	@Path("/getAllVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Superadmin take all Virtual machines.
	 * Admin and user take VM from theirs organisation
	 * 
	 * @param utgt
	 * @return list of organisations
	 */
	public List<VMToOverview> getAllVM(UserToGetData utgt){
		List<VirtuelnaMasina> vms = new ArrayList<>();
		List<VMToOverview> vmsToReturn = new ArrayList<VMToOverview>();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		/*
		 * Korisnik ima organizaciju koja ima listu imena VM
		 * Superadmin lista sve korisnike, njihove organizacije, i masine I DISKOVE unutar njih
		 * admin i korisnik dobijaju masine iz svojih organizacija.
		 */
//		try { 
			List<String> help = new ArrayList<String>();
			if (utgt.role.toLowerCase().equals("superadmin")) {
				if (virtuelneMasine.getListaVirtuelnihMasina() != null && !virtuelneMasine.getListaVirtuelnihMasina().isEmpty()) {
					for (Korisnik korisnik : korisnici.getListaKorisnici()) {
						for (String vmName : korisnik.getOrganizacija().getListaResursa()) { //kroz listu resursa korisnikove organizacije
							if (!help.contains(vmName) && virtuelneMasine.getMapaVirtuelnihMasina().containsKey(vmName)) {
								VirtuelnaMasina vm = virtuelneMasine.getMapaVirtuelnihMasina().get(vmName);
								VMToOverview vmto = new VMToOverview();
								vmto.ime = vm.getIme();
								vmto.brojJezgara = vm.getBrojJezgara() + "";
								vmto.diskovi = vm.getDiskovi();
								vmto.gpu = vm.getGpu() + "";
								vmto.ram = vm.getGpu() + "";
								vmto.kategorjiaIme = vm.getKategorjia().getIme();
								vmto.organisationName = korisnik.getOrganizacija().getIme();
								System.out.println(korisnik.getOrganizacija().getIme());
								help.add(vmto.ime);
								
								vmsToReturn.add(vmto);
							}
						}
					}
					System.out.println("Broj VM koje vracam prilikom pregleda je: " + vmsToReturn.size());
					return vmsToReturn;
				}else {
					return null;
				}
			}else { // korisnik i admin
				Korisnik korisnik = new Korisnik();
				korisnik = korisnici.getMapaKorisnici().get(utgt.email);
				if (korisnik.getOrganizacija().getListaResursa() == null) {
					return null;
				}else { //vracam listu masina
					for (String vmName : korisnik.getOrganizacija().getListaResursa()) {
						if (virtuelneMasine.getMapaVirtuelnihMasina().containsKey(vmName)) {
							VirtuelnaMasina vm = virtuelneMasine.getMapaVirtuelnihMasina().get(vmName);
							VMToOverview vmto = new VMToOverview();
							vmto.ime = vm.getIme();
							vmto.brojJezgara = vm.getBrojJezgara() + "";
							vmto.diskovi = vm.getDiskovi();
							vmto.gpu = vm.getGpu() + "";
							vmto.ram = vm.getGpu() + "";
							vmto.kategorjiaIme = vm.getKategorjia().getIme();
							vmto.organisationName = korisnik.getOrganizacija().getIme();
							
							vmsToReturn.add(vmto);
						}
					}
					
					return vmsToReturn; 
				}
			}
//		} catch (Exception e) {
//			System.out.println(e);
//			System.out.println("Vraca null sto se ne bi smelo desiti. Overview/getAllVM");
//			return null;
//		}
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
	/**
	 * 
	 * @param role
	 * @param email
	 * @return
	 */
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
			k.UcitajKorisnike();
			Korisnik korisnik = new Korisnik();
			korisnik = k.getMapaKorisnici().get(email);
			
			List<Korisnik> listOfUsersByEmail = new ArrayList<Korisnik>();
			try {
				for (String korEmail : korisnik.getOrganizacija().getListaKorisnika()) {
					listOfUsersByEmail.add(k.getMapaKorisnici().get(korEmail));
				}
				
				return listOfUsersByEmail;
			} catch (NullPointerException e) {
				System.out.println("Ovde ne sme uci: overview/getJustUsers... ->  " + e);
				return null;
			}
		}
		return null;
	}
	
	@GET
	@Path("/getJustOrganisations/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param role
	 * @param email
	 * @return
	 */
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
}
