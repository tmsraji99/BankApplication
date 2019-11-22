package com.ojas.rpo.security.dao.incentiveRole;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.IncentiveRole;


public class JpaIncentiveRoleDao extends JpaDao<IncentiveRole,Long> implements IncentiveRoleDao {

	public JpaIncentiveRoleDao() {
		super(IncentiveRole.class);
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
    @Transactional
    public IncentiveRole save(IncentiveRole entity)
    {
        return this.getEntityManager().merge(entity);
    }

}

