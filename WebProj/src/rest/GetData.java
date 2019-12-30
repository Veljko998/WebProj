package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.Diskovi;
import model.Korisnici;
import model.Korisnik;
import model.VirtuelnaMasina;
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
		
		Diskovi d = new Diskovi();
		d.setPutanja();
		Disk d1 = new Disk("disk1", TipDiska.HDD, 1, "vm1" );
		Disk d2 = new Disk("disk2", TipDiska.SSD, 2, "vm2");
		d.dodajDisk(d1);
		d.dodajDisk(d2);
		d.UpisiDiskove();
		return d.UcitajDiskove();
		
	}
	
}
