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
	private List<String> listaResursa;
	
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
	
}
