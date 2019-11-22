package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;

import com.ojas.rpo.security.entity.Skill;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaSkillDao extends JpaDao<Skill, Long>implements SkillDao {
	public JpaSkillDao() {
		super(Skill.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Skill> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Skill> criteriaQuery = builder.createQuery(Skill.class);

		Root<Skill> root = criteriaQuery.from(Skill.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Skill> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateSkillsByCandidateId(Long id, List<Skill> skills) {
		
		if (null != skills) {
			Query skillDeleteQuery = this.getEntityManager().createNativeQuery(" DELETE FROM skillcandidate WHERE candidate_ID = ? ");
			skillDeleteQuery.setParameter(1, id);
			skillDeleteQuery.executeUpdate();
			
			for (Skill skillData : skills) {
				Query addSkillQuery = this.getEntityManager().createNativeQuery("INSERT INTO skillcandidate(candidate_ID,SKILL_ID) values(?,?)");
				addSkillQuery.setParameter(1, id);
				addSkillQuery.setParameter(2, skillData.getId());
				addSkillQuery.executeUpdate();
			}	
		}
		
	}
	
	
}
