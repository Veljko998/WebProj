package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Organizacije {
	
	private HashMap<String, Organizacija> mapaOrganizacije = new HashMap<String, Organizacija>();
	private List<Organizacija> listaOrganizacije = new ArrayList<Organizacija>();
	private String putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\organizacije.json";
	
	public Organizacije(){}
	
	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public HashMap<String, Organizacija> getMapaOrganizacije() {
		return mapaOrganizacije;
	}

	public List<Organizacija> getListaOrganizacije() {
		return listaOrganizacije;
	}

	/*Vraca Organizaciju preko zadatog id-a (ime)*/
	public Organizacija getOrganizacija(String ime){
		return mapaOrganizacije.get(ime);
	}
	
	/* Funkcija vraca false ukoliko organizacija postoji,
	 * u suprotnom dodaje novu organizaciju.*/
	public boolean dodajOrganizaciju(Organizacija organizacija){
		if(mapaOrganizacije.containsKey(organizacija.getIme())){
			return false;
		}
		else{
			listaOrganizacije.add(organizacija);
			mapaOrganizacije.put(organizacija.getIme(), organizacija);
		}
		return true;
	}
	
	/* Upisuje organizacije u fajl u json formatu.*/
	public boolean UpisiOrganizacije(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(putanja), listaOrganizacije);
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/* 
	 * Ucitava listu organizacija iz fajla i pravi asocijativnu listu organizacija.
	 * Kljuc je ime organizacije, vrednost je objekat Organizacija.
	 */
	public boolean UcitajOrganizacije(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			listaOrganizacije = mapper.readValue(new File(putanja), new TypeReference<List<Organizacija>>(){});
			for(Organizacija o : listaOrganizacije){
				mapaOrganizacije.put(o.getIme(), o);
			}
			return true;
			
		}catch(JsonParseException e){
			return false;
		}catch(JsonMappingException e){
			return false;
		}catch(IOException e){
			return false;
		}
		catch(Exception e){
			return false;
		}
	}
	
}
