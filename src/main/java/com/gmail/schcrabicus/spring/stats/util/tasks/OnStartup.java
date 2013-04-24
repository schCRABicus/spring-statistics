package com.gmail.schcrabicus.spring.stats.util.tasks;

import com.gmail.schcrabicus.spring.stats.domain.Role;
import com.gmail.schcrabicus.spring.stats.domain.User;
import com.gmail.schcrabicus.spring.stats.service.IRoleService;
import com.gmail.schcrabicus.spring.stats.service.IUserService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 18.04.13
 * Time: 7:27
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OnStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger log = Logger.getLogger(OnStartup.class);

    private static final String[] ROLE_NAMES = new String[] { "ROLE_ADMIN" , "ROLE_USER" , "ROLE_MODERATOR" };

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

    @Autowired
    @Qualifier( "roleServiceImpl" )
    private IRoleService roleService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        log.debug( "on application event, preparing users and roles... ");
        log.debug("defaultUserLogin = " + defaultUserLogin);
        log.debug("defaultUserPassword = " + defaultUserPassword);
        log.debug("defaultUserFirstName = " + defaultUserFirstName);
        log.debug("defaultUserLastName = " + defaultUserLastName);
        log.debug("defaultUserBirthDate = " + ( defaultUserBirthDate != null ? defaultUserBirthDate.toString() : null));

        log.debug("checking roles presence...");
        Role role;
        for ( String roleName : ROLE_NAMES ){
            role = roleService.findByName( roleName );
            if ( role == null ){
                role = new Role();
                role.setName( roleName );
                roleService.create( role );
            }
        }

        log.debug("checking default user presence...");
        if ( installDefaultUser && defaultUserLogin != null && defaultUserPassword != null ){
            User user = userService.findByLogin( defaultUserLogin );
            if ( user == null ){
                user = new User();
                user.setLogin( defaultUserLogin );
                user.setPassword( defaultUserPassword );
                user.setFirstName( defaultUserFirstName );
                user.setLastName( defaultUserLastName );
                user.setBirthDate( defaultUserBirthDate );

                Set<Role> roles = new HashSet<Role>();
                for ( String roleName : ROLE_NAMES){
                    roles.add( roleService.findByName( roleName ));
                }

                user.setRoles( roles );
                userService.create( user );
            }
        }

    }
}
