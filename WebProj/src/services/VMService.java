/**
 * 
 */
package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import model.kendo.VMToEdit;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/VMService")
public class VMService {

	@POST
	@Path("/turnOn")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Create new tuple when turn machine on.
	 * 
	 * @param vmName has attribute name.
	 * @return
	 */
	public boolean turnOn(VMToDelete vmName) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Tuple<Date, Date> tuple = new Tuple<>();
		
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			if (vm.getIme().equals(vmName.name)) {
				tuple.setFirst(new Date());
				tuple.setSecond(null);
				vm.getListaAktivnosti().add(tuple);
				virtuelneMasine.UpisiVirtuelneMasine();
				return true;
			}
		}
		
		System.out.println("Ovde ne treba da dodje: /turnOn");
		return false;
	}
	
	@POST
	@Path("/turnOff")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Just set current time in second attribute of tuple,
	 * date of turning machine off.
	 * 
	 * @param vmtd has attribute name.
	 * @return
	 */
	public boolean turnOff(VMToDelete vmName) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			System.out.println("hajmo oppp");
			if (vm.getIme().equals(vmName.name)) {
				System.out.println("Usao uopste ovde....");
				vm.getListaAktivnosti().get(vm.getListaAktivnosti().size() - 1).setSecond(new Date());
				virtuelneMasine.UpisiVirtuelneMasine();
				return true;
			}
		}
		
		System.out.println("Ovde ne treba da dodje: /turnOff");
		return false;
	}
	
	@POST
	@Path("/checkIsVMOnOff")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param vmtd has attribute name.
	 * @return true if VM is on
	 */
	public boolean checkIsVMOnOff(VMToDelete vmName) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		
		VirtuelnaMasina virtuelnaMasina = virtuelneMasine.getMapaVirtuelnihMasina().get(vmName.name);
		if (virtuelnaMasina.getListaAktivnosti().isEmpty()) {
			System.out.println("Prazna je.");
			return false;
		}else {
			// <date  null>  VM is on
			// <date  date> VM is off
			if (virtuelnaMasina.getListaAktivnosti().get(virtuelnaMasina.getListaAktivnosti().size() - 1).getSecond() == null) {
				return true;
			}return false;
		}
	}
	
	@POST
	@Path("/editVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * If admin:
	 *   Change KORISNIK => OORGANIZACIJA => listaResursa(list if VM names) ... because admin can change just from his organisation.
	 *   
	 * Superadmin:
	 *   List all users, their organisations, VM name list and in this list look for old VM name and replace with new.
	 * 
	 * Look for disk which is connected to old VM and replace with new.
	 * Delete VM with old name and replace with new VM.
	 * change VM name in list in organisations
	 * 
	 * @param vme
	 * @return true if VM is successfully edited and written. Else false.
	 */
	public boolean editVM(VMToEdit vme) {
		Korisnici korisnici = new Korisnici();
		korisnici.setPutanja();
		korisnici.UcitajKorisnike();
		
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		if (!vme.oldName.equals(vme.name)) {
			if (diskovi.getMapaDiskovi().containsKey(vme.name)) {
				System.out.println("Pokusavamo da promenimo masinu sa novim imenom koje je vec rezervisano kod diska. /editVM");
			}
		}
		
		try {
			if (vme.role.equals("admin")) {
				for (Korisnik korisnik : korisnici.getListaKorisnici()) {
					if (korisnik.getIme().equals(vme.email)) {
						korisnik.getOrganizacija().getListaResursa().remove(vme.oldName);
						korisnik.getOrganizacija().getListaResursa().add(vme.name);
						break;
					}
				}
				korisnici.UpisiKorisnike();
				
			}else { //superadmin
				for (Korisnik korisnik : korisnici.getListaKorisnici()) {
					for (String vmName : korisnik.getOrganizacija().getListaResursa()) {
						if (vmName.equals(vme.oldName)) {
							korisnik.getOrganizacija().getListaResursa().remove(vme.oldName);
							korisnik.getOrganizacija().getListaResursa().add(vme.name);
						}
					}
				}
				korisnici.UpisiKorisnike();
			}
			
			for (Disk disk : diskovi.getListaDiskovi()) {
				if (disk.getVirtualnaMasina().equals(vme.oldName)) {
					disk.setVirtualnaMasina(vme.name);
					break;
				}
			}
			diskovi.UpisiDiskove();
			
			for (VirtuelnaMasina virtuelnaMasina : virtuelneMasine.getListaVirtuelnihMasina()) {
				if (virtuelnaMasina.getIme().equals(vme.oldName)) {
					virtuelneMasine.getListaVirtuelnihMasina().remove(virtuelnaMasina);
					
					VM kategorija = kategorijeVM.getMapaKategorijeVM().get(vme.category);
					
					virtuelneMasine.getListaVirtuelnihMasina().add(new VirtuelnaMasina(vme.name, kategorija, (ArrayList<String>)vme.disks));
				}
			}
			virtuelneMasine.UpisiVirtuelneMasine();
			
			for (Organizacija organizacija : organizacije.getListaOrganizacije()) {
				for (String vmName : organizacija.getListaResursa()) {
					if (vmName.equals(vme.oldName)) {
						organizacija.getListaResursa().remove(vme.oldName);
						organizacija.getListaResursa().add(vme.name);
					}
				}
			}
			organizacije.UpisiOrganizacije();
			
			return true;
		} catch (Exception e) {
			System.out.println("Ovde ne smemo da upadnemo. vmService/editVM. Poruka o gresci: " + e);
			return false;
		}
	}
	
	@POST
	@Path("/getDisks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * get all disks from current VM and all disks which has not VM.
	 * 
	 * @param vme
	 * @return
	 */
	public List<String> getDisks(VMToDelete vme) {
		if (vme.name == null) { // because of click to go forward
			return null;
		}
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		Diskovi diskovi = new Diskovi();
		diskovi.setPutanja();
		diskovi.UcitajDiskove();
		
		VirtuelnaMasina vm = virtuelneMasine.getVirtuelnaMasina(vme.name);
		System.out.println(vm);
		
		List<String> disks = new ArrayList<String>();
		disks.addAll(vm.getDiskovi());
		
		for (Disk disk : diskovi.getListaDiskovi()) {
			if (disk.getVirtualnaMasina() == null) {
				disks.add(disk.getIme());
			}
		}
		
		return disks;
	}
	
	@POST
	@Path("/getVMByName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param vme
	 * @return VirtualMachine if exists. Else return null.
	 */
	public VirtuelnaMasina getVMByName(VMToDelete vme) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		VirtuelnaMasina vm = new VirtuelnaMasina();
		
		if ((vm = virtuelneMasine.getMapaVirtuelnihMasina().get(vme.name)) != null) {
			return vm;
		}return null;
	}
	
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
		
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		for (Organizacija organizacija : organizacije.getListaOrganizacije()) {
			if (organizacija.getListaResursa().contains(vmtd.name)) {
				organizacija.getListaResursa().remove(vmtd.name);
			}
		}
		
		organizacije.UpisiOrganizacije();
		
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
		}else {
			System.out.println("Ovde ne sme da udje, nema smisla. //deleteVM");
		}
		
		System.out.println("Masina nije uspesno obrisana. Ovde ne bi smeo da udje nikada.  /VMService/deleteVM");
		return false;
	}
	
	@POST
	@Path("/addVM")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addVM(VMToAdd vma) {
		Diskovi ddd = new Diskovi();
		ddd.setPutanja();
		ddd.UcitajDiskove();
		
		if (ddd.getMapaDiskovi().containsKey(vma.name)) {
			System.out.println("Pokusavamo da dodamo VM a vec postoji disk sa tim imenom");
			return false;
		}
		
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
		
		ArrayList<Tuple<Date, Date>> listaAktivnosti = new ArrayList<Tuple<Date,Date>>();

		VirtuelnaMasina vMasina = new VirtuelnaMasina(vma.name, kategorija, diskovi, kategorija.getBrojJezgara(), kategorija.getRamMemory(), kategorija.getGpu(), listaAktivnosti); 
		
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
