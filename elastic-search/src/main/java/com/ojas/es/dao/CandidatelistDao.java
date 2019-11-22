package com.ojas.es.dao;

import java.util.List;

import com.ojas.es.entity.Candidate;
import com.ojas.es.entity.CandidateSearchResponse;
import com.ojas.es.entity.IndexResponse;
import com.ojas.es.util.CandidateSearch;

public interface CandidatelistDao extends Dao<Candidate, Long> {

	List<Candidate> searchDesignationByKeywordQuery(CandidateSearch text);

	CandidateSearchResponse search(CandidateSearch search, Integer pageNo, Integer pageSize);
	IndexResponse candidateIndex();

	//List<Candidate> search(CandidateSearch search);

	
	
}
