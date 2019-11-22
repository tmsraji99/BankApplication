package com.ojas.rpo.security.dao.typeOfAddres;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.TypeOfAddress;

public class JpaTypeOfAddressDao extends JpaDao<TypeOfAddress, Long>  implements TypeOfAddressDao{

	public JpaTypeOfAddressDao() {
		super(TypeOfAddress.class);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TypeOfAddress> findByBillingAddress() {
		
		
		String hql = " FROM TypeOfAddress where typeofaddress in ('Billing','Shipping')";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();
		// return users.listIterator().previous();
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TypeOfAddress> findByOfficeAddress() {
		
		
		String hql = " FROM TypeOfAddress where typeofaddress in ('Office','Residence')";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();
		// return users.listIterator().previous();
		
	}

}
