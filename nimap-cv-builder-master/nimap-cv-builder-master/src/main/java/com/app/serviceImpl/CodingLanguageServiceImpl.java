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
import com.app.entities.CodingLanguageMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.CodingLanguageRepository;
import com.app.serviceIntf.CodingLanguageServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("codingLanguageServiceImpl")
public class CodingLanguageServiceImpl implements CodingLanguageServiceIntf {

	@Autowired
	private CodingLanguageRepository codingLanguageRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> designationList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			designationList = codingLanguageRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			designationList = codingLanguageRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return designationList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto langData = codingLanguageRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Language Not Found"));
		return langData;

	}

	@Override
	public void addCodeLang(MasterAddDto langBody, Long userId) {

		CodingLanguageMasterEntity newLang = new CodingLanguageMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newLang.setCreatedBy(userDetail);
		newLang.setDescription(langBody.getDescription());
		newLang.setName(langBody.getName());
		newLang.setUpdatedBy(userDetail);
		codingLanguageRepository.save(newLang);
		return;

	}

	@Override
	public void editCodeLang(MasterAddDto langBody, Long langId, Long userId) throws ResourceNotFoundException {

		CodingLanguageMasterEntity langDetail = codingLanguageRepository.findByIdAndIsActiveTrue(langId).orElseThrow(() -> new ResourceNotFoundException("Language Not Found"));
		langDetail.setDescription(langBody.getDescription());
		langDetail.setName(langBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		langDetail.setUpdatedBy(userDetail);
		codingLanguageRepository.save(langDetail);
		return;

	}

	@Override
	public void deleteCodeLang(Long langId, Long userId) throws ResourceNotFoundException {

		CodingLanguageMasterEntity langDetail = codingLanguageRepository.findByIdAndIsActiveTrue(langId).orElseThrow(() -> new ResourceNotFoundException("Language Not Found"));
		langDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		langDetail.setUpdatedBy(userDetail);
		codingLanguageRepository.save(langDetail);
		return;

	}

	@Override
	public void updateCodeLangStatus(Long langId, Long userId) throws ResourceNotFoundException {

		CodingLanguageMasterEntity langDetail = codingLanguageRepository.findById(langId).orElseThrow(() -> new ResourceNotFoundException("Language Not Found"));
		langDetail.setIsActive(!langDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		langDetail.setUpdatedBy(userDetail);
		codingLanguageRepository.save(langDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> langs = codingLanguageRepository.findByIsActiveTrue(MasterListDto.class);
		return langs;

	}

}
