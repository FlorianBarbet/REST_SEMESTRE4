package fr.ulille.iut;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

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

@Path("/offer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OfferResource {

	private static BDDFactory sql;
	@Context
	public UriInfo uriInfo;

	public OfferResource() throws SQLException{
		sql=new BDDFactory();
	}

	@GET
	public ArrayList<Offer> getOffers() throws SQLException{
		return sql.getAllOffer();
	}

	@GET
	@Path("{id}")
	public Offer getOffer(@PathParam("id")String id){
		System.out.println("getOffer()--1 "+id);

		System.out.println("OFFER : "+sql.getOffer(id));
		return sql.getOffer(id);
	}

	@POST
	public Response createOffer(Offer offer){
		if ( sql.getOffer( offer.getID()+"" )!=null  ) {

			return Response.status(Response.Status.CONFLICT).build();
		} else {
			System.out.print("\n"+offer.toString());
			sql.addOffer(offer.getLogin(), offer.getType(), offer.getImg(),offer.getLibelle());

			URI instanceURI = uriInfo.getAbsolutePathBuilder().path(offer.getID()+"").build();
			return Response.created(instanceURI).build();
		}
	}

	@DELETE
	@Path("{id}/{login}/{password}")
	public Response deleteArticle(@PathParam("id") String id,@PathParam("login") String login,@PathParam("password") String password) {
		if ( sql.getEmployer(login)==null ) {
			throw new NotFoundException();
		}else if(sql.getOffer(id)==null){
			throw new NotFoundException();
		}else {
			if(sql.getEmployer(login).passwdOK(password).equals("{\"success\": \"true\"}")){
				sql.deleteOffer(login, Integer.parseInt(id));
			}else{
				Response.status(Response.Status.FORBIDDEN).build();
			}

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@PUT
	@Path("{id}/{login}/{password}")
	public Response modifyOffer(@PathParam("password") String pass,@PathParam("id") String id,@PathParam("login") String login, Offer off) {
		if ( sql.getEmployer(login)== null ) {
			throw new NotFoundException();
		}else if(sql.getOffer(id)==null){
			throw new NotFoundException();
		}else {
			if(sql.getEmployer(login).passwdOK(pass).equals("{\"success\": \"true\",  \"type\":\"employer\"}")){
				sql.updateOffer(off, id);
			}else if(sql.getApplicant(login).passwdOK(pass).equals("{\"success\": \"true\",  \"type\":\"applicant\"}")){
				sql.likeOffer(login, off.getID());
			}else			{
				Response.status(Response.Status.FORBIDDEN).build();
			}

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}
	
	@PUT
	@Path("{id}/{login}/{password}/addapp")
	public Response addAppLike(@PathParam("password") String pass,@PathParam("id") String id,@PathParam("login") String login, Offer off) {
		System.out.println("addapplike -- login : "+login+" off "+off);

		if ( sql.getApplicant(login)== null ) {
			System.out.println("login");

			throw new NotFoundException();
			
		}else if(sql.getOffer(id)==null){
			System.out.println("offer");
			throw new NotFoundException();
			
		}else {
		//	System.out.println("ENTER EKSE"+sql.getApplicant(login).passwdOK(pass));

			if(sql.getApplicant(login).passwdOK(pass).equals("{\"success\": \"true\",  \"type\":\"applicant\" }")){
				
				System.out.println("addapplike -- login : "+login+" off "+off);

				sql.likeOffer(login, off.getID());
			}else{
				return Response.status(Response.Status.FORBIDDEN).build();
			}

			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}


}
