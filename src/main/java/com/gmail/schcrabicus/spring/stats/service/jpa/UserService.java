package com.gmail.schcrabicus.spring.stats.service.jpa;

import com.gmail.schcrabicus.spring.stats.domain.User;
import com.gmail.schcrabicus.spring.stats.repository.UserRepository;
import com.gmail.schcrabicus.spring.stats.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 9:25
 * To change this template use File | Settings | File Templates.
 */
@Service( "userServiceImpl" )
@Qualifier( "userServiceImpl" )
@Repository
@Transactional
public class UserService extends JpaService<User, Long > implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier( "userRepository" ) UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    @Transactional( readOnly = true )
    public User findByLogin(String login) {
        return userRepository.findByLogin( login );
    }

    @Override
    @Transactional( readOnly = true )
    public Page<User> findAllByPage(Pageable pageable) {
        return userRepository.findAll( pageable );
    }
}
