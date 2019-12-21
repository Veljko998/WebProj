package model;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import model.enums.Uloga;

public class Korisnik {
	private String email; //jedinstveno
	private String ime;
	private String prezime;
	private Organizacija organizacija;
	private Uloga uloga;
	private List<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti; //(vremePrijave, vremeOdjave)
	
	public Korisnik(){}
	
	public Korisnik(String email, String ime, String prezime,
			Organizacija organizacija, Uloga uloga,
			List<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti) {
		super();
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.organizacija = organizacija;
		this.uloga = uloga;
		this.listaAktivnosti = listaAktivnosti;
	}
	
	public Korisnik(Korisnik k) {
		super();
		this.email = k.getEmail();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.organizacija = k.getOrganizacija();
		this.uloga = k.getUloga();
		this.listaAktivnosti = k.getListaAktivnosti();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Organizacija getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(Organizacija organizacija) {
		this.organizacija = organizacija;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public List<Tuple<LocalDateTime, LocalDateTime>> getListaAktivnosti() {
		return listaAktivnosti;
	}

	public void setListaAktivnosti(
			List<Tuple<LocalDateTime, LocalDateTime>> listaAktivnosti) {
		this.listaAktivnosti = listaAktivnosti;
	}
	
	
	
	
	
}
