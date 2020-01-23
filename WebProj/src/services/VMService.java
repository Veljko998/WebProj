/**
 * 
 */
package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.KategorijeVM;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.Tuple;
import model.VM;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.kendo.VMToAdd;
import model.kendo.VMToDelete;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/VMService")
public class VMService {
	
	@POST
	@Path("/deleteVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteVM(VMToDelete vmtd) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		
		VirtuelnaMasina vm = new VirtuelnaMasina();
		
		if ((vm = virtuelneMasine.getMapaVirtuelnihMasina().get(vmtd.name)) != null) {
			virtuelneMasine.getListaVirtuelnihMasina().remove(vm);
			if (virtuelneMasine.UpisiVirtuelneMasine()) {
				System.out.println("Masina je uspesno obrisana.");
				
				for (Korisnik kor : korisnici.getListaKorisnici()) {
					if (kor.getOrganizacija().getListaResursa().contains(vmtd.name)) {
						kor.getOrganizacija().getListaResursa().remove(vmtd.name);
					}
				}
				
				korisnici.UpisiKorisnike();
				
				return true;
			}
		}
		
		System.out.println("Masina nije uspesno obrisana. Ovde ne bi smeo da udje nikada.  /VMService/deleteVM");
		return false;
	}
	
	@POST
	@Path("/addVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addVM(VMToAdd vma) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		if (virtuelneMasine.getMapaVirtuelnihMasina().get(vma.name) != null) {
			System.out.println("Vec postoji ova virtuelna masina. Ovde ne bi trebalo da udje. Vracamo false.");
			return false;
		}
		
		VM kategorija = kategorijeVM.getMapaKategorijeVM().get(vma.categoryName);
		Organizacija organizacija = organizacije.getMapaOrganizacije().get(vma.organisationName);
		
		/*
		 * U korisnike u organizaciju u listu vm upisujemo ime vm koju dodajemo.
		 */
		for (Korisnik kor : korisnici.getListaKorisnici()) {
			if (kor.getOrganizacija().getIme().equals(organizacija.getIme())) {
				kor.getOrganizacija().getListaResursa().add(vma.name);
			}
		}
		korisnici.UpisiKorisnike();
		
		ArrayList<String> diskovi = (ArrayList<String>)vma.disks;
		
		ArrayList<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti = new ArrayList<Tuple<LocalDateTime,LocalDateTime>>();

		VirtuelnaMasina vMasina = new VirtuelnaMasina(vma.name, kategorija, diskovi, Integer.parseInt(vma.coreNumber), Integer.parseInt(vma.ram), Integer.parseInt(vma.gpu), listaAktivnosti); 
		
		System.out.println(vMasina);
		
		virtuelneMasine.dodajVirtuelnuMasinu(vMasina);
		virtuelneMasine.UpisiVirtuelneMasine();
		
		/*
		 * pristupamo organizaciji koja je odabrana ako je u pitanju superadmin, 
		 * ili organizaciji kojoj priada korisnik ako je admin u pitanju,
		 * i dodajmo u listu resursa(v. masina) naziv Masine koju smo dodali.
		 */
		organizacije.getListaOrganizacije().get(organizacije.getListaOrganizacije().indexOf(organizacija)).getListaResursa().add(vma.name);
		organizacije.UpisiOrganizacije();
		
		return true;
	}
	
	@GET
	@Path("/checkIfVMExist/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean checkIfVMExist(@PathParam("param1") String vmName){
		VirtuelneMasine vms = new VirtuelneMasine();
		vms.setPutanja();
		vms.UcitajVirtuelneMasine();
		
		if (vms.getMapaVirtuelnihMasina().containsKey(vmName)) {
			return true;
		} return false;
	}
}
