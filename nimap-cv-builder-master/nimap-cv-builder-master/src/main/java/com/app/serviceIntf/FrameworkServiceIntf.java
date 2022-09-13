package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface FrameworkServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addFramework(MasterAddDto frameworkBody, Long userId);

	void editFramework(MasterAddDto frameworkBody, Long frameworkId, Long userId) throws ResourceNotFoundException;

	void deleteFramework(Long frameworkId, Long userId) throws ResourceNotFoundException;

	void updateFrameworkStatus(Long frameworkId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
