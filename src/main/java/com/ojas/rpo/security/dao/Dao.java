package com.ojas.rpo.security.dao;

import java.util.List;

import com.ojas.rpo.security.entity.Entity;
import com.ojas.rpo.security.entity.User;

public interface Dao<T extends Entity, I>
{
    List<T> findAll();
    
   
    T find(I id);

    T save(T entity);


    T delete(I id);

    void delete(T entity);

	
}
