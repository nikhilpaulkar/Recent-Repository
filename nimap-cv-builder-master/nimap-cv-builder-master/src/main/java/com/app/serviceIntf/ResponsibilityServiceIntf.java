package com.app.serviceIntf;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.app.dto.IResponsibilityDetailDto;
import com.app.dto.ResponsibilityAddDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface ResponsibilityServiceIntf {

 
	public void addResponsibility(ResponsibilityAddDto reqBody, Long userId) throws ResourceNotFoundException;
	
 
	public void editResponsibility(ResponsibilityAddDto reqBody, Long resposibilityId, Long userId) throws ResourceNotFoundException;

	public void deleteResponsibility(Long resposibilityId, Long userId) throws ResourceNotFoundException;

	public Page<IResponsibilityDetailDto> getResponsibilities(String search, String from, String to);

	public IResponsibilityDetailDto getResponsibilityById(Long resposibilityId) throws ResourceNotFoundException;

	public void updateResponsibilityStatusById(Long resposibilityId, Long userId) throws ResourceNotFoundException;

	public ArrayList<IResponsibilityDetailDto> getResponsibilityByCodeLangId(Long[] id);

}
