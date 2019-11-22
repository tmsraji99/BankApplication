package com.ojas.rpo.security.dao.ContactsAddressMapping;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.ContactAddressDetails;

public class JpaContactAddressMapDao extends JpaDao<ContactAddressDetails,Long> implements ContactAddressMapDao{

	

	public JpaContactAddressMapDao() {
		super(ContactAddressDetails.class);
		
	}

	@Override
	public List<AddressDetails> findById(Long id) {
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();  
		CriteriaQuery<AddressDetails> cq = cb.createQuery(AddressDetails.class);  
		Root<AddressDetails> e = cq.from(AddressDetails.class);  
		Join<AddressDetails, AddressDetails> r = e.join("client", JoinType.LEFT);  
		Predicate p = cb.equal(r.get("id"), id);  
		cq.where(p);  
		TypedQuery<AddressDetails> tq = getEntityManager().createQuery(cq);  
		return tq.getResultList();
	}
	
	@Override
	public List<ContactAddressDetails> findBycpId(Long cpid) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<ContactAddressDetails> criteriaQuery = builder.createQuery(ContactAddressDetails.class);
		Root<ContactAddressDetails> root = criteriaQuery.from(ContactAddressDetails.class);
		Predicate p = builder.equal(root.get("cid"), cpid);
		criteriaQuery.where(p);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<ContactAddressDetails> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
	
	
}
	