package com.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.entities.UserEntity;
import com.app.entities.WebServiceMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.WebServicesRepository;
import com.app.serviceIntf.WebServicesServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("webServicesServiceImpl")
public class WebServicesServiceImpl implements WebServicesServiceIntf {

	@Autowired
	private WebServicesRepository webServicesRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> webServiceList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			webServiceList = webServicesRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			webServiceList = webServicesRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return webServiceList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto webService = webServicesRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Web Service Not Found"));
		return webService;

	}

	@Override
	public void addWebServices(MasterAddDto wsBody, Long userId) {

		WebServiceMasterEntity newWebService = new WebServiceMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newWebService.setCreatedBy(userDetail);
		newWebService.setDescription(wsBody.getDescription());
		newWebService.setName(wsBody.getName());
		newWebService.setUpdatedBy(userDetail);
		webServicesRepository.save(newWebService);
		return;

	}

	@Override
	public void editWebServices(MasterAddDto wsBody, Long wsId, Long userId) throws ResourceNotFoundException {

		WebServiceMasterEntity webServiceDetail = webServicesRepository.findByIdAndIsActiveTrue(wsId).orElseThrow(() -> new ResourceNotFoundException("Web Service Not Found"));
		webServiceDetail.setDescription(wsBody.getDescription());
		webServiceDetail.setName(wsBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		webServiceDetail.setUpdatedBy(userDetail);
		webServicesRepository.save(webServiceDetail);
		return;

	}

	@Override
	public void deleteWebServices(Long wsId, Long userId) throws ResourceNotFoundException {

		WebServiceMasterEntity webServiceDetail = webServicesRepository.findByIdAndIsActiveTrue(wsId).orElseThrow(() -> new ResourceNotFoundException("Web Service Not Found"));
		webServiceDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		webServiceDetail.setUpdatedBy(userDetail);
		webServicesRepository.save(webServiceDetail);
		return;

	}

	@Override
	public void updateWebServicesStatus(Long wsId, Long userId) throws ResourceNotFoundException {

		WebServiceMasterEntity webServiceDetail = webServicesRepository.findById(wsId).orElseThrow(() -> new ResourceNotFoundException("Web Service Not Found"));
		webServiceDetail.setIsActive(!webServiceDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		webServiceDetail.setUpdatedBy(userDetail);
		webServicesRepository.save(webServiceDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> webServices = webServicesRepository.findByIsActiveTrue(MasterListDto.class);
		return webServices;

	}

}
