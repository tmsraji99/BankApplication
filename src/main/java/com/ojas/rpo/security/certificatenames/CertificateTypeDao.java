package com.ojas.rpo.security.certificatenames;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Certificate;

public interface CertificateTypeDao extends Dao<Certificate,Long>{

	void updateCandidateCertificateDetailsbyId(Long id, List<Certificate> certificates);

}
