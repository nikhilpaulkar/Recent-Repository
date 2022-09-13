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
import com.app.entities.OperatingSystemMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.OperatingSystemRepository;
import com.app.serviceIntf.OperatingSystemServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("operatingSystemServiceImpl")
public class OperatingSystemServiceImpl implements OperatingSystemServiceIntf {

	@Autowired
	private OperatingSystemRepository operatingSystemRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> osList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			osList = operatingSystemRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			osList = operatingSystemRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return osList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto osDetail = operatingSystemRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Operating System Not Found"));
		return osDetail;

	}

	@Override
	public void addOs(MasterAddDto osBody, Long userId) {

		OperatingSystemMasterEntity newOs = new OperatingSystemMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newOs.setCreatedBy(userDetail);
		newOs.setDescription(osBody.getDescription());
		newOs.setName(osBody.getName());
		newOs.setUpdatedBy(userDetail);
		operatingSystemRepository.save(newOs);
		return;

	}

	@Override
	public void editOs(MasterAddDto osBody, Long osId, Long userId) throws ResourceNotFoundException {

		OperatingSystemMasterEntity osDetail = operatingSystemRepository.findByIdAndIsActiveTrue(osId).orElseThrow(() -> new ResourceNotFoundException("Operating System Not Found"));
		osDetail.setDescription(osBody.getDescription());
		osDetail.setName(osBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		osDetail.setUpdatedBy(userDetail);
		operatingSystemRepository.save(osDetail);
		return;

	}

	@Override
	public void deleteOs(Long osId, Long userId) throws ResourceNotFoundException {

		OperatingSystemMasterEntity osDetail = operatingSystemRepository.findByIdAndIsActiveTrue(osId).orElseThrow(() -> new ResourceNotFoundException("Operating System Not Found"));
		osDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		osDetail.setUpdatedBy(userDetail);
		operatingSystemRepository.save(osDetail);
		return;

	}

	@Override
	public void updateOsStatus(Long osId, Long userId) throws ResourceNotFoundException {

		OperatingSystemMasterEntity osDetail = operatingSystemRepository.findById(osId).orElseThrow(() -> new ResourceNotFoundException("Operating System Not Found"));
		osDetail.setIsActive(!osDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		osDetail.setUpdatedBy(userDetail);
		operatingSystemRepository.save(osDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> os = operatingSystemRepository.findByIsActiveTrue(MasterListDto.class);
		return os;

	}

}
