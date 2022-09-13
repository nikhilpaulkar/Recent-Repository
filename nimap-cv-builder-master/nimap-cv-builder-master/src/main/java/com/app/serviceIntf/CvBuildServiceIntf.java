package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.CvRequestAddDto;
import com.app.dto.ICvMasterDto;
import com.app.entities.CvRequestEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface CvBuildServiceIntf {

	public String createCv(CvRequestAddDto cvBody, Long userId, Long createdById) throws ResourceNotFoundException;

	public String cvBuilder(CvRequestEntity addedRequest) throws ResourceNotFoundException;

	public CvRequestEntity createCvRequest(CvRequestAddDto cvBody, Long userId, Long createdById)throws ResourceNotFoundException;

	List<CvRequestEntity> getAllEmployee();
	
	Page<ICvMasterDto> findAll(String search, String from, String to);
	
	 Page<ICvInfoMasterDto> findAllCv(String search, String from, String to);
	 
	 ICvInfoMasterDto findById(Long id) throws ResourceNotFoundException;
	 
	 void editCv(CvRequestAddDto cvBody, Long cvId, Long userId) throws ResourceNotFoundException;
 
}
