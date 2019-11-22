package com.ojas.rpo.security.dao.customerType;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CustomerType;


public class JpaCustomerTypeDao extends JpaDao<CustomerType, Long> implements CustomerTypeDao {
	public JpaCustomerTypeDao() {
		super(CustomerType.class);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CustomerType> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<CustomerType> criteriaQuery = builder.createQuery(CustomerType.class);

		Root<CustomerType> root = criteriaQuery.from(CustomerType.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<CustomerType> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
