package com.ojas.rpo.security.dao.candidateRequest;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateRequestByRecruiters;
import com.ojas.rpo.security.entity.CandidateRequestByRecruitersRequestDto;
import com.ojas.rpo.security.entity.Response;

public interface CandidateRequestByRecruitersDao extends Dao<CandidateRequestByRecruiters, Long> {

	Response saveRequest(CandidateRequestByRecruitersRequestDto dto);

	Response getAllcandidateRequestByRecruiters(Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField,Long loginId ,String user);

	Response getcandidateRequestByRecruitersId(Long id);

	CandidateRequestByRecruiters getRequestByCandidateAndRecruiter(Long candidateId, Long requestedUserId);
	
	public void deleteRequestByCandidateAndRecruiter(Long candidateId, Long requestedUserId);

}
