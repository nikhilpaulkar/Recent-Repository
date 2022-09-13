package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface CodingLanguageServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addCodeLang(MasterAddDto langBody, Long userId);

	void editCodeLang(MasterAddDto langBody, Long langId, Long userId) throws ResourceNotFoundException;

	void deleteCodeLang(Long langId, Long userId) throws ResourceNotFoundException;

	void updateCodeLangStatus(Long langId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
