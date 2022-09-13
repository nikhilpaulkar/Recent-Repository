package com.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.entities.DesignationMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.DesignationRepository;
import com.app.serviceIntf.DesignationServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("designationServiceImpl")
public class DesignationServiceImpl implements DesignationServiceIntf {

	@Autowired
	private DesignationRepository designationRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> designationList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			designationList = designationRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			designationList = designationRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return designationList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto desigantion = designationRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Designation Not Found"));
		return desigantion;

	}

	@Override
	public void addDesignation(MasterAddDto designationBody, Long userId) {

		DesignationMasterEntity newDesignation = new DesignationMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newDesignation.setCreatedBy(userDetail);
		newDesignation.setDescription(designationBody.getDescription());
		newDesignation.setName(designationBody.getName());
		newDesignation.setUpdateBy(userDetail);
	
		designationRepository.save(newDesignation);
		return;

	}

	@Override
	public void editDesignation(MasterAddDto designationBody, Long designationId, Long userId) throws ResourceNotFoundException {

		DesignationMasterEntity desigantion = designationRepository.findByIdAndIsActiveTrue(designationId).orElseThrow(() -> new ResourceNotFoundException("Designation Not Found"));
		desigantion.setDescription(designationBody.getDescription());
		desigantion.setName(designationBody.getName());
		
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		desigantion.setUpdateBy(userDetail);
		designationRepository.save(desigantion);
		return;

	}

	@Override
	public void deleteDesignation(Long designationId, Long userId) throws ResourceNotFoundException {

		DesignationMasterEntity desigantion = designationRepository.findByIdAndIsActiveTrue(designationId).orElseThrow(() -> new ResourceNotFoundException("Designation Not Found"));
		desigantion.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		desigantion.setUpdateBy(userDetail);
		designationRepository.save(desigantion);
		return;

	}

	@Override
	public void updateDesignationStatus(Long designationId, Long userId) throws ResourceNotFoundException {

		DesignationMasterEntity desigantion = designationRepository.findById(designationId).orElseThrow(() -> new ResourceNotFoundException("Designation Not Found"));
		desigantion.setIsActive(!desigantion.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		desigantion.setUpdateBy(userDetail);
		designationRepository.save(desigantion);
		return;

	}

}
