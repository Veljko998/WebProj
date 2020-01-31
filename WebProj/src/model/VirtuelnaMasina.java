package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	@Min(1)
	private int gpu;
	
	private ArrayList<String> diskovi; //jedinstvena imena diskova
	
	private List<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti; //(vremePaljenja, vremeGasenja)
	
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
		this.listaAktivnosti = new ArrayList<Tuple<LocalDateTime, LocalDateTime>>();
	}
	
	public VirtuelnaMasina(String ime, VM kategorjia, ArrayList<String> diskovi, ArrayList<Tuple<LocalDateTime, 
			LocalDateTime>> listaAktivnosti) {
		this.ime = ime;
		this.kategorjia = kategorjia;
		this.diskovi = diskovi;
		this.brojJezgara = kategorjia.getBrojJezgara();
		this.ram = kategorjia.getRamMemory();
		this.gpu = kategorjia.getGpu();
		this.listaAktivnosti = listaAktivnosti;
	}
	
	public VirtuelnaMasina(String ime, VM kategorjia, ArrayList<String> diskovi, int brojJezgara,
			int ram, int gpu, ArrayList<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti) {
		this.ime = ime;
		this.kategorjia = kategorjia;
		this.diskovi = diskovi;
		this.brojJezgara = brojJezgara;
		this.ram = ram;
		this.gpu = gpu;
		this.listaAktivnosti = listaAktivnosti;
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
	
	/**
	 * 
	 * @return
	 */
	public int getBrojJezgara() {
		return brojJezgara;
	}
	
	/**
	 * 
	 * @param brojJezgara
	 */
	public void setBrojJezgara(int brojJezgara) {
		this.brojJezgara = brojJezgara;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRam() {
		return ram;
	}
	
	/**
	 * 
	 * @param ram
	 */
	public void setRam(int ram) {
		this.ram = ram;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getGpu() {
		return gpu;
	}
	
	/**
	 * 
	 * @param gpu
	 */
	public void setGpu(int gpu) {
		this.gpu = gpu;
	}
	
	public List<Tuple<LocalDateTime, LocalDateTime>> getListaAktivnosti() {
		return listaAktivnosti;
	}
	
	public void setListaAktivnosti(
			List<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti) {
		this.listaAktivnosti = listaAktivnosti;
	}
	
	@Override
	public String toString() {
		return "VirtuelnaMasina [ime=" + ime + ", kategorjia=" + kategorjia + ", brojJezgara=" + brojJezgara + ", ram="
				+ ram + ", gpu=" + gpu + ", diskovi=" + diskovi + ", listaAktivnosti=" + listaAktivnosti + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brojJezgara;
		result = prime * result + ((diskovi == null) ? 0 : diskovi.hashCode());
		result = prime * result + gpu;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((kategorjia == null) ? 0 : kategorjia.hashCode());
		result = prime * result + ((listaAktivnosti == null) ? 0 : listaAktivnosti.hashCode());
		result = prime * result + ram;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtuelnaMasina other = (VirtuelnaMasina) obj;
		if (brojJezgara != other.brojJezgara)
			return false;
		if (diskovi == null) {
			if (other.diskovi != null)
				return false;
		} else if (!diskovi.equals(other.diskovi))
			return false;
		if (gpu != other.gpu)
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (kategorjia == null) {
			if (other.kategorjia != null)
				return false;
		} else if (!kategorjia.equals(other.kategorjia))
			return false;
		if (listaAktivnosti == null) {
			if (other.listaAktivnosti != null)
				return false;
		} else if (!listaAktivnosti.equals(other.listaAktivnosti))
			return false;
		if (ram != other.ram)
			return false;
		return true;
	}
}
