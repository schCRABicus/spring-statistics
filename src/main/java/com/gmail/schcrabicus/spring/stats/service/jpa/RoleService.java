package com.gmail.schcrabicus.spring.stats.service.jpa;

import com.gmail.schcrabicus.spring.stats.domain.Role;
import com.gmail.schcrabicus.spring.stats.repository.RoleRepository;
import com.gmail.schcrabicus.spring.stats.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;


/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 9:01
 * To change this template use File | Settings | File Templates.
 */
@Service( "roleServiceImpl" )
@Qualifier( "roleServiceImpl" )
@Repository
@Transactional
public class RoleService extends JpaService<Role, Long > implements IRoleService {

    /*@Autowired*/
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(@Qualifier( "roleRepository") RoleRepository repository) {
        super(repository);
        this.roleRepository = repository;
    }

    /*@PostConstruct
    private void initRepository(){
        this.setRepository( roleRepository );
    }*/

    @Override
    @Transactional( readOnly = true )
    public Role findByName(String name) {
        return roleRepository.findByName( name );
    }
}
