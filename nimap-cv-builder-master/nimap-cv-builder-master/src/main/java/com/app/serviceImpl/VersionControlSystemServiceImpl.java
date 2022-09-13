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
import com.app.entities.UserEntity;
import com.app.entities.VersionControlSystemMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.VersionControlSystemRepository;
import com.app.serviceIntf.VersionControlSystemServiceIntf;
import com.app.utils.PaginationUsingFromTo;

@Service("versionControlSystemServiceImpl")
public class VersionControlSystemServiceImpl implements VersionControlSystemServiceIntf {

	@Autowired
	private VersionControlSystemRepository versionControlSystemRepository;

	@Override
	public Page<MasterListDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<MasterListDto> vcsList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			vcsList = versionControlSystemRepository.findByOrderByIdDesc(paging, MasterListDto.class);

		} else {

			vcsList = versionControlSystemRepository.findByNameContainingIgnoreCaseOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging, MasterListDto.class);

		}

		return vcsList;

	}

	@Override
	public IMasterDetailDto findById(Long id) throws ResourceNotFoundException {

		IMasterDetailDto vcsDetail = versionControlSystemRepository.findByIdAndIsActiveTrue(id, IMasterDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Version Control System Not Found"));
		return vcsDetail;

	}

	@Override
	public void addVersionControlSystem(MasterAddDto vcsBody, Long userId) {

		VersionControlSystemMasterEntity newVcs = new VersionControlSystemMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newVcs.setCreatedBy(userDetail);
		newVcs.setDescription(vcsBody.getDescription());
		newVcs.setName(vcsBody.getName());
		newVcs.setUpdatedBy(userDetail);
		versionControlSystemRepository.save(newVcs);
		return;

	}

	@Override
	public void editVersionControlSystem(MasterAddDto vcsBody, Long vcsId, Long userId) throws ResourceNotFoundException {

		VersionControlSystemMasterEntity vcsDetail = versionControlSystemRepository.findByIdAndIsActiveTrue(vcsId).orElseThrow(() -> new ResourceNotFoundException("Version Control System Not Found"));
		vcsDetail.setDescription(vcsBody.getDescription());
		vcsDetail.setName(vcsBody.getName());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		vcsDetail.setUpdatedBy(userDetail);
		versionControlSystemRepository.save(vcsDetail);
		return;

	}

	@Override
	public void deleteVersionControlSystem(Long vcsId, Long userId) throws ResourceNotFoundException {

		VersionControlSystemMasterEntity vcsDetail = versionControlSystemRepository.findByIdAndIsActiveTrue(vcsId).orElseThrow(() -> new ResourceNotFoundException("Version Control System Not Found"));
		vcsDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		vcsDetail.setUpdatedBy(userDetail);
		versionControlSystemRepository.save(vcsDetail);
		return;

	}

	@Override
	public void updateVersionControlSystemStatus(Long vcsId, Long userId) throws ResourceNotFoundException {

		VersionControlSystemMasterEntity vcsDetail = versionControlSystemRepository.findById(vcsId).orElseThrow(() -> new ResourceNotFoundException("Version Control System Not Found"));
		vcsDetail.setIsActive(!vcsDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		vcsDetail.setUpdatedBy(userDetail);
		versionControlSystemRepository.save(vcsDetail);
		return;

	}

	@Override
	public List<MasterListDto> findAll() {

		List<MasterListDto> vcs = versionControlSystemRepository.findByIsActiveTrue(MasterListDto.class);
		return vcs;

	}

}
