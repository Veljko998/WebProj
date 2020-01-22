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

import model.Disk;
import model.Diskovi;
import model.Korisnici;
import model.Korisnik;
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
	 * Take just disks from users organisation if admin
	 * Else take disks from picked organisation
	 * 
	 * @param ugt
	 * @return list of name of disks
	 */
	public List<String> getAllDisks(UserToGetData2 ugt) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		if (organizacije.getMapaOrganizacije().get(ugt.orgName) == null) {
			System.out.println("Ne postoji organizacija sa imenom: " + ugt.orgName);
			return null;
		}
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		organizacije.getMapaOrganizacije().get(ugt.orgName);
		
		List<VirtuelnaMasina> masine = new ArrayList<VirtuelnaMasina>();
		List<String> imenaDiskova = new ArrayList<String>();
		
		try {
			for (String vmName : organizacije.getMapaOrganizacije().get(ugt.orgName).getListaResursa()) {
				if (virtuelneMasine.getMapaVirtuelnihMasina().get(vmName) == null) {
					System.out.println("Ne postoji VM sa nazivom: " + vmName);
				}else {
					masine.add(virtuelneMasine.getMapaVirtuelnihMasina().get(vmName));
				}
			}
			
			for (VirtuelnaMasina vm : masine) {
				for (String diskName : vm.getDiskovi()) {
					imenaDiskova.add(diskName);
				}
			}
			
			System.out.println("Velicina liste koju vracam je: " + imenaDiskova.size());
			return imenaDiskova;
		} catch (Exception e) {
			System.out.println("Nesto ne valja prilikom vracanja diskova. /discService/getAllDisks");
			return null;
		}
		
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
		HashMap<String, Disk> mapaDiskova = diskovi.getMapaDiskovi();
		
		if (mapaDiskova.containsKey(dte.name) && !dte.name.equals(dte.oldName)) {
			System.out.println("Disk sa ovim nazivom vec postoji.");
			return false;
		}
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Disk oldDisk = mapaDiskova.get(dte.oldName);
		Disk editedDisk = new Disk(dte.name, TipDiska.valueOf(dte.type), Integer.parseInt(dte.capacity), dte.VMName);
		
		if (oldDisk.equals(editedDisk)) {
			System.out.println("Isti disk hocemo da menjamo sto nema smisla. Vracam false.");
			return false;
		}else {
			//stari disk brisemo i dodajemo novi
			Collections.replaceAll(diskovi.getListaDiskovi(), oldDisk, editedDisk);
			diskovi.UpisiDiskove();
		}
		
		//ako diskovi imaju isti naziv, nista ne diramo jer vm imaju listu naziva diskova. 
		//Disk ce se svakako ucitati iz diskovi.json sto je vec promenjeno
		if (dte.oldName.equals(dte.name)) {
			System.out.println("Diskovi imaju isti naziv i ne diram nista kod vir. masina.");
			return true;
		}
		
		/*
		 * Stari disk pokazuje na odredjenu VM.
		 * Iz liste naziva diskova te VM brisemo naziv starog diska.
		 * 
		 * Sada novi disk pokazuje na odredjenu VM.
		 * U listu te vm mi ubacujemo naziv novog diska(bio on isti kao stari ili ne).
		 * Upisujemo sve VM.
		 */
		virtuelneMasine.getListaVirtuelnihMasina().get(virtuelneMasine.getListaVirtuelnihMasina().indexOf(virtuelneMasine.getMapaVirtuelnihMasina().get(oldDisk.getVirtualnaMasina()))).getDiskovi().remove(oldDisk.getIme());  
		virtuelneMasine.getListaVirtuelnihMasina().get(virtuelneMasine.getListaVirtuelnihMasina().indexOf(virtuelneMasine.getMapaVirtuelnihMasina().get(editedDisk.getVirtualnaMasina()))).getDiskovi().add(editedDisk.getIme());
		virtuelneMasine.UpisiVirtuelneMasine();
		System.out.println("Uspesno smo upisali i virtuelne masine. vracamo true");
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
		Disk diskToDelete;
		
		if ((diskToDelete = diskovi.getMapaDiskovi().get(dtd.name)) != null) {
			diskovi.getListaDiskovi().remove(diskToDelete);
			diskovi.UpisiDiskove();
			return true;
		}return false;
	}
	
	@GET
	@Path("/checkIfDiscExist/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @param discName
	 * @return true if there is no disc with name discName
	 */
	public boolean getJustUsers2(@PathParam("param1") String discName){
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		if (diskovi.getMapaDiskovi().containsKey(discName)) {
			return true;
		} return false;
	}
	
	@POST
	@Path("/addDisc")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addDisc(DiscToAdd dta) {
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		Disk disk = new Disk();
		disk.setIme(dta.name);
		disk.setKapacitet(Integer.parseInt(dta.capacity));
		disk.setTip(TipDiska.valueOf(dta.type));
		disk.setVirtualnaMasina(dta.VMName); // Name of virtual machine

		diskovi.dodajDisk(disk);
		
		//TODO: Maybe write this disc to virtual machine ?
		if (diskovi.UpisiDiskove()) {
			return true;
		} return false;
	}
}
