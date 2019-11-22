package com.ojas.rpo.security.dao.Offered;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.Offered;

public class OfferedJpaDao  extends JpaDao<Offered, Long>implements OfferedDao{
	public OfferedJpaDao() {
		super(Offered.class);
	}
	@Override
	@Transactional(readOnly = true)
	public List<Offered> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Offered> criteriaQuery = builder.createQuery(Offered.class);

		Root<Offered> root = criteriaQuery.from(Offered.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Offered> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateCandiate(Long candiateId,String status) {
		//boolean result=false;
	Query q = getEntityManager().createNativeQuery("update Candidate set candidateStatus=?, statusLastUpdatedDate=now() where id =?");
	q.setParameter(1, status);
	q.setParameter(2, candiateId);
	int i=q.executeUpdate();
	/*if(i>0){
		return true;
	}
	return result;*/
	}
}
