package com.ojas.es.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.es.entity.Location;

/**
 * 
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaLocationDao extends JpaDao<Location, Long> implements LocationDao {
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

	@Override
	public Long getLocaltionByName(String locationName) {

		Query q = getEntityManager().createNativeQuery("select id, locationName  from location where locationName= '"
				+ locationName + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		if(results!=null){
			for (Object obj[] : results) {
			return Long.parseLong(obj[0]+"");
		}

		
	}
		return 0L;
}
	
}
