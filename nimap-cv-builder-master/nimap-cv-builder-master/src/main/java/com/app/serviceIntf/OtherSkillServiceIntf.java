package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.IMasterDetailDto;
import com.app.dto.MasterAddDto;
import com.app.dto.MasterListDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface OtherSkillServiceIntf {

	Page<MasterListDto> findAll(String search, String from, String to);

	IMasterDetailDto findById(Long id) throws ResourceNotFoundException;

	void addOtherSkill(MasterAddDto otherSkillBody, Long userId);

	void editOtherSkill(MasterAddDto otherSkillBody, Long otherSkillId, Long userId) throws ResourceNotFoundException;

	void deleteOtherSkill(Long otherSkillId, Long userId) throws ResourceNotFoundException;

	void updateOtherSkillStatus(Long otherSkillId, Long userId) throws ResourceNotFoundException;

	List<MasterListDto> findAll();

}
