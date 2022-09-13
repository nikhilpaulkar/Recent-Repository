package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface OperatingSystemServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addOs(MasterAddDto osBody, Long userId);

	void editOs(MasterAddDto osBody, Long osId, Long userId) throws ResourceNotFoundException;

	void deleteOs(Long osId, Long userId) throws ResourceNotFoundException;

	void updateOsStatus(Long osId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
