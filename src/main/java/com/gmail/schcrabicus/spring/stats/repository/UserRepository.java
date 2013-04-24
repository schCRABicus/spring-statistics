package com.gmail.schcrabicus.spring.stats.repository;

import com.gmail.schcrabicus.spring.stats.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 8:54
 * To change this template use File | Settings | File Templates.
 */
/*@Component( "userRepository" )*/
public interface UserRepository extends PagingAndSortingRepository< User, Long> {

    public User findByLogin( String login );
}
