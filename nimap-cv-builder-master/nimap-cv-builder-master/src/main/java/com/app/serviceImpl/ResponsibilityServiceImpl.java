package com.app.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dto.IResponsibilityDetailDto;
import com.app.dto.ResponsibilityAddDto;
import com.app.entities.CodingLanguageMasterEntity;
import com.app.entities.ResponsibilityMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ResponsibilityRepository;
import com.app.serviceIntf.ResponsibilityServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("responsibilityServiceImpl")
public class ResponsibilityServiceImpl implements ResponsibilityServiceIntf {

	@Autowired
	private ResponsibilityRepository responsibilityRepository;

	@Override
	public void addResponsibility(ResponsibilityAddDto reqBody, Long userId) throws ResourceNotFoundException {

		ResponsibilityMasterEntity newResponsibility = new ResponsibilityMasterEntity();
		CodingLanguageMasterEntity codeData = new CodingLanguageMasterEntity();
		codeData.setId(reqBody.getCodeLanguagesId());
		UserEntity user = new UserEntity();
		user.setId(userId);
		newResponsibility.setCodeLanguages(codeData);
		newResponsibility.setDescription(reqBody.getDescription());
		newResponsibility.setName(reqBody.getName());
		newResponsibility.setCreatedBy(user);
		newResponsibility.setUpdatedBy(user);
		responsibilityRepository.save(newResponsibility);

	}

	@Override
	public void editResponsibility(ResponsibilityAddDto reqBody, Long resposibilityId, Long userId)
			throws ResourceNotFoundException {

		ResponsibilityMasterEntity responsibility = responsibilityRepository.findByIdAndIsActiveTrue(resposibilityId)
				.orElseThrow(() -> new ResourceNotFoundException("Responsibility Not Found"));
		CodingLanguageMasterEntity codeData = new CodingLanguageMasterEntity();
		codeData.setId(reqBody.getCodeLanguagesId());
		UserEntity user = new UserEntity();
		user.setId(userId);
		responsibility.setCodeLanguages(codeData);
		responsibility.setDescription(reqBody.getDescription());
		responsibility.setName(reqBody.getName());
		responsibility.setUpdatedBy(user);
		responsibilityRepository.save(responsibility);
	}

	@Override
	public void deleteResponsibility(Long resposibilityId, Long userId) throws ResourceNotFoundException {

		ResponsibilityMasterEntity responsibility = responsibilityRepository.findByIdAndIsActiveTrue(resposibilityId)
				.orElseThrow(() -> new ResourceNotFoundException("Responsibility Not Found"));
		UserEntity user = new UserEntity();
		user.setId(userId);
		responsibility.setIsActive(false);
		responsibility.setUpdatedBy(user);
		responsibilityRepository.save(responsibility);
	}

	@Override
	public Page<IResponsibilityDetailDto> getResponsibilities(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<IResponsibilityDetailDto> responsibilities;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			responsibilities = responsibilityRepository.findByOrderByIdDesc(paging, IResponsibilityDetailDto.class);

		} else {

			responsibilities = responsibilityRepository.findByNameContainingIgnoreCaseOrCodeLanguagesNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search),StringUtils.trimLeadingWhitespace(search), paging,
					IResponsibilityDetailDto.class);

		}

		return responsibilities;

	}

	@Override
	public IResponsibilityDetailDto getResponsibilityById(Long resposibilityId) throws ResourceNotFoundException {

		IResponsibilityDetailDto responsibility = responsibilityRepository
				.findById(resposibilityId, IResponsibilityDetailDto.class)
				.orElseThrow(() -> new ResourceNotFoundException("Responsibility Not Found"));
		return responsibility;

	}

	@Override
	public void updateResponsibilityStatusById(Long resposibilityId, Long userId) throws ResourceNotFoundException {

		ResponsibilityMasterEntity responsibility = responsibilityRepository.findById(resposibilityId)
				.orElseThrow(() -> new ResourceNotFoundException("Responsibility Not Found"));
		UserEntity user = new UserEntity();
		user.setId(userId);
		responsibility.setIsActive(!responsibility.getIsActive());
		responsibility.setUpdatedBy(user);
		responsibilityRepository.save(responsibility);

	}

	@Override
	public ArrayList<IResponsibilityDetailDto> getResponsibilityByCodeLangId(Long[] id) {

		return responsibilityRepository.findByCodeLanguagesIdInAndIsActiveTrueOrderByIdDesc(id,
				IResponsibilityDetailDto.class);

	}

}
