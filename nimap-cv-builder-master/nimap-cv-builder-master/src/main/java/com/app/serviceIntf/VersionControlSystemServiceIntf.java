package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface VersionControlSystemServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addVersionControlSystem(MasterAddDto vcsBody, Long userId);

	void editVersionControlSystem(MasterAddDto vcsBody, Long vcsId, Long userId) throws ResourceNotFoundException;

	void deleteVersionControlSystem(Long vcsId, Long userId) throws ResourceNotFoundException;

	void updateVersionControlSystemStatus(Long vcsId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
