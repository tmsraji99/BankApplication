package com.ojas.rpo.security.dao.incentiveconfigs;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.IncentiveConfigurations;

public class JpaIncentiveConfigDao extends JpaDao<IncentiveConfigurations, Long> implements IncentiveConfigDao {

	public JpaIncentiveConfigDao() {
		super(IncentiveConfigurations.class);
	}

	
	
}
