package com.ojas.rpo.security.dao.status;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Status;

public class JpaStatusDao extends JpaDao<Status, Long> implements StatusDao {

	public JpaStatusDao() {
		super(Status.class);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Status> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Status> criteriaQuery = builder.createQuery(Status.class);

		Root<Status> root = criteriaQuery.from(Status.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Status> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
