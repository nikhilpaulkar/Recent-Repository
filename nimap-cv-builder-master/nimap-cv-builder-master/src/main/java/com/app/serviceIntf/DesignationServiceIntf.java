package com.app.serviceIntf;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface DesignationServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addDesignation(MasterAddDto designationBody, Long userId);

	void editDesignation(MasterAddDto designationBody, Long designationId, Long userId) throws ResourceNotFoundException;

	void deleteDesignation(Long designationId, Long userId) throws ResourceNotFoundException;

	void updateDesignationStatus(Long designationId, Long userId) throws ResourceNotFoundException;

}
