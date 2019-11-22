package com.ojas.rpo.security.dao.companytaxinfo;

import java.util.List;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CompanyTaxInfo;



public class JpaCompanyTaxInfoDao extends JpaDao<CompanyTaxInfo, Long> implements CompanyTaxInfoDao{
	public JpaCompanyTaxInfoDao() {
		super(CompanyTaxInfo.class);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public List<CompanyTaxInfo> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<CompanyTaxInfo> criteriaQuery = builder.createQuery(CompanyTaxInfo.class);

		Root<CompanyTaxInfo> root = criteriaQuery.from(CompanyTaxInfo.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<CompanyTaxInfo> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */




	
}
