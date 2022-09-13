package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface ExternalComponentServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addExtComponent(MasterAddDto ecBody, Long userId);

	void editExtComponent(MasterAddDto ecBody, Long ecId, Long userId) throws ResourceNotFoundException;

	void deleteExtComponent(Long ecId, Long userId) throws ResourceNotFoundException;

	void updateExtComponentStatus(Long ecId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
