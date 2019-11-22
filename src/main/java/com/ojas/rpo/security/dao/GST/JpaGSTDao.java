package com.ojas.rpo.security.dao.GST;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.GST;
import com.ojas.rpo.security.entity.Response;

public class JpaGSTDao extends JpaDao<GST, Long> implements GSTDao{

	public JpaGSTDao() {
		super(GST.class);
	}

	
}
