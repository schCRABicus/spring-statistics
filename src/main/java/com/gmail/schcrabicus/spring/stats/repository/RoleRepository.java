package com.gmail.schcrabicus.spring.stats.repository;

import com.gmail.schcrabicus.spring.stats.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 8:55
 * To change this template use File | Settings | File Templates.
 */
/*@Component( "roleRepository" )*/
public interface RoleRepository extends CrudRepository<Role, Long> {

    public Role findByName( String name );
}
