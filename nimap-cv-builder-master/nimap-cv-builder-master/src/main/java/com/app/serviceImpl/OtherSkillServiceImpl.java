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
import com.app.entities.OtherSkillMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.OtherSkillRepository;
import com.app.serviceIntf.OtherSkillServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("otherSkillServiceImpl")
public class OtherSkillServiceImpl implements OtherSkillServiceIntf {

	@Autowired
	private OtherSkillRepository otherSkillRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> skillList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			skillList = otherSkillRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			skillList = otherSkillRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return skillList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto skill = otherSkillRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Other Skill Not Found"));
		return skill;

	}

	@Override
	public void addOtherSkill(MasterAddDto otherSkillBody, Long userId) {

		OtherSkillMasterEntity newSkill = new OtherSkillMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newSkill.setCreatedBy(userDetail);
		newSkill.setDescription(otherSkillBody.getDescription());
		newSkill.setName(otherSkillBody.getName());
		newSkill.setUpdatedBy(userDetail);
		otherSkillRepository.save(newSkill);
		return;

	}

	@Override
	public void editOtherSkill(MasterAddDto otherSkillBody, Long otherSkillId, Long userId) throws ResourceNotFoundException {

		OtherSkillMasterEntity otherSkillDetail = otherSkillRepository.findByIdAndIsActiveTrue(otherSkillId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		otherSkillDetail.setDescription(otherSkillBody.getDescription());
		otherSkillDetail.setName(otherSkillBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		otherSkillDetail.setUpdatedBy(userDetail);
		otherSkillRepository.save(otherSkillDetail);
		return;

	}

	@Override
	public void deleteOtherSkill(Long otherSkillId, Long userId) throws ResourceNotFoundException {

		OtherSkillMasterEntity otherSkillDetail = otherSkillRepository.findByIdAndIsActiveTrue(otherSkillId).orElseThrow(() -> new ResourceNotFoundException("Other Skill Not Found"));
		otherSkillDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		otherSkillDetail.setUpdatedBy(userDetail);
		otherSkillRepository.save(otherSkillDetail);
		return;

	}

	@Override
	public void updateOtherSkillStatus(Long otherSkillId, Long userId) throws ResourceNotFoundException {

		OtherSkillMasterEntity otherSkillDetail = otherSkillRepository.findById(otherSkillId).orElseThrow(() -> new ResourceNotFoundException("Other Skill Not Found"));
		otherSkillDetail.setIsActive(!otherSkillDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		otherSkillDetail.setUpdatedBy(userDetail);
		otherSkillRepository.save(otherSkillDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> skills = otherSkillRepository.findByIsActiveTrue(MasterListDto.class);
		return skills;

	}

}
