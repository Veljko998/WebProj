package model;

/**
 * 
 * @author Veljko
 * @since 20.12.2019.
 */
public class VM {
	private String ime;
	private int brojJezgara;
	private int ramMemory;
	private int gpu;
	
	public VM(String ime, int brojJezgara, int ramMemory) {
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		this.ramMemory = ramMemory;
		this.gpu = 0;
	}

	public VM(String ime, int brojJezgara, int ramMemory, int gpu) {
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		this.ramMemory = ramMemory;
		this.gpu = gpu;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getBrojJezgara() {
		return brojJezgara;
	}

	public void setBrojJezgara(int brojJezgara) {
		this.brojJezgara = brojJezgara;
	}

	public int getRamMemory() {
		return ramMemory;
	}

	public void setRamMemory(int ramMemory) {
		this.ramMemory = ramMemory;
	}

	public int getGpu() {
		return gpu;
	}

	public void setGpu(int gpu) {
		this.gpu = gpu;
	}
}
