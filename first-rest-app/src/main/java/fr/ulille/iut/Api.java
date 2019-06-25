package fr.ulille.iut;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/jobber/")
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.ulille.iut");
       // register(ApplicantResource.class);
        register(LoggingFeature.class);
    }
}