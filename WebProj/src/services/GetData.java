package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.Diskovi;
import model.KategorijeVM;
import model.Korisnici;
import model.Korisnik;
import model.Organizacija;
import model.Organizacije;
import model.VM;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.enums.TipDiska;

@Path("/data")  
public class GetData {
	
	@GET
	@Path("/make")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getKorisnici() {
		//Korisnici k = new Korisnici();
		//k.setPutanja();
		//Korisnici k = new Korisnici("C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json");
		//k.UpisiKorisnike();
		//k.UcitajKorisnike();
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
		
		VirtuelneMasine m = new VirtuelneMasine();
		m.setPutanja();
		ArrayList<String> lista = new ArrayList<String>();
		VirtuelnaMasina vm1 = new VirtuelnaMasina("vm1", new VM("vm1", 1, 1, 1), lista, 1, 2, 3);
		VirtuelnaMasina vm2 = new VirtuelnaMasina("vm2", new VM("vm1", 1, 1, 1), lista, 1, 2, 3);
		m.dodajVirtuelnuMasinu(vm1);
		m.dodajVirtuelnuMasinu(vm2);
		m.UpisiVirtuelneMasine();
		return m.UcitajVirtuelneMasine();
		
	}
	
}
