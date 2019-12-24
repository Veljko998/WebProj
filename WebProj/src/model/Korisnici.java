package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;

import model.enums.Uloga;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Korisnici {
	private HashMap<String, Korisnik> korisniciMapa = new HashMap<String, Korisnik>();
	private List<Korisnik> korisniciLista = new ArrayList<Korisnik>();
	private String putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json";
	
	public Korisnici(){}
	
	/* 
	 * Ucitava listu korisnika iz fajla i pravi asocijativnu listu korisnika.
	 * Kljuc je email korisnika jer je on jedinstven, vrednost je objekat Korisnik.
	 * */
	public Korisnici(String putanja){
		this.putanja = putanja;
	}
	
	public List<Korisnik> UcitajKorisnike(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			//korisniciLista =  Arrays.asList(mapper.readValue(putanja, Korisnik[].class));
			korisniciLista = mapper.readValue(new File(putanja), new TypeReference<List<Korisnik>>(){});
			for(Korisnik k : korisniciLista){
				korisniciMapa.put(k.getEmail(), k);
			}
			
			return korisniciLista;
			
		}catch(JsonParseException e){
			
		}catch(JsonMappingException e){
			
		}catch(IOException e){
			
		}
		catch(Exception e){
			return korisniciLista;
		}
		return korisniciLista;
	}
	
	public List<Korisnik> UpisiKorisnike(){
		try{
			Korisnik k = new Korisnik("mail", "Ime1", "Prezime1",
					new Organizacija(), Uloga.KORISNIK,
					 new ArrayList<Tuple<LocalDateTime, LocalDateTime>>());
			Korisnik k2 = new Korisnik("mail2", "Ime1", "Prezime1",
					new Organizacija(), Uloga.KORISNIK,
					 new ArrayList<Tuple<LocalDateTime, LocalDateTime>>());
			
			korisniciLista.add(k);
			korisniciLista.add(k2);
			
			ObjectMapper mapper = new ObjectMapper();
			//this.putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json";
			mapper.writeValue(new File(putanja), korisniciLista);
			return korisniciLista;
			
			
		}catch(Exception e){
			return korisniciLista;
		}
		
	}
}
