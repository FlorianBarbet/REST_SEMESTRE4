package fr.iutinfo.skeleton.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	
	@Context
	public UriInfo uriInfo;

	public EmployerResource(){}
	
	@POST
	public Response createEmployer(Employer emp){
		for(String id : employer.keySet()){
			if(emp.equals(employer.get(id))){
				return Response.status(Response.Status.CONFLICT).build();
			}
		}

		if ( employer.containsKey(emp.getId()) ) {
			return Response.status(Response.Status.CONFLICT).build();
		} else {
			employer.put(emp.getId()+"", emp);

			URI instanceURI = uriInfo.getAbsolutePathBuilder().path(emp.getId()+"").build();
			return Response.created(instanceURI).build();
		}
	} 
	
	@GET
	public ArrayList<Employer> getEmployer(){
		return new ArrayList<Employer>(employer.values());
	}


}
