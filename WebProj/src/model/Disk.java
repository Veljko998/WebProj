package model;

import model.enums.TipDiska;

public class Disk {
	private String ime;
	private TipDiska tip;
	private int kapacitet;
	private VirtuelnaMasina virtualnaMasina;
	
	public Disk() {}
	
	public Disk(String ime, TipDiska tip, int kapacitet, VirtuelnaMasina virtualnaMasina) {
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.virtualnaMasina = virtualnaMasina;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public TipDiska getTip() {
		return tip;
	}

	public void setTip(TipDiska tip) {
		this.tip = tip;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public VirtuelnaMasina getVirtualnaMasina() {
		return virtualnaMasina;
	}

	public void setVirtualnaMasina(VirtuelnaMasina virtualnaMasina) {
		this.virtualnaMasina = virtualnaMasina;
	}
}
