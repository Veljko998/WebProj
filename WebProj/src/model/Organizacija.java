package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class Organizacija {
	private String ime; //jedinstveno
	private String opis;
	private String logo; //putanja ili samo naziv fajla 
	private List<String> listaKorisnika; // mejlovi
	private List<String> listaResursa; // imena virtuelnih masina i diskova
	
	public Organizacija(){}
	
	public Organizacija(String ime, String opis, String logo,
			List<String> listaKorisnika, List<String> listaResursa) {
		super();
		this.ime = ime;
		this.opis = opis;
		this.logo = logo;
		this.listaKorisnika = listaKorisnika;
		this.listaResursa = listaResursa;
	}
	
	public Organizacija(Organizacija org) {
		super();
		this.ime = org.getIme();
		this.opis = org.getOpis();
		this.logo = org.getLogo();
		this.listaKorisnika = org.getListaKorisnika();
		this.listaResursa = org.getListaResursa();
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<String> getListaKorisnika() {
		return listaKorisnika;
	}

	public void setListaKorisnika(List<String> listaKorisnika) {
		this.listaKorisnika = listaKorisnika;
	}

	public List<String> getListaResursa() {
		return listaResursa;
	}

	public void setListaResursa(List<String> listaResursa) {
		this.listaResursa = listaResursa;
	}

	@Override
	public String toString() {
		return "Organizacija [ime=" + ime + ", opis=" + opis + ", logo=" + logo + ", listaKorisnika=" + listaKorisnika
				+ ", listaResursa=" + listaResursa + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((listaKorisnika == null) ? 0 : listaKorisnika.hashCode());
		result = prime * result + ((listaResursa == null) ? 0 : listaResursa.hashCode());
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
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
		Organizacija other = (Organizacija) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (listaKorisnika == null) {
			if (other.listaKorisnika != null)
				return false;
		} else if (!listaKorisnika.equals(other.listaKorisnika))
			return false;
		if (listaResursa == null) {
			if (other.listaResursa != null)
				return false;
		} else if (!listaResursa.equals(other.listaResursa))
			return false;
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		return true;
	}
}
