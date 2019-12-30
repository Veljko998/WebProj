package model;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Veljko
 * @since 20.12.2019.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class VirtuelnaMasina {
	
	@NotNull
	private String ime;
	
	@NotNull
	private VM kategorjia;
	
	@NotNull
	@Min(1)
	private int brojJezgara;
	
	@NotNull
	@Min(1)
	private int ram;
	
	private int gpu;
	
	private ArrayList<String> diskovi; //jedinstvena imena diskova
	
	
	public VirtuelnaMasina(){}
	/**
	 * Constructor.
	 * 
	 * @param ime - unique
	 * @param kategorjia
	 * @param diskovi
	 */
	public VirtuelnaMasina(String ime, VM kategorjia, ArrayList<String> diskovi) {
		this.ime = ime;
		this.kategorjia = kategorjia;
		this.diskovi = diskovi;
		this.brojJezgara = kategorjia.getBrojJezgara();
		this.ram = kategorjia.getRamMemory();
		this.gpu = kategorjia.getGpu();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * 
	 * @param ime
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * 
	 * @return
	 */
	public VM getKategorjia() {
		return kategorjia;
	}

	/**
	 *  All common attributes from category will be set with setting the category.
	 *  
	 * @param kategorjia
	 */
	public void setKategorjia(VM kategorjia) {
		this.kategorjia = kategorjia;
		this.brojJezgara = kategorjia.getBrojJezgara();
		this.ram = kategorjia.getRamMemory();
		this.gpu = kategorjia.getGpu();
	}
	
	/**
	 * 
	 * @return all connected disks
	 */
	public ArrayList<String> getDiskovi() {
		return diskovi;
	}

	/**
	 * 
	 * @param diskovi
	 */
	public void setDiskovi(ArrayList<String> diskovi) {
		this.diskovi = diskovi;
	}
	
	
}
