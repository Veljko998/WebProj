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

import model.Organizacije;

/** 
 * @author Veljko
 * @since 22.01.2020.
 */
@Path("/organisationService")
public class OrganisationService {

	@POST
	@Path("/getAllOrganisations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * @return list of names of all organisations.
	 */
	public List<String> getAllOrganisations() {
		Organizacije organizacije = new Organizacije();
		organizacije.setPutanja();
		organizacije.UcitajOrganizacije();
		
		try {
			return new ArrayList<String>(organizacije.getMapaOrganizacije().keySet());
		} catch (Exception e) {
			System.out.println("Neuspesno vracanje liste imena organizacija. /organisationService/getAllOrganisations");
			return null;
		}
	}
	
}
