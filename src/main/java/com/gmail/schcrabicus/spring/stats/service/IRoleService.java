package com.gmail.schcrabicus.spring.stats.service;

import com.gmail.schcrabicus.spring.stats.domain.Role;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 8:59
 * To change this template use File | Settings | File Templates.
 */
public interface IRoleService extends IService<Role, Long> {

    public Role findByName( String name );
}
