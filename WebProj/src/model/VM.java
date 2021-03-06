package model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @author Veljko
 * @since 20.12.2019.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class VM {
	
	@NotNull
	private String ime;
	
	@NotNull
	@Min(1)
	private int brojJezgara;
	
	@NotNull
	@Min(1)
	private int ramMemory;
	
	private int gpu;
	
	public VM(){}
	/**
	 * Constructor.
	 * 
	 * @param ime
	 * @param brojJezgara
	 * @param ramMemory
	 */
	public VM(String ime, int brojJezgara, int ramMemory) {
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		this.ramMemory = ramMemory;
		this.gpu = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param ime
	 * @param brojJezgara
	 * @param ramMemory
	 * @param gpu
	 */
	public VM(String ime, int brojJezgara, int ramMemory, int gpu) {
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		this.ramMemory = ramMemory;
		this.gpu = gpu;
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
	public int getRamMemory() {
		return ramMemory;
	}

	/**
	 * 
	 * @param ramMemory
	 */
	public void setRamMemory(int ramMemory) {
		this.ramMemory = ramMemory;
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
	
	@Override
	public String toString() {
		return "VM [ime=" + ime + ", brojJezgara=" + brojJezgara + ", ramMemory=" + ramMemory + ", gpu=" + gpu + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brojJezgara;
		result = prime * result + gpu;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ramMemory;
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
		VM other = (VM) obj;
		if (brojJezgara != other.brojJezgara)
			return false;
		if (gpu != other.gpu)
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (ramMemory != other.ramMemory)
			return false;
		return true;
	}
}
