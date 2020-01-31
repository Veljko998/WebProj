package model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import model.enums.TipDiska;

/**
 * 
 * @author Veljko
 * @since 21.12.2019.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Disk {
	
	@NotNull
	/**
	 * Unique
	 */
	private String ime;
	
	@NotNull
	private TipDiska tip;
	
	@NotNull
	private int kapacitet;
	
	private String virtualnaMasina; //jedinstveno ime virtuelne masine
	
	public Disk(){}
	/**
	 * Constructor.
	 * 
	 * @param ime
	 * @param tip
	 * @param kapacitet
	 * @param virtualnaMasina
	 */
	public Disk(String ime, TipDiska tip, int kapacitet, String virtualnaMasina) {
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.virtualnaMasina = virtualnaMasina;
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
	public TipDiska getTip() {
		return tip;
	}

	/**
	 * 
	 * @param tip
	 */
	public void setTip(TipDiska tip) {
		this.tip = tip;
	}

	/**
	 * 
	 * @return
	 */
	public int getKapacitet() {
		return kapacitet;
	}

	/**
	 * 
	 * @param kapacitet
	 */
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	/**
	 * 
	 * @return
	 */
	public String getVirtualnaMasina() {
		return virtualnaMasina;
	}

	/**
	 * 
	 * @param virtualnaMasina
	 */
	public void setVirtualnaMasina(String virtualnaMasina) {
		this.virtualnaMasina = virtualnaMasina;
	}
	
	@Override
	public String toString() {
		return "Disk [ime=" + ime + ", tip=" + tip + ", kapacitet=" + kapacitet + ", virtualnaMasina=" + virtualnaMasina
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + kapacitet;
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
		result = prime * result + ((virtualnaMasina == null) ? 0 : virtualnaMasina.hashCode());
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
		Disk other = (Disk) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (kapacitet != other.kapacitet)
			return false;
		if (tip != other.tip)
			return false;
		if (virtualnaMasina == null) {
			if (other.virtualnaMasina != null)
				return false;
		} else if (!virtualnaMasina.equals(other.virtualnaMasina))
			return false;
		return true;
	}
}
