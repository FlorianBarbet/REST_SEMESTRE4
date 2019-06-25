package fr.iutinfo.skeleton.api;

import java.net.URI;
import java.util.ArrayList;
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


@Path("/app")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicantResource {
	private static Map<String,Applicant> applicant= new HashMap<>();
	
	@Context
	public UriInfo uriInfo;

	public ApplicantResource(){}
	
	@POST
	public Response createApplicant(Applicant app){
		for(String id : applicant.keySet()){
			if(app.equals(applicant.get(id))){
				return Response.status(Response.Status.CONFLICT).build();
			}
		}

		if ( applicant.containsKey(app.getLogin()) ) {
			return Response.status(Response.Status.CONFLICT).build();
		} else {
			applicant.put(app.getLogin(), app);
			
			URI instanceURI = uriInfo.getAbsolutePathBuilder().path(app.getLogin()).build();
			return Response.created(instanceURI).build();
		}
	} 
	
	@GET
	public List<Applicant> getApplicants(){
		return new ArrayList<Applicant>(applicant.values());
	}
	
	@DELETE
	@Path("{login}")
	public Response deleteArticle(@PathParam("login") String id) {
		if (  ! applicant.containsKey(id) ) {
			throw new NotFoundException();
		}
		else {
			applicant.remove(id);

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
	
	@PUT
	@Path("{login}")
	public Response modifyUser(@PathParam("login") String id, Applicant app) {
		// Si l'utilisateur est inconnu, on renvoie 404
		if (  ! applicant.containsKey(id) ) {
			throw new NotFoundException();
		}
		else {
			applicant.put(id, app);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}


	@GET
	@Path("{login}")
	public Applicant getAccount(@PathParam("login")String log){
		if(!applicant.containsKey(log)){
			throw new NotFoundException();

		}else {
			return applicant.get(log);
		}
	}
	
	@GET
	@Path("{login}/{password}")
	public String verifyAccount(@PathParam("login")String log,@PathParam("password")String password){
		if(!applicant.containsKey(log)){
			throw new NotFoundException();

		}else {
			return applicant.get(log).passwdOK(password);
		}
	}
	
		
	

	

}
