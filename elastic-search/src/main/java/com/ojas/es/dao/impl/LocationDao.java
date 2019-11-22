package com.ojas.es.dao.impl;

import com.ojas.es.dao.Dao;
import com.ojas.es.entity.Location;

/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link location}s.
 *
 * @author jyothi g. Sorst <jyothi@ojas-it.com>
 */
public interface LocationDao extends Dao<Location, Long>
{

	Long getLocaltionByName(String locationName);
}
