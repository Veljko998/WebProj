/**
 * 
 */
package services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.KategorijeVM;
import model.VM;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/categoryService")
public class CategoryService {

	@POST
	@Path("/getAllCategories")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @return all categories
	 */
	public List<VM> getAllCategories() {
		KategorijeVM kategorijeVM = new KategorijeVM();
		kategorijeVM.setPutanja();
		kategorijeVM.UcitajKategorijeVM();

		try {
			return kategorijeVM.getListaKategorijeVM();
		} catch (Exception e) {
			System.out.println("Nismo uspeli da vratimo kategorije. /categoryService/getAllCategories");
			return null;
		}
	}
}



