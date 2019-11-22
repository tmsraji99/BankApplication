package com.ojas.recruitex.dao;

import java.util.List;

import com.ojas.recruitex.entity.Entity;

public interface Dao<T extends Entity, I> {
	List<T> findAll();

	T find(I id);

	T save(T entity);

	T delete(I id);

	void delete(T entity);

}
