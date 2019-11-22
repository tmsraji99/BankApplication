package com.ojas.es.dao.impl;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.es.dao.Dao;
import com.ojas.es.entity.Entity;

public class JpaDao<T extends Entity,I> implements Dao<T, I> {
	
	private EntityManager entityManager;

	protected Class<T> entityClass;

	public JpaDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Transactional(readOnly = true)
	public List<T> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> criteriaQuery = builder.createQuery(this.entityClass);

		criteriaQuery.from(this.entityClass);

		TypedQuery<T> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Transactional(readOnly = true)
	public T find(I id) {
		return this.getEntityManager().find(this.entityClass, id);
	}

	@Transactional
	public T save(T entity) {
		return this.getEntityManager().merge(entity);
	}

	@Transactional(readOnly = true)
	public T delete(I id) {
		this.getEntityManager().remove(id);
		return this.getEntityManager().find(this.entityClass, id);
	}

	@Transactional
	public void delete(T entity) {
		this.getEntityManager().remove(entity);
	}

}
