package fr.iutinfo.skeleton.api;

import fr.iutinfo.skeleton.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;
import java.util.List;

public class Helper {
    private final static Logger logger = LoggerFactory.getLogger(Helper.class);
    private static final UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    static GenericType<List<UserDto>> listUserResponseType = new GenericType<List<UserDto>>() {
    };

    public static void initDb() {
        dao.dropUserTable();
        dao.createUserTable();
    }

    static UserJobber createUserWithName(String name) {
        UserJobber user = new UserJobber(0, name);
        return createUser(user);
    }

    static UserJobber createUserWithAlias(String name, String alias) {
        UserJobber user = new UserJobber(0, name, alias);
        return createUser(user);
    }

    static UserJobber createUserWithEmail(String name, String email) {
        UserJobber user = new UserJobber(0, name);
        user.setEmail(email);
        return createUser(user);
    }

    public static UserJobber createUserWithPassword(String name, String passwd, String salt) {
        UserJobber user = new UserJobber(0, name);
        user.setSalt(salt);
        user.setPassword(passwd);
        logger.debug("createUserWithPassword Hash : " + user.getPasswdHash());
        return createUser(user);
    }

    private static UserJobber createUser(UserJobber user) {
        int id = dao.insert(user);
        user.setId(id);
        return user;
    }


    private static UserJobber createFullUSer(String name, String alias, String email, String paswword) {
        UserJobber user = new UserJobber(0, name);
        user.setAlias(alias);
        user.setEmail(email);
        user.setPassword(paswword);
        int id = dao.insert(user);
        user.setId(id);
        return user;
    }

    static void createRms() {
        createFullUSer("Richard Stallman", "RMS", "rms@fsf.org", "gnuPaswword");
    }

    static UserJobber createRob() {
        return createFullUSer("Robert Capillo", "rob", "rob@fsf.org", "paswword");
    }

    static UserJobber createLinus() {
        return createFullUSer("Linus Torvalds", "linus", "linus@linux.org", "paswword");
    }

    static UserJobber createIan() {
        return createFullUSer("Ian Murdock", "debian", "ian@debian.org", "mot de passe");
    }
}
