package com.gmail.schcrabicus.spring.stats.service.jpa;

import com.gmail.schcrabicus.spring.stats.domain.Role;
import com.gmail.schcrabicus.spring.stats.service.IRoleService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 25.04.13
 * Time: 8:29
 * To change this template use File | Settings | File Templates.
 */
public class RoleServiceJpaImplTest extends AbstractServiceJpaImplTest {

    @Autowired
    @Qualifier( "roleServiceImpl" )
    private IRoleService roleService;

    @Resource( name = "defaultRoles" )
    private Set<String> defaultRoles;

    private static final String TEST_ROLE = "QA";

    private static Logger log = Logger.getLogger(RoleServiceJpaImplTest.class);

    /**
     * Tests whether the default roles have been installed;
     * @throws Exception
     */
    @Test
    @Transactional( readOnly = true )
    public void testDefaultRoles() throws Exception{
        if ( defaultRoles != null ){
            for ( String role : defaultRoles ){
                assertNotNull( roleService.findByName( role ) );
            }
        }
    }

    /**
     * Tests findAll method;
     * @throws Exception
     */
    @Test
    @Transactional( readOnly = true )
    public void testFindAll() throws Exception{
        log.debug(">> testFindAll, defaultRoles : ");
        log.debug("... " + defaultRoles);
        assertEquals( defaultRoles != null ? defaultRoles.size() : 0 , roleService.findAll().size() );
    }

    /**
     * Tests role creation;
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testCreateRole() throws Exception{
        Role role = getDefaultRole();

        roleService.create( role );

        assertNotNull( roleService.findByName( TEST_ROLE ));
    }

    /**
     * Tests delete;
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteRole() throws Exception{
        Role role = getDefaultRole();

        roleService.create( role );
        Long id = role.getId();

        roleService.delete( role );
        assertNull( roleService.findById( id ) );
    }

    /**
     * Creates default Role instance;
     *
     * @return Role instance;
     */
    private static Role getDefaultRole(){
        Role role = new Role();

        role.setName( TEST_ROLE );
        return role;
    }
}
