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

public class VirtuelneMasine {
	private HashMap<String, VirtuelnaMasina> mapaVirtuelnihMasina = new HashMap<String, VirtuelnaMasina>();
	private List<VirtuelnaMasina> listaVirtuelnihMasina = new ArrayList<VirtuelnaMasina>();
	private String putanja = "C:\\Users\\Ivana\\git\\WebProj\\WebProj\\WebContent\\virtuelneMasine.json";
	
	public VirtuelneMasine() {}
	
	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public HashMap<String, VirtuelnaMasina> getMapaVirtuelnihMasina() {
		return mapaVirtuelnihMasina;
	}

	public List<VirtuelnaMasina> getListaVirtuelnihMasina() {
		return listaVirtuelnihMasina;
	}

	/*Vraca virtuelnu masinu preko zadatog id-a (ime)*/
	public VirtuelnaMasina getVirtuelnaMasina(String ime){
		return mapaVirtuelnihMasina.get(ime);
	}
	
	/* Funkcija vraca false ukoliko virtuelna masina postoji,
	 * u suprotnom dodaje novu.*/
	public boolean dodajVirtuelnuMasinu(VirtuelnaMasina vm){
		if(mapaVirtuelnihMasina.containsKey(vm.getIme())){
			return false;
		}
		else{
			listaVirtuelnihMasina.add(vm);
			mapaVirtuelnihMasina.put(vm.getIme(), vm);
		}
		return true;
	}
	
	/* Upisuje virtuelne masine u fajl u json formatu.*/
	public boolean UpisiVirtuelneMasine(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(putanja), listaVirtuelnihMasina);
			return true;
			
		}catch(Exception e){
			return false;
		}
		
	}
	
	/* 
	 * Ucitava listu virtuelnih masina iz fajla i pravi asocijativnu listu.
	 * Kljuc je ime virtuelne masine, vrednost je objekat VirtuelnaMasina.
	 */
	public boolean UcitajVirtuelneMasine(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			listaVirtuelnihMasina = mapper.readValue(new File(putanja), new TypeReference<List<VirtuelnaMasina>>(){});
			for(VirtuelnaMasina vm : listaVirtuelnihMasina){
				mapaVirtuelnihMasina.put(vm.getIme(), vm);
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
