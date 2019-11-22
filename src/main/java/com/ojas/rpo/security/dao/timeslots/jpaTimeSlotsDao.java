package com.ojas.rpo.security.dao.timeslots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.TimeSlot;

public class jpaTimeSlotsDao extends JpaDao<TimeSlot, Long> implements TimeSlotDao {

	public jpaTimeSlotsDao() {
		super(TimeSlot.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TimeSlot getTimeSlotsByCandiateId(Long candidateId,Long reqId) {
		// TODO Auto-generated method stub
		/*
		 * TimeSlot tm=null;
		 * 
		 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		 * CriteriaQuery<TimeSlot> cq = cb.createQuery(TimeSlot.class); Root<TimeSlot> e
		 * = cq.from(TimeSlot.class); Join<TimeSlot, TimeSlot> r = e.join("candidate",
		 * JoinType.LEFT); Predicate p = cb.equal(r.get("id"), reqId); cq.where(p);
		 * TypedQuery<TimeSlot> tq = getEntityManager().createQuery(cq); List<TimeSlot>
		 * timeslot=tq.getResultList();
		 * 
		 * if (timeslot.size() > 0) { tm = (TimeSlot) timeslot.get(0); } return tm;
		 */
		TimeSlot tm=null;

		Query q = getEntityManager().createQuery(" from TimeSlot where candidate_id=? and requirement_id=?");
		q.setParameter(1,candidateId);
		q.setParameter(2,reqId);
		List<TimeSlot> timeslot = q.getResultList();
		if (timeslot.size() > 0) {
			tm = (TimeSlot) timeslot.get(0);
		}
		return tm;
	}
	
	

	

	
}
