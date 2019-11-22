package com.ojas.es.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.es.dao.impl.JpaDao;
import com.ojas.es.entity.Designation;



public class JpaDesignationDao extends JpaDao<Designation, Long>implements DesignationDao {
	public JpaDesignationDao() {
		super(Designation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Designation> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Designation> criteriaQuery = builder.createQuery(Designation.class);

		Root<Designation> root = criteriaQuery.from(Designation.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Designation> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
	
	@Override
	public Long getDesignantionByName(String Designantion) {

		Query q = getEntityManager().createNativeQuery("select id, designation  from designation where designation= '"
				+ Designantion + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		if(results!=null){
			for (Object obj[] : results) {
			return Long.parseLong(obj[0]+"");
		}

		
	}
		return 0L;
}
}


