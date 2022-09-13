package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface ToolsServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addTool(MasterAddDto toolBody, Long userId);

	void editTool(MasterAddDto toolBody, Long toolId, Long userId) throws ResourceNotFoundException;

	void deleteTool(Long toolId, Long userId) throws ResourceNotFoundException;

	void updateToolStatus(Long toolId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
