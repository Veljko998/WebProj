/**
 * 
 */
package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Disk;
import model.Diskovi;
import model.VirtuelneMasine;
import model.enums.TipDiska;
import model.kendo.DiscToAdd;

/** 
 * @author Veljko
 * @since 13.01.2020.
 */
@Path("/discService")
public class DiscService {

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
