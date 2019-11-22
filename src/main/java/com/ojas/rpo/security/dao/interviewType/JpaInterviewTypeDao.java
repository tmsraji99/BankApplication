package com.ojas.rpo.security.dao.interviewType;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewType;

public class JpaInterviewTypeDao extends JpaDao<InterviewType,Long> implements InterviewTypeDao {

	public JpaInterviewTypeDao() {
		super(InterviewType.class);
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
    @Transactional
    public InterviewType save(InterviewType entity)
    {
        return this.getEntityManager().merge(entity);
    }

}
