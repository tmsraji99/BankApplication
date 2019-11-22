package com.ojas.rpo.security.dao.companyAddressinfo;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.companytaxinfo.CompanyTaxInfoDao;
import com.ojas.rpo.security.entity.BankAndAddressDetails;
import com.ojas.rpo.security.entity.CompanyTaxInfo;

public class JpaCompanyAddressInfoDao extends JpaDao<BankAndAddressDetails, Long> implements CompanyAddressInfoDao{
	public JpaCompanyAddressInfoDao() {
		super(BankAndAddressDetails.class);
		// TODO Auto-generated constructor stub
	}

	

	
	@Override
	public List<BankAndAddressDetails> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<BankAndAddressDetails> criteriaQuery = builder.createQuery(BankAndAddressDetails.class);

		Root<BankAndAddressDetails> root = criteriaQuery.from(BankAndAddressDetails.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<BankAndAddressDetails> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
