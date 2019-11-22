package com.ojas.rpo.security.dao.incentiveslab;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.IncentiveSlab;

public class JpaIncentiveSlabDao extends JpaDao<IncentiveSlab, Long>implements IncentiveSlabDao {
	public JpaIncentiveSlabDao() {
		super(IncentiveSlab.class);
	}
		@Override
		@Transactional(readOnly = true)
		public List<IncentiveSlab> findAll() {
			final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<IncentiveSlab> criteriaQuery = builder.createQuery(IncentiveSlab.class);

			Root<IncentiveSlab> root = criteriaQuery.from(IncentiveSlab.class);
			criteriaQuery.orderBy(builder.desc(root.get("date")));

			TypedQuery<IncentiveSlab> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
			return typedQuery.getResultList();
		}
	
}

 

