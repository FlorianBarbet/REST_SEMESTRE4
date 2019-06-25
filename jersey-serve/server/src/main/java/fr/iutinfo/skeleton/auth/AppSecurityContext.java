package fr.iutinfo.skeleton.auth;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.User;

public class AppSecurityContext implements SecurityContext {
    final static Logger logger = LoggerFactory.getLogger(AppSecurityContext.class);
    private User user;
    private String scheme;

    public AppSecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
        logger.debug("isUserInRole called for : "+ s);
        if ("user".equals(s)){
            return user.isInUserGroup();
        }
            return true;

    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
