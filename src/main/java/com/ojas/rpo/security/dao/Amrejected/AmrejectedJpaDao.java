package com.ojas.rpo.security.dao.Amrejected;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.entity.Amrejected;
import com.ojas.rpo.security.entity.InterviewFeedback;

public class AmrejectedJpaDao extends JpaDao<Amrejected, Long>implements AmrejectedDao{

	public AmrejectedJpaDao(Class<Amrejected> entityClass) {
		super(entityClass);
		
	}
	public AmrejectedJpaDao() {
		super(Amrejected.class);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<Amrejected> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Amrejected> criteriaQuery = builder.createQuery(Amrejected.class);

		Root<Amrejected> root = criteriaQuery.from(Amrejected.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Amrejected> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
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
