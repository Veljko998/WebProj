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
}
