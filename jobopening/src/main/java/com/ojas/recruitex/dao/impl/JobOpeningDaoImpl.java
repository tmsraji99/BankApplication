package com.ojas.recruitex.dao.impl;

import com.ojas.recruitex.dao.JpaDao;
import com.ojas.recruitex.entity.JobOpening;

public class JobOpeningDaoImpl extends JpaDao<JobOpening, Long> implements JobOpeningDaoInf {

	public JobOpeningDaoImpl() {
		super(JobOpening.class);
		// TODO Auto-generated constructor stub
	}

}
