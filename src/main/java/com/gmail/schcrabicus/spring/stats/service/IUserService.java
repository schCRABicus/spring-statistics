package com.gmail.schcrabicus.spring.stats.service;

import com.gmail.schcrabicus.spring.stats.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 8:58
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService extends IService< User, Long> {

    public User findByLogin( String login );

    public Page<User> findAllByPage( Pageable pageable );
}
