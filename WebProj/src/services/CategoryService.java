/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.KategorijeVM;
import model.VM;
import model.VirtuelnaMasina;
import model.VirtuelneMasine;
import model.kendo.CategoryToAdd;
import model.kendo.CategoryToEdit;
import model.kendo.VMToDelete;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/categoryService")
public class CategoryService {

	@POST
	@Path("/editCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Change machine with oldName category
	 * 
	 * Change category.
	 * 
	 * @param cte
	 * @return true if category is edited successfully
	 */
	public boolean editCategory(CategoryToEdit cte) {
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		if (!cte.oldName.equals(cte.name)) {
			if (kategorijeVM.getMapaKategorijeVM().containsKey(cte.name)) {
				System.out.println("Vec postoji sa ovim nazivom. return false");
				return false;
			}
		}
		
		VM vmCat = new VM(cte.name, Integer.parseInt(cte.coreNumber), Integer.parseInt(cte.ram));
		
		//handle with gpu if has not value.
		if (cte.gpu != null) {
			vmCat.setGpu(Integer.parseInt(cte.gpu));
		}else {
			vmCat.setGpu(0);
		}
		
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			if (vm.getKategorjia().getIme().equals(cte.oldName)) {
				vm.setKategorjia(vmCat);
				
				vm.setBrojJezgara(vmCat.getBrojJezgara());
				vm.setRam(vmCat.getRamMemory());
				vm.setGpu(vmCat.getGpu());
			}
		}
		
		virtuelneMasine.UpisiVirtuelneMasine();
		
		for (VM vmCatToEdit : kategorijeVM.getListaKategorijeVM()) {
			if (vmCatToEdit.getIme().equals(cte.oldName)) {
				vmCatToEdit.setIme(vmCat.getIme());
				vmCatToEdit.setRamMemory(vmCat.getRamMemory());
				vmCatToEdit.setBrojJezgara(vmCat.getBrojJezgara());
				vmCatToEdit.setGpu(vmCat.getGpu());
				break;
			}
		}
		
		kategorijeVM.UpisiKategorijeVM();
		
		return true;
	}
	
	@POST
	@Path("/addCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * Just add new category.
	 * 
	 * @param cta
	 * @return true if category is written successfully
	 */
	public boolean addCategory(CategoryToAdd cta) {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		if (kategorijeVM.getMapaKategorijeVM().get(cta.name) != null) {
			System.out.println("Kategorija sa ovim nazivom vec postoji, "
					+ "ali ovde ne bi trebalo da se udje jer je prethodno napravljena provera za ovo. /addCategory");
			return false;
		}
		
		VM vm = new VM(cta.name, Integer.parseInt(cta.coreNumber), Integer.parseInt(cta.ram));
		
		if (cta.gpu != null) {
			vm.setGpu(Integer.parseInt(cta.gpu));
		}
		
		kategorijeVM.getListaKategorijeVM().add(vm);
		kategorijeVM.UpisiKategorijeVM();
		
		return true;
	}
	
	@GET
	@Path("/checkIfCategoryExist/{param1}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * @param categoryName
	 * @return true if category already exists with this name
	 */
	public boolean checkIfCategoryExist(@PathParam("param1") String categoryName) {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		if (kategorijeVM.getMapaKategorijeVM().get(categoryName) != null) {
			return true;
		}return false;
	}
	
	@POST
	@Path("/deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return all categories
	 */
	public boolean deleteCategory(VMToDelete vmtd) {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();
		
		VirtuelneMasine virtuelneMasine = new VirtuelneMasine();
		virtuelneMasine.setPutanja();
		virtuelneMasine.UcitajVirtuelneMasine();
		
		for (VirtuelnaMasina vm : virtuelneMasine.getListaVirtuelnihMasina()) {
			if (vm.getKategorjia().getIme().equals(vmtd.name)) {
				System.out.println("Ima dodeljene masine i ne moze da se brise...");
				return false;
			}
		}

		VM carForDelete = kategorijeVM.getMapaKategorijeVM().get(vmtd.name);
		
		kategorijeVM.getListaKategorijeVM().remove(carForDelete);
		kategorijeVM.UpisiKategorijeVM();
		
		return true;
		
	}
	
	@POST
	@Path("/getCategoryByName")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return all categories
	 */
	public VM getCategoryByName(VMToDelete vm) {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();

		try {
			return kategorijeVM.getMapaKategorijeVM().get(vm.name);
		} catch (Exception e) {
			System.out.println("Nismo uspeli da vratimo listu imena kategorija. /categoryService/getAllCategories");
			return null;
		}
	}
	
	@POST
	@Path("/getAllCategories2")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return all categories
	 */
	public List<VM> getAllCategories2() {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();

		try {
			return kategorijeVM.getListaKategorijeVM();
		} catch (Exception e) {
			System.out.println("Nismo uspeli da vratimo listu imena kategorija. /categoryService/getAllCategories");
			return null;
		}
	}
	
	@POST
	@Path("/getAllCategories")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return list of all category names.
	 */
	public List<String> getAllCategories() {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();

		try {
//			return kategorijeVM.getListaKategorijeVM();
			return new ArrayList<String>(kategorijeVM.getMapaKategorijeVM().keySet());
		} catch (Exception e) {
			System.out.println("Nismo uspeli da vratimo listu imena kategorija. /categoryService/getAllCategories");
			return null;
		}
	}
}



