package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Services;
import com.ojas.rpo.security.entity.Skill;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaServicesDao extends JpaDao<Services, Long>implements ServicesDao {
	public JpaServicesDao() {
		super(Services.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Services> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Services> criteriaQuery = builder.createQuery(Services.class);

		Root<Services> root = criteriaQuery.from(Services.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Services> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
}
