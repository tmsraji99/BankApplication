package com.ojas.rpo.security.dao.typeofprocess;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Processtype;

public class JpaProcessDao extends JpaDao<Processtype, Long> implements ProcessDao {
	public JpaProcessDao() {
		super(Processtype.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Processtype> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Processtype> criteriaQuery = builder.createQuery(Processtype.class);

		Root<Processtype> root = criteriaQuery.from(Processtype.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Processtype> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateCandiate(Long candiateId, String status,Long recId,Long userId) {
		// boolean result=false;
		Query q = getEntityManager().createNativeQuery("update candidatemapping set candidateStatus=?, lastUpdatedDate=now() where candidate_id =? and bdmReq_id=? and mappedUser_id=?");
		q.setParameter(1, status);
		q.setParameter(2, candiateId);
		q.setParameter(3, recId);
		q.setParameter(4, userId);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}
	
	@Override
	@Transactional
	public void updateCandiate(Long candiateId,Long recId,Long userId) {
		// boolean result=false;
		Query q = getEntityManager().createNativeQuery("update candidatemapping set  lastUpdatedDate=now() where candidate_id =? and bdmReq_id=? and mappedUser_id=?");
		q.setParameter(1, candiateId);
		q.setParameter(2, recId);
		q.setParameter(3, userId);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}

	@Override
	@Transactional(readOnly = true)
	public Processtype findById(Long candidateId) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Processtype> criteriaQuery = builder.createQuery(this.entityClass);
		Root<Processtype> root = criteriaQuery.from(this.entityClass);
		Path<Long> namePath = root.get("candidateid");
		criteriaQuery.where(builder.equal(namePath, candidateId));
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Processtype> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Processtype> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
		// return users.listIterator().previous();
	}
}
