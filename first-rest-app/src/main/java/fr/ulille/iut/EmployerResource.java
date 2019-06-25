package fr.ulille.iut;

import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/emp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployerResource {
	private static Map<String,Employer> employer= new HashMap<>();
	private static BDDFactory sql;

	@Context
	public UriInfo uriInfo;

	public EmployerResource() throws SQLException{
		sql=new BDDFactory();
	}

	@POST
	public Response createEmployer(Employer emp){

	

		if ( sql.getEmployer(emp.getLogin())!=null ) {
			return Response.status(Response.Status.CONFLICT).build();
		} else if(sql.getApplicant(emp.getLogin())!=null ) {
			return Response.status(Response.Status.CONFLICT).build();
		}else {
			sql.addEmployer(emp.getLogin(), emp.getPassword(), emp.getEmail());

			URI instanceURI = uriInfo.getAbsolutePathBuilder().path(emp.getId()+"").build();
			return Response.created(instanceURI).build();
		}

	} 

//	@POST
//	@Path("{login}/offer")
//	public Response createOffer(@PathParam("login") String id,Offer offer) throws SQLException{
//		if ( sql.getEmployer(id).equals(null)) {
//			throw new NotFoundException();
//		}
//		else {
//			ArrayList<Offer> off=sql.getAllOffer();
//			for(int i=0;i < off.size();i++){
//				if(off.get(i).equals(offer)){
//					return Response.status(Response.Status.CONFLICT).build();
//				}
//			}
//
//
//			sql.addOffer(id, offer.getType(), offer.getImg(), offer.getLibelle());
//
//			URI instanceURI = uriInfo.getAbsolutePathBuilder().path(offer+"").build();
//			return Response.created(instanceURI).build();
//
//		}
//	}

	/*@GET
	public ArrayList<Employer> getEmployer(){
		return new ArrayList<Employer>(sql.get);
	}*/

	@DELETE
	@Path("{login}")
	public Response deleteArticle(@PathParam("login") String id) {
		if ( sql.getEmployer(id)==null ) {
			throw new NotFoundException();
		}
		else {
			sql.deleteEmployer(id);

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@PUT
	@Path("{login}")
	public Response modifyUser(@PathParam("login") String id, Employer emp) {
		// Si l'utilisateur est inconnu, on renvoie 404
		if (   sql.getEmployer(id)==null) {
			throw new NotFoundException();
		}
		else {
			sql.updateEmployer(emp);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}


	@GET
	@Path("{login}")
	public Employer getAccount(@PathParam("login")String log){
		if( sql.getEmployer(log)==null){
			throw new NotFoundException();

		}else {
			return sql.getEmployer(log);
		}
	}
	
	@GET
    public List<Employer> getApplicants(){        
        return sql.getAllEmployer();
        
    }



}
