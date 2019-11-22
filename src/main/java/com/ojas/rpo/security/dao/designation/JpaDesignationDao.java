package com.ojas.rpo.security.dao.designation;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Designation;

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
	
	
}


