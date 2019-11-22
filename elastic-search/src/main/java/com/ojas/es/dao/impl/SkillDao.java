package com.ojas.es.dao.impl;

import java.util.List;

import com.ojas.es.dao.Dao;
import com.ojas.es.entity.Skill;





public interface SkillDao extends Dao<Skill, Long>
{

	void updateSkillsByCandidateId(Long id, List<Skill> skills);

	Long getSkillByName(String skillName);
}
