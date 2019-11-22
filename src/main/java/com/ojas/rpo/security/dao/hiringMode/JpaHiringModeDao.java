package com.ojas.rpo.security.dao.hiringMode;


import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;

import com.ojas.rpo.security.entity.HiringMode;


public class JpaHiringModeDao  extends JpaDao<HiringMode, Long>implements HiringModeDao {
	public JpaHiringModeDao() {
		super(HiringMode.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HiringMode> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<HiringMode> criteriaQuery = builder.createQuery(HiringMode.class);

		Root<HiringMode> root = criteriaQuery.from(HiringMode.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<HiringMode> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
}

