package com.ojas.rpo.security.dao.InterviewDetails;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewDetails;

public class JpaInterviewDetailsDao extends JpaDao<InterviewDetails, Long> implements InterviewDetailsDao {

	public JpaInterviewDetailsDao() {
		super(InterviewDetails.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * @Override public InterviewDetails getCandiateByCandidateIdAndStatus(Long
	 * candidateId) {
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	 * CriteriaQuery<InterviewDetails> cq =
	 * cb.createQuery(InterviewDetails.class); Root<InterviewDetails> e =
	 * cq.from(InterviewDetails.class); Join<InterviewDetails, InterviewDetails>
	 * r = e.join("candidate", JoinType.LEFT); Predicate predicates =
	 * cb.and(cb.equal(r.get("id"), candidateId)); cq.where(predicates);
	 * TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
	 * InterviewDetails resultList = tq.getSingleResult();
	 * System.out.println("Result"+resultList.getAddress()); return resultList;
	 * 
	 * }
	 */

	@Override
	public List<InterviewDetails> getInterviewDetailsByCandidateIdAndStatus(Long candidateId, String status,
			Long requimentId, Long userId) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> cq = getCriteriaQuery();
		Root<InterviewDetails> e = cq.from(InterviewDetails.class);
		Join<InterviewDetails, InterviewDetails> r = e.join("candidate", JoinType.LEFT);
		Join<InterviewDetails, InterviewDetails> r1 = e.join("requirement", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), candidateId), cb.equal(e.get("status"), status),
				cb.equal(r1.get("id"), requimentId), cb.equal(e.get("userId"), userId));

		cq.where(predicates);
		TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
		List<InterviewDetails> resultList = tq.getResultList();
		return resultList;

	}

	public InterviewDetails getInterviewDetailsByCandidateId(Long candidateId, Long requimentId, Long userId) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> cq = getCriteriaQuery();
		Root<InterviewDetails> e = cq.from(InterviewDetails.class);
		Join<InterviewDetails, InterviewDetails> r = e.join("candidate", JoinType.LEFT);
		Join<InterviewDetails, InterviewDetails> r1 = e.join("requirement", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), candidateId), cb.equal(r1.get("id"), requimentId),
				cb.equal(e.get("userId"), userId));

		cq.where(predicates);
		TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
		List<InterviewDetails> resultList = tq.getResultList();
		if (resultList.isEmpty()) {
			return null;
		}

		return resultList.get(0);
	}

	private CriteriaBuilder getCriteriaBuilder() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		return criteriaBuilder;
	}

	private CriteriaQuery<InterviewDetails> getCriteriaQuery() {
		CriteriaQuery<InterviewDetails> criteriaQuery = getCriteriaBuilder().createQuery(InterviewDetails.class);
		return criteriaQuery;
	}

	@Transactional
	public InterviewDetails updateInterviewStatus(Long candidateId, Long requimentId, Long userId,
			String activestatus) {
		InterviewDetails data = getInterviewDetailsByCandidateId(candidateId, requimentId, userId);
		// data.setStatus("Waiting for Interview Feedback");
		data.setStatus(activestatus);
		data.setLastUpdatedDate(new Date());
		InterviewDetails updateData = save(data);
		return updateData;
	}

	@Override
	public void removeInterviewDetailsByCandidateId(Long candidateId, Long requimentId, Long userId) {
		String sql = " DELETE FROM interviewdetails  WHERE candidate_id =" + candidateId + " and requiment_id="
				+ requimentId + " and userId=" + userId;
		Query query = getEntityManager().createNativeQuery(sql);
		query.executeUpdate();

	}

}
