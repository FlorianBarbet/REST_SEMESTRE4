package fr.ulille.iut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplicantResourceTest {
	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		server = Main.startServer();
		Client c = ClientBuilder.newClient();

		target = c.target(Main.BASE_URI);

		Field field = ApplicantResource.class.getDeclaredField("app");
		field.setAccessible(true);
		field.set("null", new HashMap<>());
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test
	public void testCreateUser() throws NegativeIDException {
		Applicant user = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> appEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

		Response response = target.path("/app").request().post(appEntity);

		assertEquals(201, response.getStatus());


		URI uriAttendue = target.path("/app").path(user.getId()+"").getUri();
		assertTrue(uriAttendue.equals(response.getLocation()));
	}

	@Test
	public void testCreateSameUser() throws NegativeIDException {
		Applicant user = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

		// Envoi de la requête HTTP POST pour la création de l'utilisateur
		int first = target.path("/app").request().post(userEntity).getStatus();

		// Vérification du code de retour HTTP
		assertEquals(201, first);

		int same = target.path("/app").request().post(userEntity).getStatus();
		assertEquals(409, same);
	}

	@Test
	public void testGetEmptyListofUsers() {
		List<Applicant> list = target.path("/app").request().get(new GenericType<List<Applicant>>(){});
		assertTrue(list.isEmpty());
	}

	@Test
	public void testGetTwoUsers() throws NegativeIDException {
		Applicant user1 = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(user1, MediaType.APPLICATION_JSON);
		target.path("/users").request().post(userEntity);

		Applicant user2 = new Applicant("titi@toto.com", 0, "toto", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");

		Entity<Applicant> appEntity = Entity.entity(user2, MediaType.APPLICATION_JSON);
		appEntity = Entity.entity(user2, MediaType.APPLICATION_JSON);

		target.path("/app").request().post(appEntity);

		List<Applicant> list = target.path("/app").request().get(new GenericType<List<Applicant>>(){});
		assertEquals(2, list.size());
	}

	@Test
	public void testGetOneUser() throws NegativeIDException {
		Applicant user = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/app").request().post(userEntity);

		User result = target.path("/app").path("tata").request().get(User.class);
		assertEquals(user, result);
	}

	@Test
	public void testGetInexistantUser() {
		int notFound = target.path("/app").path("dsqsdq").request().get().getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testDeleteOneUser() throws NegativeIDException {
		Applicant user = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/app").request().post(userEntity);

		int code = target.path("/app").path("tata").request().delete().getStatus();
		assertEquals(204, code);

		int notFound = target.path("/app").path("tata").request().get().getStatus();
		assertEquals(404, notFound);	
	}

	@Test
	public void testDeleteInexistantUser() {
		int notFound = target.path("/app").path("tking").request().delete().getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testModifyInexistantUser() throws NegativeIDException {
		Applicant inexistant = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(inexistant, MediaType.APPLICATION_JSON);
		int notFound = target.path("/app").path("tata").request().put(userEntity).getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testModifyUser() throws NegativeIDException {
		Applicant user = new Applicant("titi@toto.com", 0, "titi", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		Entity<Applicant> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/app").request().post(userEntity);

		Applicant modified = new Applicant("titi@toto.com", 0, "tata", "123", "", "", 31, "", "Je veux devenir DevOps", "Informatique");
		userEntity = Entity.entity(modified, MediaType.APPLICATION_JSON);

		int noContent = target.path("/app").path("tata").request().put(userEntity).getStatus();
		assertEquals(204, noContent);

		User retrieved = target.path("/app").path("tata").request().get(Applicant.class);
		assertEquals(modified, retrieved);
	}

}
