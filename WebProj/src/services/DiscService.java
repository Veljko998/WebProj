/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.graalvm.compiler.hotspot.nodes.VMErrorNode;

import model.Disk;
import model.Diskovi;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.enums.TipDiska;
import model.kendo.DiscToAdd;
import model.kendo.DiskToDelete;
import model.kendo.DiskToEdit;
import model.kendo.UserToGetData2;

/** 
 * @author Veljko
 * @since 13.01.2020.
 */
@Path("/discService")
public class DiscService {
	
	@POST
	@Path("/getAllDisks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Take just disks from users organisation if admin and if not connected on Vm
	 * Else take disks from picked organisation if not connected on Vm
	 * 
	 * @param ugt - role, email, orgName
	 * @return list of name of disks
	 */
	public List<String> getAllDisks(UserToGetData2 ugt) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		Organizacija org = organizacije.getMapaOrganizacije().get(ugt.orgName);
		
		List<String> imenaDiskova = new ArrayList<String>();
		
		if (org != null) {
			for (String str : org.getListaResursa()) {
				if (diskovi.getMapaDiskovi().containsKey(str) && diskovi.getMapaDiskovi().get(str).getVirtualnaMasina() == null) {
					imenaDiskova.add(str);
				}
			}
		}
		
		return imenaDiskova;
	}
	
	@POST
	@Path("/editDisk")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param dta - role, email, oldName, name, capacity, VMName, type
	 * @return
	 */
	public boolean editDisk(DiskToEdit dte) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		if (dte.VMName == null) {

		}else if (dte.VMName.equalsIgnoreCase("Choose...")) {
			dte.VMName = null;
		}
		
		Disk newDisk = new Disk(dte.name, TipDiska.valueOf(dte.type), Integer.parseInt(dte.capacity), dte.VMName);
		
		if (diskovi.getMapaDiskovi().get(dte.oldName).equals(newDisk)) {
			System.out.println("Nema smisa da menjam disk istim diskom. vracam true svakako.");
			return true;
		}
		
		/*
		 * Ako imaju ista imena onda nema potrebe da menjam unutar masina i organizacija
		 */
		if (!dte.oldName.equals(dte.name)) {
			if (!dte.oldName.equals(dte.name)) {
				for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
					if (vm.getDiskovi().contains(dte.oldName)) {
						vm.getDiskovi().remove(dte.oldName);
						vm.getDiskovi().add(dte.name);
					}
				}
			}
			
			virtuelneMasine.UpisiVirtuelneMasine();
			
			String orgName = "";
			
			for (Organizacija org : organizacije.getListaOrganizacije()) {
				if (org.getListaResursa().contains(dte.oldName) && diskovi.getMapaDiskovi().containsKey(dte.oldName)) { //Ovde ce samo jednom da udje
					org.getListaResursa().remove(dte.oldName);
					org.getListaResursa().add(dte.name);
					orgName = org.getIme();
				}
			}
			
			organizacije.UpisiOrganizacije();
			System.out.println("Naziv organizacije je: " + orgName);
			
			for (Korisnik kor : korisnici.getListaKorisnici()) {
				if (kor.getOrganizacija().getIme().equals(orgName)) {
					kor.getOrganizacija().getListaResursa().remove(dte.oldName);
					kor.getOrganizacija().getListaResursa().add(dte.name);
				}
			}
			
			korisnici.UpisiKorisnike();
		}
		
		diskovi.getListaDiskovi().remove(diskovi.getMapaDiskovi().get(dte.oldName));
		diskovi.getListaDiskovi().add(newDisk);
		
		diskovi.UpisiDiskove();
		
		return true;
	}

	@POST
	@Path("/getDiskByName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Find Disk with name dtd.name.
	 * 
	 * @param dtd is used as DiskToEdit, dte is not necessary because they would have just one, same, unique attribute 'name'
	 * @return Disk
	 */
	public Disk getDiskByName(DiskToDelete dtd) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		try {
			System.out.println(diskovi.getMapaDiskovi().get(dtd.name));
			return diskovi.getMapaDiskovi().get(dtd.name);
		} catch (Exception e) {
			System.out.println("Usao u catch prilikom vracanja diska iz mape '/editDisk'..");
			return null;
		}
	}
	
	@POST
	@Path("/deleteDisk")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteDisk(DiskToDelete dtd) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		Disk diskToDelete = diskovi.getMapaDiskovi().get(dtd.name);
		String diskName = dtd.name;
		
		/*
		 * Delete disk from disks.
		 */
		diskovi.getListaDiskovi().remove(diskToDelete);
		diskovi.UpisiDiskove();
		
		String orgName = "";
		
		/*
		 * Delete disk from organisations.
		 */
		for (Organizacija org : organizacije.getListaOrganizacije()) {
			if (org.getListaResursa().contains(diskName)) {
				org.getListaResursa().remove(diskName);
				orgName = org.getIme();
			}
		}
		
		organizacije.UpisiOrganizacije();
		
		/*
		 * Delete disk from VMs.
		 */
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			if (vm.getDiskovi().contains(diskName)) {
				vm.getDiskovi().remove(diskName);
			}
		}
		
		virtuelneMasine.UpisiVirtuelneMasine();
		
		/*
		 * Delete disk from users.
		 */
		for (Korisnik kor : korisnici.getListaKorisnici()) {
			if (kor.getOrganizacija().getIme().equals(orgName)) {
				kor.getOrganizacija().getListaResursa().remove(diskName);
			}
		}
		
		korisnici.UpisiKorisnike();
		
		return true;
	}
	
	@GET
	@Path("/checkIfDiscExist/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param discName
	 * @return true if there is disc with name discName
	 */
	public boolean checkIfDiscExist(@PathParam("param1") String discName){
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		VirtuelneMasine masine = new VirtuelneMasine();
		masine.setPutanja();
		masine.UcitajVirtuelneMasine();
		
		if (masine.getMapaVirtuelnihMasina().containsKey(discName)) {
			System.out.println("Pokusavamo da dodamo disk a vec postoji masina sa tim nazivom.");
			return true;
		}
		
		if (diskovi.getMapaDiskovi().containsKey(discName)) {
			System.out.println("Postoji disk sa nazivom: " + discName);
			return true;
		} return false;
	}
	
	@POST
	@Path("/addDisc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addDisc(DiscToAdd dta) {
		if (dta.VMName == null) {
			
		}else if (dta.VMName.equalsIgnoreCase("Choose...")) {
			dta.VMName = null;
		}
		
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		System.out.println(dta.name);
		System.out.println(dta.email);
		System.out.println(dta.VMName);
		
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		String orgName = korisnici.getMapaKorisnici().get(dta.email).getOrganizacija().getIme();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			if (vm.getIme().equals(dta.VMName)) {
					vm.getDiskovi().add(dta.name);
			}
		}
		
		virtuelneMasine.UpisiVirtuelneMasine();
		
		for (Korisnik kor : korisnici.getListaKorisnici()) {
			if (kor.getOrganizacija().getIme().equals(orgName)) {
				kor.getOrganizacija().getListaResursa().add(dta.name);
			}
		}
		
		for (Organizacija organizacija : organizacije.getListaOrganizacije()) {
			if (organizacija.getIme().equals(orgName)) {
				organizacija.getListaResursa().add(dta.name);
			}
		}
		
		organizacije.UpisiOrganizacije();
		
		korisnici.UpisiKorisnike();
		
		Disk disk = new Disk();
		
		disk.setIme(dta.name);
		disk.setKapacitet(Integer.parseInt(dta.capacity));
		disk.setTip(TipDiska.valueOf(dta.type));
		disk.setVirtualnaMasina(dta.VMName); // Name of virtual machine, or null if not VM

		diskovi.dodajDisk(disk);
		
		//TODO: Maybe write this disc to virtual machine ?
		if (diskovi.UpisiDiskove()) {
			return true;
		} return false;
	}
}
