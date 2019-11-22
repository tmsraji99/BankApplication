package com.ojas.rpo.security.dao.Amquery;

import java.util.List;


import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Amquery;

public class AmQueryJpaDao extends JpaDao<Amquery, Long> implements AmQuryDao {
	public AmQueryJpaDao(Class<Amquery> entityClass) {
		super(entityClass);

	}

	public AmQueryJpaDao() {
		super(Amquery.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Amquery findById(Long candidateId) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Amquery> criteriaQuery = builder.createQuery(this.entityClass);

		Root<Amquery> root = criteriaQuery.from(this.entityClass);
		Path<Long> namePath = root.get("candidateid");
		criteriaQuery.where(builder.equal(namePath, candidateId));
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Amquery> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Amquery> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
		// return users.listIterator().previous();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Amquery> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Amquery> criteriaQuery = builder.createQuery(Amquery.class);

		Root<Amquery> root = criteriaQuery.from(Amquery.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Amquery> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateCandiate(Long candiateId, String status) {
		// boolean result=false;
		Query q = getEntityManager().createNativeQuery("update Candidate set candidateStatus=?, statusLastUpdatedDate=now() where id =?");
		q.setParameter(1, status);
		q.setParameter(2, candiateId);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result; }
		 */

	}
}
