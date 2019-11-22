package com.ojas.rpo.security.dao.userlist;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.UserDetailsList;

public class JpaUserListDao extends JpaDao<UserDetailsList, Long> implements UserListDao{
	 public JpaUserListDao()
	    {
	        super(UserDetailsList.class);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<UserDetailsList> findAll()
	    {
	        final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
	        final CriteriaQuery<UserDetailsList> criteriaQuery = builder.createQuery(UserDetailsList.class);

	        Root<UserDetailsList> root = criteriaQuery.from(UserDetailsList.class);
	        criteriaQuery.orderBy(builder.desc(root.get("date")));

	        TypedQuery<UserDetailsList> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
	        return typedQuery.getResultList();
	    }
	    

}
