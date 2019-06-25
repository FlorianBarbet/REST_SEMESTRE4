package fr.iutinfo.skeleton.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import fr.iutinfo.skeleton.auth.AuthFilter;

@ApplicationPath("/v1/")
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.iutinfo.skeleton.api");
        register(LoggingFeature.class);
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }

}
