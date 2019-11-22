package com.ojas.rpo.security.dao.InterviewDetails;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewDetails;


public interface InterviewDetailsDao extends Dao<InterviewDetails,Long>{
	
	public List<InterviewDetails>  getInterviewDetailsByCandidateIdAndStatus(Long candidateId, String status,
			Long requimentId, Long userId);
	public InterviewDetails getInterviewDetailsByCandidateId(Long candidateId, Long requimentId,
			Long userId);
	public InterviewDetails updateInterviewStatus(Long candidateId, Long requimentId,
			Long userId, String activestatus);
	public void removeInterviewDetailsByCandidateId(Long candidateId, Long requimentId,
			Long userId);
}
