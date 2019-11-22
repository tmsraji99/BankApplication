package com.ojas.rpo.security.dao.paymentTerms;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.PaymentTerms;

public class JpaPaymentTermsDao extends JpaDao<PaymentTerms, Long> implements PaymentTermsDao {
	public JpaPaymentTermsDao() {
		super(PaymentTerms.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PaymentTerms> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<PaymentTerms> criteriaQuery = builder.createQuery(PaymentTerms.class);

		Root<PaymentTerms> root = criteriaQuery.from(PaymentTerms.class);

		TypedQuery<PaymentTerms> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
