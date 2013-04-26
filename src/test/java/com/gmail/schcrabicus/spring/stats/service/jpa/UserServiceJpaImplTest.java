package com.gmail.schcrabicus.spring.stats.service.jpa;

import com.gmail.schcrabicus.spring.stats.domain.Role;
import com.gmail.schcrabicus.spring.stats.domain.User;
import com.gmail.schcrabicus.spring.stats.service.IUserService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;


/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 22.04.13
 * Time: 8:03
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceJpaImplTest extends AbstractServiceJpaImplTest {

    private static Logger log = Logger.getLogger( UserServiceJpaImplTest.class );

    @Value("#{applicationProperties['default.install']}")
    private boolean installDefaultUser;

    @Value("#{applicationProperties['default.user.admin.login']}")
    private String defaultUserLogin;

    @Value("#{applicationProperties['default.user.admin.password']}")
    private String defaultUserPassword;

    @Value("#{applicationProperties['default.user.admin.firstname']}")
    private String defaultUserFirstName;

    @Value("#{applicationProperties['default.user.admin.lastname']}")
    private String defaultUserLastName;

    @Value("#{applicationProperties['default.user.admin.birthdate']}")
    private DateTime defaultUserBirthDate;

    private static Map<String, String> defaultUserCredentials = new HashMap<String, String>();

    static {
        defaultUserCredentials.put( "correctLogin" , "Login" );
        defaultUserCredentials.put( "correctPassword" , "Password" );
        defaultUserCredentials.put( "shortLogin" , "Me" );
        defaultUserCredentials.put( "shortPassword" , "Pass" );
        defaultUserCredentials.put( "firstName" , "Quality" );
        defaultUserCredentials.put( "updatedFirstName" , "SuperQuality" );
        defaultUserCredentials.put( "lastName" , "Assurance" );
    }

    @Autowired
    @Qualifier( "userServiceImpl" )
    private IUserService userService;

    /**
     * Checks whether the default user has been installed on application startup, if required;
     */
    @Test
    @Transactional( readOnly = true )
    public void testDefaultUserPresence() throws Exception{
        log.debug(" testing users presence just after application startup... ");

        List<User> users = userService.findAll();
        User defaultUser;
        if ( installDefaultUser ){
            assertEquals( 1 , users.size() );

            defaultUser = users.get( 0 );
            if ( defaultUser != null ){
                assertEquals( defaultUser.getFirstName() , defaultUserFirstName );
                assertEquals( defaultUser.getLastName() , defaultUserLastName );
                assertEquals( defaultUser.getLogin() , defaultUserLogin );
                assertEquals( defaultUser.getPassword() , defaultUserPassword );
                //assertEquals( defaultUser.getBirthDate() , defaultUserBirthDate );
            }
        } else {
            assertEquals( users.size() , 0 );
        }
    }

    /**
     * Tests list of all users loading;
     */
    @Test
    @Transactional( readOnly = true )
    public void testFindAll() throws Exception{

        log.debug(" loading all users... ");

        List<User> users = userService.findAll();

        for ( User user : users ){
            log.debug(">> found user : " + user);
        }
        assertNotNull( users );
    }

    /**
     * Tests finding user by login;
     */
    @Test
    @Transactional
    @Rollback
    public void testFindByLogin() throws Exception{

        log.debug(" loading user by login... ");

        User user = null;

        if ( installDefaultUser ){
            user = userService.findByLogin( defaultUserLogin );
        } else {
            User newUser = getDefaultUser();

            userService.create( newUser );
            user = userService.findByLogin( "User_login" );
        }

        assertNotNull( user );
    }

    /**
     * Tests ability to create user;
     */
    @Test
    @Transactional
    @Rollback
    public void testCreateUser() throws Exception{
        User user = getDefaultUser();

        Long id = userService.create( user ).getId();

        User storedUser = userService.findById( id );
        assertNotNull( storedUser );
        assertEquals( storedUser.getFirstName() , user.getFirstName() );
    }

    /**
     * Tries to create a user with empty credentials;
     *
     * @throws Exception
     */
    @Test( expected = ConstraintViolationException.class )
    @Transactional
    @Rollback
    public void testCreateUserWithJSR303Error() throws Exception{
        User user = new User();

        userService.create( user );

        List<User> users = userService.findAll();
        assertEquals( installDefaultUser ? 1 : 0 , users.size() );
    }

    /**
     * Creates user with short login;
     */
    @Test( expected = ConstraintViolationException.class )
    @Transactional
    @Rollback
    public void testCreateUserWithShortLoginSizeJSR303Error() throws Exception{
        User user = getDefaultUser();
        user.setLogin(defaultUserCredentials.get("shortLogin"));

        userService.create( user );

        List<User> users = userService.findAll();
        assertEquals( installDefaultUser ? 1 : 0 , users.size() );
    }

    /**
     * Creates user with short password;
     */
    @Test( expected = ConstraintViolationException.class )
    @Transactional
    @Rollback
    public void testCreateUserWithShortPasswordSizeJSR303Error() throws Exception{
        User user = getDefaultUser();
        user.setPassword( defaultUserCredentials.get( "shortPassword" ) );

        userService.create( user );

        List<User> users = userService.findAll();
        assertEquals( installDefaultUser ? 1 : 0 , users.size() );
    }

    /**
     * Tests update functionality;
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testUpdateUser() throws Exception{
        User user = getDefaultUser();

        user = userService.create( user );
        user.setFirstName( defaultUserCredentials.get( "updatedFirstName" ));

        User updatedUser = userService.update( user );

        assertEquals( user.getId() , updatedUser.getId() );
        assertEquals( defaultUserCredentials.get( "updatedFirstName" ) , updatedUser.getFirstName() );
    }

    /**
     * Tests delete functionality;
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() throws Exception{
        Long initialSize = userService.count();

        User user = getDefaultUser();
        userService.create( user );
        User createdUser = userService.findById( user.getId() );

        assertNotNull( createdUser );

        userService.delete( user );
        User removedUser = userService.findById( createdUser.getId() );

        assertNull( removedUser );
        Long finalSize = userService.count();

        assertEquals( initialSize , finalSize );
    }

    /**
     * Gets default user;
     *
     * @return User instance;
     */
    private static User getDefaultUser(){
        User user = new User();

        user.setLogin( defaultUserCredentials.get( "correctLogin" ));
        user.setPassword( defaultUserCredentials.get( "correctPassword" ));
        user.setFirstName( defaultUserCredentials.get( "firstName" ));
        user.setLastName( defaultUserCredentials.get( "lastName" ));
        user.setRoles( new HashSet<Role>());

        return user;
    }
}
