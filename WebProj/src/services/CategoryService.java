/**
 * 
 */
package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.KategorijeVM;
import model.VM;
import model.kendo.VMToDelete;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/categoryService")
public class CategoryService {

	//deleteCategory
	@POST
	@Path("/deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return all categories
	 */
	public boolean deleteCategory(VMToDelete vm) {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();

		VM carForDelete = kategorijeVM.getMapaKategorijeVM().get(vm.name);
		
		kategorijeVM.getListaKategorijeVM().remove(carForDelete);
		
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



