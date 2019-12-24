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
}
