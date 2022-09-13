package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface IdeServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addIde(MasterAddDto ideBody, Long userId);

	void editIde(MasterAddDto ideBody, Long ideId, Long userId) throws ResourceNotFoundException;

	void deleteIde(Long ideId, Long userId) throws ResourceNotFoundException;

	void updateIdeStatus(Long ideId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
