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
import com.app.entities.ToolsMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ToolRepository;
import com.app.serviceIntf.ToolsServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("toolServiceImpl")
public class ToolServiceImpl implements ToolsServiceIntf {

	@Autowired
	private ToolRepository toolRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> skillList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			skillList = toolRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			skillList = toolRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return skillList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto skill = toolRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Tool Not Found"));
		return skill;

	}

	@Override
	public void addTool(MasterAddDto toolBody, Long userId) {

		ToolsMasterEntity newTool = new ToolsMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newTool.setCreatedBy(userDetail);
		newTool.setDescription(toolBody.getDescription());
		newTool.setName(toolBody.getName());
		newTool.setUpdatedBy(userDetail);
		toolRepository.save(newTool);
		return;

	}

	@Override
	public void editTool(MasterAddDto toolBody, Long toolId, Long userId) throws ResourceNotFoundException {

		ToolsMasterEntity toolDetail = toolRepository.findByIdAndIsActiveTrue(toolId).orElseThrow(() -> new ResourceNotFoundException("first make it the active switch then do the editing"));
		toolDetail.setDescription(toolBody.getDescription());
		toolDetail.setName(toolBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		toolDetail.setUpdatedBy(userDetail);
		toolRepository.save(toolDetail);
		return;

	}

	@Override
	public void deleteTool(Long toolId, Long userId) throws ResourceNotFoundException {

		ToolsMasterEntity toolDetail = toolRepository.findByIdAndIsActiveTrue(toolId).orElseThrow(() -> new ResourceNotFoundException("Tool Not Found"));
		toolDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		toolDetail.setUpdatedBy(userDetail);
		toolRepository.save(toolDetail);
		return;

	}

	@Override
	public void updateToolStatus(Long toolId, Long userId) throws ResourceNotFoundException {

		ToolsMasterEntity toolDetail = toolRepository.findById(toolId).orElseThrow(() -> new ResourceNotFoundException("Tool Not Found"));
		toolDetail.setIsActive(!toolDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		toolDetail.setUpdatedBy(userDetail);
		toolRepository.save(toolDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> tools = toolRepository.findByIsActiveTrue(MasterListDto.class);
		return tools;

	}

}
