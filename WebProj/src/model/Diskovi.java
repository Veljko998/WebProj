package model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
	private String putanja = "";
	
	public Diskovi(){}
	
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
			reponsePath = new File(fullPath).getPath() + File.separatorChar + "diskovi.json";
			this.putanja = reponsePath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
	public boolean UcitajDiskove(){
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
