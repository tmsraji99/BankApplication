package com.ojas.rpo.security.dao.timeslots;


import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.TimeSlot;

public interface TimeSlotDao extends Dao<TimeSlot,Long> {
	
	public TimeSlot getTimeSlotsByCandiateId(Long candidateId,Long reqId);
	
}
