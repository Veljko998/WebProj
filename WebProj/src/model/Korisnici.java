package model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
	private HashMap<String, Korisnik> mapaKorisnici = new HashMap<String, Korisnik>();
	private List<Korisnik> listaKorisnici = new ArrayList<Korisnik>();
	private String putanja = "";
	//private String putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\korisnici.json";
	
	public Korisnici(){}
	
	public HashMap<String, Korisnik> getMapaKorisnici() {
		return mapaKorisnici;
	}

	public List<Korisnik> getListaKorisnici() {
		return listaKorisnici;
	}

	public String getPutanja() {
		return putanja;
	}
	
	/*pronalazi apsolutnu putanju do fajla*/
	public void setPutanja() {
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
			fullPath = pathArr[0];
			String reponsePath = "";
			reponsePath = new File(fullPath).getPath() + File.separatorChar + "korisnici.json";
			this.putanja = reponsePath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/*Vraca Korisnika preko zadatog id-a (email)*/
	public Korisnik getKorisnik(String email){
		return mapaKorisnici.get(email);
	}
	
	/* Funkcija vraca false ukoliko korisnik postoji,
	 * u suprotnom dodaje novog korisnika.*/
	public boolean dodajKorisnika(Korisnik korisnik){
		if(mapaKorisnici.containsKey(korisnik.getEmail())){
			System.out.println("Postoji korisnik sa tim mejlom. Korisnici/dodajKorisnika");
			return false;
		}
		else{
			listaKorisnici.add(korisnik);
			mapaKorisnici.put(korisnik.getEmail(), korisnik);
		}
		return true;
	}
	
	/* Upisuje korisnike u fajl u json formatu.*/
	public boolean UpisiKorisnike(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(putanja), listaKorisnici);
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/* 
	 * Ucitava listu korisnika iz fajla i pravi asocijativnu listu korisnika.
	 * Kljuc je email korisnika (jedinstven), vrednost je objekat Korisnik.
	 */
	public boolean UcitajKorisnike(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			listaKorisnici = mapper.readValue(new File(putanja), new TypeReference<List<Korisnik>>(){});
			for(Korisnik k : listaKorisnici){
				mapaKorisnici.put(k.getEmail(), k);
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
