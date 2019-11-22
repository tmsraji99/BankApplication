package com.ojas.rpo.security.dao.incentive;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.IncentiveNew;
import com.ojas.rpo.security.entity.Response;

public interface IncentiveDao extends Dao<IncentiveNew, Long> {

	public Response getRecruiterIncentive(Long userId);

	public Response getAllRecruitersIncentives(List<CandidateData> users);

	Response getIncentiveList(String role, Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField);

}
