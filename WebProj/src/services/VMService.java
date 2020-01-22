/**
 * 
 */
package services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.KategorijeVM;
import model.Organizacija;
import model.Organizacije;
import model.VM;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.kendo.VMToAdd;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/VMService")
public class VMService {
	
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
		
		if (virtuelneMasine.getMapaVirtuelnihMasina().get(vma.name) != null) {
			System.out.println("Vec postoji ova virtuelna masina. Ovde ne bi trebalo da udje. Vracamo false.");
			return false;
		}
		
		VM kategorija = kategorijeVM.getMapaKategorijeVM().get(vma.categoryName);
		Organizacija organizacija = organizacije.getMapaOrganizacije().get(vma.organisationName);
		//TODO: diskove trebam da ubacim u lsitu.
		ArrayList<String> diskovi = (ArrayList<String>)vma.disks;

		VirtuelnaMasina vMasina = new VirtuelnaMasina(vma.name, kategorija, diskovi, Integer.parseInt(vma.coreNumber), Integer.parseInt(vma.ram), Integer.parseInt(vma.gpu)); 
		
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
