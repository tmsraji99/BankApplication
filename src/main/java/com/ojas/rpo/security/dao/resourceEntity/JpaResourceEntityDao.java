package com.ojas.rpo.security.dao.resourceEntity;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ResourceEntity;

public class JpaResourceEntityDao extends JpaDao<ResourceEntity, Long> implements ResourceEntityDao {

	public JpaResourceEntityDao() {
		super(ResourceEntity.class);
	}

	
	
	
	
}
