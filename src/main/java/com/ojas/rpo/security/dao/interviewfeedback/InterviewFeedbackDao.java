package com.ojas.rpo.security.dao.interviewfeedback;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewFeedback;

public interface InterviewFeedbackDao extends Dao<InterviewFeedback, Long> {
	

	void updateCandiate(Long candiateId,String status,Long reqId,Long userId);
	InterviewFeedback findById(Long candidateId);
	void updateInterviewDetails(Long candiateId, String status, String nameOftheRound,Long reqId,Long useid );
	void removeInterviewFeedbackByCandidateId(Long id);

}