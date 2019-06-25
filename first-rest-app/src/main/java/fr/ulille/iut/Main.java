package fr.ulille.iut;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on

    public static final String BASE_URI = "http://0.0.0.0:8080/jobber/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     * @throws SQLException 
     */
    public static HttpServer startServer() throws SQLException {
        // create a resource config that scans for JAX-RS resources and providers
        // in fr.ulille.iut package
        final ResourceConfig rc = new Api();
        BDDFactory database = new BDDFactory();
        //database.dropTable();
        if(database.getApplicant("root")==null)database.CreateBDD();
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     * @throws SQLException 
     */
    public static void main(String[] args) throws IOException, SQLException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

