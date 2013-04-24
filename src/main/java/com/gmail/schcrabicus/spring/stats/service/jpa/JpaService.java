package com.gmail.schcrabicus.spring.stats.service.jpa;

import com.gmail.schcrabicus.spring.stats.service.IService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 9:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class JpaService< T , ID extends Serializable > implements IService< T , ID > {

    private CrudRepository repository;

    public JpaService( CrudRepository repository ){
        this.repository = repository;
    }

    public JpaService(){

    }

    public CrudRepository getRepository() {
        return repository;
    }

    public void setRepository(CrudRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional( readOnly = true )
    public T findById(ID id) {
        return ( T ) repository.findOne( id );
    }

    @Override
    @Transactional( readOnly = true )
    public List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public T create(T object) {
        return ( T ) repository.save( object );
    }

    @Override
    public T update(T object) {
        return ( T ) repository.save( object );
    }

    @Override
    public void delete(T object) {
        repository.delete( object );
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public boolean exists(ID id) {
        return repository.exists( id );
    }
}
