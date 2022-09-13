package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface WebServicesServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addWebServices(MasterAddDto wsBody, Long userId);

	void editWebServices(MasterAddDto wsBody, Long wsId, Long userId) throws ResourceNotFoundException;

	void deleteWebServices(Long wsId, Long userId) throws ResourceNotFoundException;

	void updateWebServicesStatus(Long wsId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
