package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Korisnici {
	private HashMap<String, Korisnik> korisniciMapa = new HashMap<String, Korisnik>();
	private List<Korisnik> korisniciLista = new ArrayList<Korisnik>();
	private String putanja;
	
	public Korisnici(){}
	
	/* 
	 * Ucitava listu korisnika iz fajla i pravi asocijativnu listu korisnika.
	 * Kljuc je email korisnika jer je on jedinstven, vrednost je objekat Korisnik.
	 * */
	public Korisnici(String putanja){
		this.putanja = putanja;
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			korisniciLista = mapper.readValue(putanja, mapper.getTypeFactory().constructCollectionType(List.class, Korisnik.class));
			
			for(Korisnik k : korisniciLista){
				korisniciMapa.put(k.getEmail(), k);
			}
			
		}catch(JsonParseException e){
			
		}catch(JsonMappingException e){
			
		}catch(IOException e){
			
		}
		catch(Exception e){
			
		}
	}
	
	public boolean UpisiKorisnike(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			String arrayToJson = mapper.writeValueAsString(korisniciLista);
			
			
		}
	}
}
