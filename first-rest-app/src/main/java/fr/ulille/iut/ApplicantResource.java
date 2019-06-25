package fr.ulille.iut;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

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

	private static BDDFactory sql;
	@Context
	public UriInfo uriInfo;

	public ApplicantResource() throws SQLException{
		sql=new BDDFactory();
	}

	@POST
    public Response createApplicant(Applicant app){


        if ( sql.getApplicant(app.getLogin())!=null  ) {

            return Response.status(Response.Status.CONFLICT).build();
        }else if ( sql.getEmployer(app.getLogin())!=null  ) {
            return Response.status(Response.Status.CONFLICT).build();
        } else {
            System.out.print("\n"+app.toString());
            sql.addAppilcant(app.getLogin(), app.getPassword(), app.getEmail());

            URI instanceURI = uriInfo.getAbsolutePathBuilder().path(app.getLogin()).build();
            return Response.created(instanceURI).build();
        }
    } 

    @GET
    public List<Applicant> getApplicants(){        
        return sql.getAllApplicant();
    }
	@DELETE
	@Path("{login}")
	public Response deleteArticle(@PathParam("login") String login) {
		if ( sql.getApplicant(login)==null ) {
			throw new NotFoundException();
		}
		else {
			sql.deleteApplicant(login);

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	//	@PUT
	//	@Path("dip/{login}")
	//	public Response addDip(@PathParam("login") String id, Diplomes dip) {
	//		// Si l'utilisateur est inconnu, on renvoie 404
	//		if (  ! applicant.containsKey(id) ) {
	//			throw new NotFoundException();
	//		}
	//		else {
	//			Applicant app = applicant.get(id);
	//			app.addDiplomes(dip);
	//			applicant.put(id,app);
	//			return Response.status(Response.Status.NO_CONTENT).build();
	//		}
	//	}

	@PUT
	@Path("{login}")
	public Response modifyUser(@PathParam("login") String login, Applicant app) {
		// Si l'utilisateur est inconnu, on renvoie 404
		if ( sql.getApplicant(login)==null  ) {
			throw new NotFoundException();
		}
		else {

			String age = app.getAge()+"";
			sql.updateAppilcant(app,login,age);

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
	
	

	@GET
	@Path("{login}")
	public Applicant getAccount(@PathParam("login")String log){
		if(sql.getApplicant(log)==null){
			throw new NotFoundException();

		}else {
			return sql.getApplicant(log);
		}
	}

	@GET
	@Path("{login}/{password}")
	public String verifyAccount(@PathParam("login")String log,@PathParam("password")String password){
		
		System.out.println("log : "+log);
		System.out.println("pass : "+password);
		if((sql.getApplicant(log)==null&&sql.getEmployer(log)==null)&&sql.getEmailApplicant(log)==null){
			throw new NotFoundException();

		}else {
			Applicant app=null;
			Employer emp=null;
			
			if(sql.getEmployer(log)==null){
				app=sql.getApplicant(log);
			}
			else
				emp=sql.getEmployer(log);

			if(emp!=null&&app==null){
				System.out.println("next "+emp);
				return emp.passwdOK(password);
			}
			else if(app!=null)
				return app.passwdOK(password);
			else
				return "{ \"success\" : \"undefined\" }";
		}
	}	

}
