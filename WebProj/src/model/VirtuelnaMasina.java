package model;

import java.util.ArrayList;

/**
 * 
 * @author Veljko
 * @since 20.12.2019.
 */
public class VirtuelnaMasina {
	private String ime;
	private VM kategorjia;
	private int brojJezgara;
	private int ram;
	private int gpu;
	private ArrayList<Disk> diskovi;
	
	/**
	 * Konstruktor
	 * 
	 * @param ime - unique
	 * @param kategorjia
	 * @param diskovi
	 */
	public VirtuelnaMasina(String ime, VM kategorjia, ArrayList<Disk> diskovi) {
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
	public ArrayList<Disk> getDiskovi() {
		return diskovi;
	}

	/**
	 * 
	 * @param diskovi
	 */
	public void setDiskovi(ArrayList<Disk> diskovi) {
		this.diskovi = diskovi;
	}
	
	
}
