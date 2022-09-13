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
import com.app.entities.FrameworkMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.FrameworkRepository;
import com.app.serviceIntf.FrameworkServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("frameworkServiceImpl")
public class FrameworkServiceImpl implements FrameworkServiceIntf {

	@Autowired
	private FrameworkRepository frameworkRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> frameworkList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			frameworkList = frameworkRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			frameworkList = frameworkRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return frameworkList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto frmaeworkData = frameworkRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Framework Not Found"));
		return frmaeworkData;

	}

	@Override
	public void addFramework(MasterAddDto frameworkBody, Long userId) {

		FrameworkMasterEntity newFramwork = new FrameworkMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newFramwork.setCreatedBy(userDetail);
		newFramwork.setDescription(frameworkBody.getDescription());
		newFramwork.setName(frameworkBody.getName());
		newFramwork.setUpdatedBy(userDetail);
		frameworkRepository.save(newFramwork);
		return;

	}

	@Override
	public void editFramework(MasterAddDto frameworkBody, Long frameworkId, Long userId) throws ResourceNotFoundException {

		FrameworkMasterEntity frameworkDetail = frameworkRepository.findByIdAndIsActiveTrue(frameworkId).orElseThrow(() -> new ResourceNotFoundException("Framework Not Found"));
		frameworkDetail.setDescription(frameworkBody.getDescription());
		frameworkDetail.setName(frameworkBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		frameworkDetail.setUpdatedBy(userDetail);
		frameworkRepository.save(frameworkDetail);
		return;

	}

	@Override
	public void deleteFramework(Long frameworkId, Long userId) throws ResourceNotFoundException {

		FrameworkMasterEntity frameworkDetail = frameworkRepository.findByIdAndIsActiveTrue(frameworkId).orElseThrow(() -> new ResourceNotFoundException("Framework Not Found"));
		frameworkDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		frameworkDetail.setUpdatedBy(userDetail);
		frameworkRepository.save(frameworkDetail);
		return;

	}

	@Override
	public void updateFrameworkStatus(Long frameworkId, Long userId) throws ResourceNotFoundException {

		FrameworkMasterEntity frameworkDetail = frameworkRepository.findById(frameworkId).orElseThrow(() -> new ResourceNotFoundException("Framework Not Found"));
		frameworkDetail.setIsActive(!frameworkDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		frameworkDetail.setUpdatedBy(userDetail);
		frameworkRepository.save(frameworkDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> frameworks = frameworkRepository.findByIsActiveTrue(MasterListDto.class);
		return frameworks;

	}

}
