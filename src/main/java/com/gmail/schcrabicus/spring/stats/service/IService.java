package com.gmail.schcrabicus.spring.stats.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
public interface IService<T, ID extends Serializable > {

    public T findById( ID id );

    public List<T> findAll();

    public T create ( T object );

    public T update ( T object );

    public void delete ( T object );

    public Long count();

    public boolean exists ( ID id );
}
