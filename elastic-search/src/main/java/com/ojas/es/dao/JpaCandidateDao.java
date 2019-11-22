package com.ojas.es.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.hibernate.CacheMode;
import org.hibernate.search.batchindexing.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.ojas.es.dao.impl.JpaDao;
import com.ojas.es.entity.Candidate;
import com.ojas.es.entity.CandidateSearchResponse;
import com.ojas.es.entity.IndexResponse;
import com.ojas.es.util.CandidateSearch;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaCandidateDao extends JpaDao<Candidate, Long> implements CandidatelistDao {
	public JpaCandidateDao() {
		super(Candidate.class);
	}

	@Transactional
	@Override
	public CandidateSearchResponse search(CandidateSearch search, Integer pageNo, Integer pageSize) {
		List<Candidate> result = null;
		CandidateSearchResponse candidateSearchResponse = new CandidateSearchResponse();
		try {
			EntityManager em = this.getEntityManager();
			FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
			SimpleIndexingProgressMonitor progressMonitor = new SimpleIndexingProgressMonitor();
			fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
			
//			fullTextEntityManager.createIndexer(Candidate.class)
//			.purgeAllOnStart(true)    //default
//			.optimizeAfterPurge(true)    //default
//			.optimizeOnFinish(true)      //default
//			.typesToIndexInParallel(8)
//            .batchSizeToLoadObjects(500)            
//            .threadsToLoadObjects(300)
//            .idFetchSize(1500)
//            .cacheMode(CacheMode.IGNORE)
//            .progressMonitor(progressMonitor)
//            .startAndWait();
			
			org.apache.lucene.search.Query query1 = null;
			org.apache.lucene.search.Query query2 = null;
			org.apache.lucene.search.Query query3 = null;
			org.apache.lucene.search.Query query4 = null;
			org.apache.lucene.search.Query query5 = null;
			org.apache.lucene.search.Query query6 = null;
			org.apache.lucene.search.Query query7 = null;
			org.apache.lucene.search.Query query8 = null;
			Query combinedQuery = null;
			// List<Query> list=new ArrayList<>();

			// create native Lucene query using the query DSL
			// alternatively you can write the Lucene query using the Lucene
			// query parser
			// or the Lucene programmatic API. The Hibernate Search DSL is
			// recommended
			// though

			QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class)
					.get();

			if (search.getDesignation() != null && !search.getDesignation().isEmpty()) {
				query1 = qb.keyword().onFields("appliedPossitionFor").matching(search.getDesignation()).createQuery();

			}

			if (search.getExp() != null && !search.getExp().isEmpty()) {
				query2 = qb.keyword().onFields("totalExperience").matching(search.getExp()).createQuery();
			}

			if (search.getSalary() != null && !search.getSalary().isEmpty()) {
				query3 = qb.keyword().onFields("currentCTC").matching(search.getSalary()).createQuery();
			}

			if (search.getSkill() != null && !search.getSkill().isEmpty()) {
				query4 = qb.keyword().onField("skills.skillName").matching(search.getSkill()).createQuery();
			}

			if (search.getLocation() != null && !search.getLocation().isEmpty()) {
				query5 = qb.keyword().onField("location.locationName").matching(search.getLocation()).createQuery();
			}

			if (search.getNoticePeriod() != null && !search.getNoticePeriod().isEmpty()) {
				query6 = qb.keyword().onField("noticePeriod").matching(search.getNoticePeriod()).createQuery();
			}

			if (search.getCurrentCompany() != null && !search.getCurrentCompany().isEmpty()) {
				query7 = qb.keyword().onField("currentCompanyName").matching(search.getCurrentCompany()).createQuery();
			}
			if (search.getCurrentLocation() != null && !search.getCurrentLocation().isEmpty()) {
				query8 = qb.keyword().onField("currentLocation").matching(search.getCurrentLocation()).createQuery();
			}

			// 8
			if (query1 != null && query2 != null && query3 != null && query5 != null && query6 != null && query7 != null
					&& query8 != null) {
				combinedQuery = qb.bool().must(query1).must(query2).must(query3).must(query4).must(query5).must(query6)
						.must(query7).must(query8).createQuery();
			}
			// 7
			else if (query1 != null && query2 != null && query3 != null && query5 != null && query6 != null
					&& query7 != null) {
				combinedQuery = qb.bool().must(query1).must(query2).must(query3).must(query4).must(query5).must(query7)
						.createQuery();
			} else if (query1 != null && query2 != null && query3 != null && query5 != null && query6 != null) {
				combinedQuery = qb.bool().must(query1).must(query2).must(query3).must(query4).must(query6).must(query5)
						.createQuery();
			} else if (query1 != null && query2 != null && query3 != null && query5 != null) {
				combinedQuery = qb.bool().must(query1).must(query2).must(query5).must(query4).must(query3)
						.createQuery();
			} else if (query1 != null && query2 != null && query3 != null) {
				combinedQuery = qb.bool().must(query1).must(query2).must(query3).must(query4).createQuery();
			} else if (query2 != null && query1 != null) {
				combinedQuery = qb.bool().must(query2).must(query1).must(query4).createQuery();

			} else if (query1 != null && query3 != null) {
				combinedQuery = qb.bool().must(query1).must(query3).must(query4).createQuery();

			} else if (query1 != null && query5 != null) {
				combinedQuery = qb.bool().must(query1).must(query5).must(query4).createQuery();

			} else if (query1 != null && query6 != null) {
				combinedQuery = qb.bool().must(query1).must(query6).must(query4).createQuery();

			} else if (query1 != null && query7 != null) {
				combinedQuery = qb.bool().must(query1).must(query7).must(query4).createQuery();

			} else if (query1 != null && query8 != null) {
				combinedQuery = qb.bool().must(query1).must(query8).must(query4).createQuery();

			} else if (query1 != null) {
				combinedQuery = qb.bool().must(query1).must(query4).createQuery();
			}

			else if (query2 != null && query3 != null && query5 != null && query6 != null && query7 != null
					&& query8 != null) {
				combinedQuery = qb.bool().must(query2).must(query3).must(query4).must(query5).must(query6).must(query7)
						.must(query8).createQuery();
			}
			// 7
			else if (query2 != null && query3 != null && query5 != null && query6 != null && query7 != null) {
				combinedQuery = qb.bool().must(query2).must(query3).must(query4).must(query5).createQuery();
			} else if (query2 != null && query3 != null && query5 != null && query6 != null) {
				combinedQuery = qb.bool().must(query2).must(query3).must(query4).must(query6).must(query5)
						.createQuery();
			} else if (query2 != null && query3 != null && query5 != null) {
				combinedQuery = qb.bool().must(query2).must(query5).must(query4).must(query3).createQuery();
			} else if (query2 != null && query3 != null) {
				combinedQuery = qb.bool().must(query2).must(query3).must(query4).createQuery();
			}
			else if (query2 != null && query5 != null) {
				combinedQuery = qb.bool().must(query2).must(query4).must(query5).createQuery();
			}
			
			else if (query2 != null && query6 != null) {
				combinedQuery = qb.bool().must(query2).must(query4).must(query6).createQuery();
			}
			else if (query2 != null && query7 != null) {
				combinedQuery = qb.bool().must(query2).must(query4).must(query7).createQuery();
			}
			else if (query2 != null && query8 != null) {
				combinedQuery = qb.bool().must(query2).must(query4).must(query8).createQuery();
			}
			else if (query2 != null) {
				combinedQuery = qb.bool().must(query2).must(query4).createQuery();
			}

			else if (query3 != null && query5 != null && query6 != null && query7 != null && query8 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query5).must(query8).createQuery();
			} else if (query3 != null && query5 != null && query6 != null && query7 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query6).must(query5).must(query7)
						.createQuery();
			} else if (query3 != null && query5 != null && query6 != null) {
				combinedQuery = qb.bool().must(query5).must(query4).must(query3).createQuery();
			} else if (query3 != null && query5 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query5).createQuery();
			} else if (query3 != null && query6 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query6).createQuery();
			}else if (query3 != null && query7 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query7).createQuery();
			}
			else if (query3 != null && query8 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).must(query8).createQuery();
			}
			else if (query3 != null) {
				combinedQuery = qb.bool().must(query3).must(query4).createQuery();
			}

			else if (query5 != null && query6 != null && query7 != null && query8 != null) {
				combinedQuery = qb.bool().must(query4).must(query5).must(query7).must(query6).must(query8)
						.createQuery();
			} else if (query5 != null && query6 != null && query7 != null) {
				combinedQuery = qb.bool().must(query4).must(query6).must(query5).must(query7).createQuery();
			} else if (query5 != null && query6 != null) {
				combinedQuery = qb.bool().must(query5).must(query6).must(query4).createQuery();
			}else if (query5 != null && query7 != null) {
				combinedQuery = qb.bool().must(query5).must(query7).must(query4).createQuery();
			}
			else if (query5 != null && query8 != null) {
				combinedQuery = qb.bool().must(query5).must(query8).must(query4).createQuery();
			}
			else if (query5 != null) {
				combinedQuery = qb.bool().must(query5).must(query4).createQuery();
			}

			else if (query6 != null && query7 != null && query8 != null) {
				combinedQuery = qb.bool().must(query4).must(query7).must(query6).must(query8).createQuery();
			} else if (query6 != null && query7 != null) {
				combinedQuery = qb.bool().must(query4).must(query6).must(query7).createQuery();
			} else if (query6 != null && query8 != null) {
				combinedQuery = qb.bool().must(query4).must(query6).must(query8).createQuery();
			} 
			
			else if (query6 != null) {
				combinedQuery = qb.bool().must(query4).must(query6).createQuery();
			}

			else if (query7 != null && query8 != null) {
				combinedQuery = qb.bool().must(query4).must(query7).must(query8).createQuery();
			} else if (query7 != null) {
				combinedQuery = qb.bool().must(query4).must(query7).createQuery();
			} else if (query8 != null) {
				combinedQuery = qb.bool().must(query4).must(query8).createQuery();
			}
			else if (query4 != null) {
				combinedQuery = qb.bool().must(query4).createQuery();
			}
			
			int startingRow = 0;
			if (pageNo == 1) {
				startingRow = 0;
			} else {
				startingRow = ((pageNo - 1) * pageSize);
			}
			
			javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(combinedQuery,
					Candidate.class).setFirstResult(startingRow).setMaxResults(pageSize); //pagination setFirstResult(0) is page number and setMaxResults(10) is page size

			javax.persistence.Query persQuery = fullTextEntityManager.createFullTextQuery(combinedQuery,
					Candidate.class);
			int totalRecords = persQuery.getResultList().size();
			candidateSearchResponse.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				candidateSearchResponse.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				candidateSearchResponse.setTotalPages(totalPages);
			}
			
			
			
			// SearchResults<Book> searchResult =
			// ElasticSearch.query(QueryBuilders.queryString(search),
			// Book.class).from(i).size(j).fetch();

			// execute search
			result = persistenceQuery.getResultList();
			
			candidateSearchResponse.setCandidates(result);

			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return candidateSearchResponse;
	}

	@Override
	@Transactional
	public List<Candidate> searchDesignationByKeywordQuery(CandidateSearch text) {

		Query keywordQuery = getQueryBuilder().keyword().onField("appliedPossitionFor").matching(text.getDesignation())
				.createQuery();

		List<Candidate> results = getJpaQuery(keywordQuery).getResultList();

		return results;
	}

	public List<Candidate> searchProductNameByFuzzyQuery(String text) {

		Query fuzzyQuery = getQueryBuilder().keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0)
				.onField("productName").matching(text).createQuery();

		List<Candidate> results = getJpaQuery(fuzzyQuery).getResultList();

		return results;
	}

	public List<Candidate> searchProductNameByWildcardQuery(String text) {

		Query wildcardQuery = getQueryBuilder().keyword().wildcard().onField("productName").matching(text)
				.createQuery();

		List<Candidate> results = getJpaQuery(wildcardQuery).getResultList();

		return results;
	}

	public List<Candidate> searchProductDescriptionByPhraseQuery(String text) {

		Query phraseQuery = getQueryBuilder().phrase().withSlop(1).onField("description").sentence(text).createQuery();

		List<Candidate> results = getJpaQuery(phraseQuery).getResultList();

		return results;
	}

	public List<Candidate> searchProductNameAndDescriptionBySimpleQueryStringQuery(String text) {

		Query simpleQueryStringQuery = getQueryBuilder().simpleQueryString().onFields("productName", "description")
				.matching(text).createQuery();

		List<Candidate> results = getJpaQuery(simpleQueryStringQuery).getResultList();

		return results;
	}

	private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {
		EntityManager em = this.getEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		return fullTextEntityManager.createFullTextQuery(luceneQuery, Candidate.class);
	}

	private QueryBuilder getQueryBuilder() {
		EntityManager em = this.getEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		return fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
	}

	@Transactional
	@Override
	public IndexResponse candidateIndex(){
		IndexResponse response = new IndexResponse();
		EntityManager em = this.getEntityManager();
		try{
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		SimpleIndexingProgressMonitor progressMonitor = new SimpleIndexingProgressMonitor();
		//fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Candidate.class).get();
		
		fullTextEntityManager.createIndexer(Candidate.class)
		.purgeAllOnStart(true)    //default
		.optimizeAfterPurge(true)    //default
		.optimizeOnFinish(true)      //default
		.typesToIndexInParallel(8)
        .batchSizeToLoadObjects(500)            
        .threadsToLoadObjects(300)
        .idFetchSize(1500)
        .cacheMode(CacheMode.IGNORE)
        .progressMonitor(progressMonitor)
        .startAndWait();
		response.setCode(200);
		response.setMessage("Cadidate details indexed");
		}catch (InterruptedException e) {
			response.setCode(415);
			response.setMessage("Cadidate details indexed failed");
			// TODO: handle exception
		}
		return response;
	}
}
