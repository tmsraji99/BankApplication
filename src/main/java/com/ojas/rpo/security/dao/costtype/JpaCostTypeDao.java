package com.ojas.rpo.security.dao.costtype;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CostType;

public class JpaCostTypeDao extends JpaDao<CostType, Long>implements CostTypeDao {
public JpaCostTypeDao(){
	super(CostType.class);
}
@Override
@Transactional(readOnly = true)
public List<CostType> findAll() {
	final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<CostType> criteriaQuery = builder.createQuery(CostType.class);

	Root<CostType> root = criteriaQuery.from(CostType.class);
	criteriaQuery.orderBy(builder.desc(root.get("id")));

	TypedQuery<CostType> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
	return typedQuery.getResultList();
}

}
