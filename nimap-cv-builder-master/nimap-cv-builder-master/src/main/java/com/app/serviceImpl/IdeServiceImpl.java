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
import com.app.entities.IDEMasterEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.IdeRepository;
import com.app.serviceIntf.IdeServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("ideServiceImpl")
public class IdeServiceImpl implements IdeServiceIntf {

	@Autowired
	private IdeRepository ideRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> ideList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			ideList = ideRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			ideList = ideRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return ideList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto ide = ideRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("IDE Not Found"));
		return ide;

	}

	@Override
	public void addIde(MasterAddDto ideBody, Long userId) {

		IDEMasterEntity newIde = new IDEMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newIde.setCreatedBy(userDetail);
		newIde.setDescription(ideBody.getDescription());
		newIde.setName(ideBody.getName());
		newIde.setUpdatedBy(userDetail);
		ideRepository.save(newIde);
		return;

	}

	@Override
	public void editIde(MasterAddDto ideBody, Long ideId, Long userId) throws ResourceNotFoundException {

		IDEMasterEntity ideDetail = ideRepository.findByIdAndIsActiveTrue(ideId).orElseThrow(() -> new ResourceNotFoundException("IDE Not Found"));
		ideDetail.setDescription(ideBody.getDescription());
		ideDetail.setName(ideBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		ideDetail.setUpdatedBy(userDetail);
		ideRepository.save(ideDetail);
		return;

	}

	@Override
	public void deleteIde(Long ideId, Long userId) throws ResourceNotFoundException {

		IDEMasterEntity ideDetail = ideRepository.findByIdAndIsActiveTrue(ideId).orElseThrow(() -> new ResourceNotFoundException("IDE Not Found"));
		ideDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		ideDetail.setUpdatedBy(userDetail);
		ideRepository.save(ideDetail);
		return;

	}

	@Override
	public void updateIdeStatus(Long ideId, Long userId) throws ResourceNotFoundException {

		IDEMasterEntity ideDetail = ideRepository.findById(ideId).orElseThrow(() -> new ResourceNotFoundException("IDE Not Found"));
		ideDetail.setIsActive(!ideDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		ideDetail.setUpdatedBy(userDetail);
		ideRepository.save(ideDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> ides = ideRepository.findByIsActiveTrue(MasterListDto.class);
		return ides;

	}

}
