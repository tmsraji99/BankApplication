package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Location;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaLocationDao extends JpaDao<Location, Long>implements LocationDao {
	public JpaLocationDao() {
		super(Location.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Location> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);

		Root<Location> root = criteriaQuery.from(Location.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Location> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
}
