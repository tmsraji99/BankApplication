package com.ojas.rpo.security.dao.noticePeriod;

import java.util.List;
/**
 * 
 * @author Nagajyothi.R
 *
 */

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.NoticePeriod;

public class JpaNoticePeriodDao extends JpaDao<NoticePeriod, Long> implements NoticePeriodDao{

	public JpaNoticePeriodDao() {
		super(NoticePeriod.class);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<NoticePeriod> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<NoticePeriod> criteriaQuery = builder.createQuery(NoticePeriod.class);

		Root<NoticePeriod> root = criteriaQuery.from(NoticePeriod.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<NoticePeriod> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
