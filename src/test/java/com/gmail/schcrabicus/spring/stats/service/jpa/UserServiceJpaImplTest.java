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
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


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
            User newUser = new User();
            newUser.setLogin( "User_login" );
            newUser.setPassword( "User_password" );

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
        User user = new User();

        user.setFirstName( "User" );
        user.setLastName( "Shmuser" );
        user.setLogin( "zeliboba" );
        user.setPassword( "crabby" );
        user.setRoles( new HashSet<Role>() );

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
        User user = new User();
        user.setLogin( "me" );
        user.setPassword( "perfect_password" );

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
        User user = new User();
        user.setLogin( "Perfect_login" );
        user.setPassword( "I" );

        userService.create( user );

        List<User> users = userService.findAll();
        assertEquals( installDefaultUser ? 1 : 0 , users.size() );
    }
}
