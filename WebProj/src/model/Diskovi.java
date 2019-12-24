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

public class Diskovi {
	private HashMap<String, Disk> mapaDiskovi = new HashMap<String, Disk>();
	private List<Disk> listaDiskovi = new ArrayList<Disk>();
	private String putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\diskovi.json";
	
	public Diskovi(){}
	
	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	

	public HashMap<String, Disk> getMapaDiskovi() {
		return mapaDiskovi;
	}

	public List<Disk> getListaDiskovi() {
		return listaDiskovi;
	}

	/*Vraca Disk preko zadatog id-a (ime)*/
	public Disk getDisk(String ime){
		return mapaDiskovi.get(ime);
	}
	
	/* Funkcija vraca false ukoliko disk postoji,
	 * u suprotnom dodaje novi disk.*/
	public boolean dodajDisk(Disk disk){
		if(mapaDiskovi.containsKey(disk.getIme())){
			return false;
		}
		else{
			listaDiskovi.add(disk);
			mapaDiskovi.put(disk.getIme(), disk);
		}
		return true;
	}
	
	/* Upisuje disk u fajl u json formatu.*/
	public boolean UpisiDiskove(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(putanja), listaDiskovi);
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/* 
	 * Ucitava listu diskova iz fajla i pravi asocijativnu listu diskova.
	 * Kljuc je ime diska, vrednost je objekat Disk.
	 */
	public boolean UcitajOrganizacije(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			listaDiskovi = mapper.readValue(new File(putanja), new TypeReference<List<Disk>>(){});
			for(Disk d : listaDiskovi){
				mapaDiskovi.put(d.getIme(), d);
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
