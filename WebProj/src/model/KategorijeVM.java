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

public class KategorijeVM {
	private HashMap<String, VM> mapaKategorijeVM = new HashMap<String, VM>();
	private List<VM> listaKategorijeVM = new ArrayList<VM>();
	private String putanja = "";
	
	public KategorijeVM(){}
	
	public String getPutanja() {
		return putanja;
	}

	/*pronalazi apsolutnu putanju do fajla*/
	public void setPutanja() {
		//this.putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\kategorijeVM.json";
		//this.putanja = "D:\\Semestar5\\Veb programiranje\\Projekat\\WebProj\\WebContent\\kategorijeVM.json";
		
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/classes/");
			fullPath = pathArr[0];
			String reponsePath = "";
			reponsePath = new File(fullPath).getPath() + File.separatorChar + "kategorijeVM.json";
			this.putanja = reponsePath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public HashMap<String, VM> getMapaKategorijeVM() {
		return mapaKategorijeVM;
	}

	public List<VM> getListaKategorijeVM() {
		return listaKategorijeVM;
	}

	/*Vraca Kategoriju preko zadatog id-a (ime)*/
	public VM getKategorijaVM(String ime){
		return mapaKategorijeVM.get(ime);
	}
	
	/* Funkcija vraca false ukoliko ime kategorije vec postoji,
	 * u suprotnom dodaje novu kategoriju.*/
	public boolean dodajKategorijuVM(VM vm){
		if(mapaKategorijeVM.containsKey(vm.getIme())){
			return false;
		}
		else{
			listaKategorijeVM.add(vm);
			mapaKategorijeVM.put(vm.getIme(), vm);
		}
		return true;
	}
	
	/* Upisuje kategoriju VM u fajl u json formatu.*/
	public boolean UpisiKategorijeVM(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(putanja), listaKategorijeVM);
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/* 
	 * Ucitava listu kategorija VM iz fajla i pravi asocijativnu listu kategorija VM.
	 * Kljuc je ime kategorije, vrednost je objekat VM.
	 */
	public boolean UcitajKategorijeVM(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			listaKategorijeVM = mapper.readValue(new File(putanja), new TypeReference<List<VM>>(){});
			for(VM vm : listaKategorijeVM){
				mapaKategorijeVM.put(vm.getIme(), vm);
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
