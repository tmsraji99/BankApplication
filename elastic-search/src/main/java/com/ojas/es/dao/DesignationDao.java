package com.ojas.es.dao;

import com.ojas.es.entity.Designation;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link location}s.
 *
 * @author Lakshmi.Allam
 */

public interface DesignationDao extends Dao<Designation, Long>{

	Long getDesignantionByName(String Designantion);

	}




