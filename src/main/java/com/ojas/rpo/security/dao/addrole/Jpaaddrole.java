package com.ojas.rpo.security.dao.addrole;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Addrole;


public class Jpaaddrole extends JpaDao<Addrole, Long>implements AddroleDao {

	public Jpaaddrole() {
		super(Addrole.class);
	
}
	@Override
	@Transactional(readOnly = true)
	public List<Addrole> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Addrole> criteriaQuery = builder.createQuery(Addrole.class);

		Root<Addrole> root = criteriaQuery.from(Addrole.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Addrole> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	
}
