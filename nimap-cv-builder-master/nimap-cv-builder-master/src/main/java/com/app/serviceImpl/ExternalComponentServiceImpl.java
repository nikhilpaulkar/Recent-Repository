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
import com.app.entities.ExternalComponentMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ExternalComponentRepository;
import com.app.serviceIntf.ExternalComponentServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("externalComponentServiceImpl")
public class ExternalComponentServiceImpl implements ExternalComponentServiceIntf {

	@Autowired
	private ExternalComponentRepository externalComponentRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> extComponentList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			extComponentList = externalComponentRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			extComponentList = externalComponentRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return extComponentList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto extComponent = externalComponentRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("External Component Not Found"));
		return extComponent;

	}

	@Override
	public void addExtComponent(MasterAddDto ecBody, Long userId) {

		ExternalComponentMasterEntity newExtComp = new ExternalComponentMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newExtComp.setCreatedBy(userDetail);
		newExtComp.setDescription(ecBody.getDescription());
		newExtComp.setName(ecBody.getName());
		newExtComp.setUpdatedBy(userDetail);
		externalComponentRepository.save(newExtComp);
		return;

	}

	@Override
	public void editExtComponent(MasterAddDto ecBody, Long ecId, Long userId) throws ResourceNotFoundException {

		ExternalComponentMasterEntity extComp = externalComponentRepository.findByIdAndIsActiveTrue(ecId).orElseThrow(() -> new ResourceNotFoundException("External Component Not Found"));
		extComp.setDescription(ecBody.getDescription());
		extComp.setName(ecBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		extComp.setUpdatedBy(userDetail);
		externalComponentRepository.save(extComp);
		return;

	}

	@Override
	public void deleteExtComponent(Long ecId, Long userId) throws ResourceNotFoundException {

		ExternalComponentMasterEntity extComp = externalComponentRepository.findByIdAndIsActiveTrue(ecId).orElseThrow(() -> new ResourceNotFoundException("External Component Not Found"));
		extComp.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		extComp.setUpdatedBy(userDetail);
		externalComponentRepository.save(extComp);
		return;

	}

	@Override
	public void updateExtComponentStatus(Long ecId, Long userId) throws ResourceNotFoundException {

		ExternalComponentMasterEntity extComp = externalComponentRepository.findById(ecId).orElseThrow(() -> new ResourceNotFoundException("External Component Not Found"));
		extComp.setIsActive(!extComp.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		extComp.setUpdatedBy(userDetail);
		externalComponentRepository.save(extComp);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> extComps = externalComponentRepository.findByIsActiveTrue(MasterListDto.class);
		return extComps;

	}

}
