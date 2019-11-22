package com.ojas.rpo.security.dao.slab;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Slab;
public class JpaSlabDao extends JpaDao<Slab, Long>implements SlabDao {
	public JpaSlabDao() {
		super(Slab.class);
	}
		@Override
		@Transactional(readOnly = true)
		public List<Slab> findAll() {
			final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Slab> criteriaQuery = builder.createQuery(Slab.class);

			Root<Slab> root = criteriaQuery.from(Slab.class);
			criteriaQuery.orderBy(builder.desc(root.get("date")));

			TypedQuery<Slab> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
			return typedQuery.getResultList();
		}
	
}
