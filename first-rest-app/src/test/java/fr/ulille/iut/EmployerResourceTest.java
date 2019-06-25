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

public class EmployerResourceTest {
	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		server = Main.startServer();
		Client c = ClientBuilder.newClient();

		target = c.target(Main.BASE_URI);

		Field field = EmployerResource.class.getDeclaredField("app");
		field.setAccessible(true);
		field.set("null", new HashMap<>());
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	/*@Test
	public void testCreateUser() throws NegativeIDException {
		Employer user = new Employer(0,"AVOSDIM","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> appEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

		Response response = target.path("/emp").request().post(appEntity);

		assertEquals(201, response.getStatus());


		URI uriAttendue = target.path("/emp").path(user.getId()+"").getUri();
		assertTrue(uriAttendue.equals(response.getLocation()));
	}

	@Test
	public void testCreateSameUser() throws NegativeIDException {
		Employer user = new Employer(0,"AVOSDIM","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

		// Envoi de la requête HTTP POST pour la création de l'utilisateur
		int first = target.path("/emp").request().post(userEntity).getStatus();

		// Vérification du code de retour HTTP
		assertEquals(201, first);

		int same = target.path("/emp").request().post(userEntity).getStatus();
		assertEquals(409, same);
	}

	@Test
	public void testGetEmptyListofUsers() {
		List<Employer> list = target.path("/emp").request().get(new GenericType<List<Employer>>(){});
		assertTrue(list.isEmpty());
	}

	@Test
	public void testGetTwoUsers() throws NegativeIDException {
		Employer user1 = new Employer(0,"AVOSDIM","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> userEntity = Entity.entity(user1, MediaType.APPLICATION_JSON);
		target.path("/users").request().post(userEntity);

		Employer user2 = new Employer(0,"AVOSDIMv12","volet4856", "Jean", "Bernard", 30);

		Entity<Employer> appEntity = Entity.entity(user2, MediaType.APPLICATION_JSON);
		appEntity = Entity.entity(user2, MediaType.APPLICATION_JSON);

		target.path("/emp").request().post(appEntity);

		List<Employer> list = target.path("/emp").request().get(new GenericType<List<Employer>>(){});
		assertEquals(2, list.size());
	}

	@Test
	public void testGetOneUser() throws NegativeIDException {
		Employer user = new Employer(0,"tata","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/emp").request().post(userEntity);

		User result = target.path("/emp").path("tata").request().get(User.class);
		assertEquals(user, result);
	}

	@Test
	public void testGetInexistantUser() {
		int notFound = target.path("/emp").path("dsqsdq").request().get().getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testDeleteOneUser() throws NegativeIDException {
		Employer user = new Employer(0,"tata","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/emp").request().post(userEntity);

		int code = target.path("/emp").path("tata").request().delete().getStatus();
		assertEquals(204, code);

		int notFound = target.path("/emp").path("tata").request().get().getStatus();
		assertEquals(404, notFound);	
	}

	@Test
	public void testDeleteInexistantUser() {
		int notFound = target.path("/emp").path("tking").request().delete().getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testModifyInexistantUser() throws NegativeIDException {
		Employer inexistant = new Employer(0,"AVOSDIM","volet4856", "Jean", "Bernard", 30);
		Entity<Employer> userEntity = Entity.entity(inexistant, MediaType.APPLICATION_JSON);
		int notFound = target.path("/emp").path("tata").request().put(userEntity).getStatus();
		assertEquals(404, notFound);
	}

	@Test
	public void testModifyUser() throws NegativeIDException {
		Employer user = new Employer(0,"AVOSDIM","volet4856", "Jean", "Bernard", 30);;
		Entity<Employer> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		target.path("/emp").request().post(userEntity);

		Employer modified = new Employer(0,"AVOSDIM","volet56", "Jean", "Bernard", 30);
		userEntity = Entity.entity(modified, MediaType.APPLICATION_JSON);

		int noContent = target.path("/emp").path("AVOSDIM").request().put(userEntity).getStatus();
		assertEquals(204, noContent);

		User retrieved = target.path("/emp").path("AVOSDIM").request().get(Employer.class);
		assertEquals(modified, retrieved);
	}
*/
}
