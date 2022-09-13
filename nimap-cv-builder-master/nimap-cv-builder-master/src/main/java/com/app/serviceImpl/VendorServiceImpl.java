package com.app.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.MasterListVendor;
import com.app.dto.VendorMasterDto;
import com.app.entities.UserEntity;
import com.app.entities.VendorMasterEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.VendorRepository;
import com.app.serviceIntf.VendorServiceIntrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.app.utils.PaginationUsingFromTo;

@Service
public class VendorServiceImpl implements VendorServiceIntrf {
	@Autowired
	private VendorRepository vendorRepository;

	@Override
	public Page<VendorMasterDto> findAll(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<VendorMasterDto> vendorList;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			vendorList = vendorRepository.findByOrderByIdDesc(paging, VendorMasterDto.class);

		} else {

			vendorList = vendorRepository.findByNameContainingIgnoreCaseOrOrganizationContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCaseOrderByIdDesc(
					StringUtils.trimLeadingWhitespace(search),StringUtils.trimLeadingWhitespace(search),StringUtils.trimLeadingWhitespace(search),StringUtils.trimLeadingWhitespace(search), paging, VendorMasterDto.class);

		}

		return vendorList;
	}

	@Override
	public VendorMasterDto findById(Long id) throws ResourceNotFoundException {
		VendorMasterDto vendorData = vendorRepository.findByIdAndIsActiveTrue(id, VendorMasterDto.class)
				.orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found"));
		return vendorData;
	}

	@Override
	public void addVendor(MasterListVendor vendorBody, Long userId) {
		VendorMasterEntity newVedor = new VendorMasterEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newVedor.setCreatedBy(userDetail);
		newVedor.setMobile(vendorBody.getMobile());
		newVedor.setEmail(vendorBody.getEmail());
		newVedor.setOrganization(vendorBody.getOrganization());
		newVedor.setName(vendorBody.getName());
		newVedor.setUpdatedBy(userDetail);
		vendorRepository.save(newVedor);
		return;
	}

	@Override
	public void editVendor(MasterListVendor vedorBody, Long vedorId, Long userId) throws ResourceNotFoundException {
		VendorMasterEntity newVedor = vendorRepository.findByIdAndIsActiveTrue(vedorId)
				.orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found"));
		newVedor.setEmail(vedorBody.getEmail());
		newVedor.setMobile(vedorBody.getMobile());
		newVedor.setName(vedorBody.getName());
		newVedor.setOrganization(vedorBody.getOrganization());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newVedor.setUpdatedBy(userDetail);
		vendorRepository.save(newVedor);
		return;

	}

	@Override
	public void deleteVendor(Long vedorId, Long userId) throws ResourceNotFoundException {
		VendorMasterEntity vedorDetail = vendorRepository.findByIdAndIsActiveTrue(vedorId)
				.orElseThrow(() -> new ResourceNotFoundException("vendor Not Found"));
		vedorDetail.setIsActive(false);
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		vedorDetail.setUpdatedBy(userDetail);
		vendorRepository.save(vedorDetail);
		return;

	}

	@Override
	public void updateVendorStatus(Long vedorId, Long userId) throws ResourceNotFoundException {
		VendorMasterEntity vedorDetail = vendorRepository.findById(vedorId)
				.orElseThrow(() -> new ResourceNotFoundException("vendor Not Found"));
		vedorDetail.setIsActive(!vedorDetail.getIsActive());
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		vedorDetail.setUpdatedBy(userDetail);
		vendorRepository.save(vedorDetail);
		return;
	}

	@Override
	public List<VendorMasterDto> findAll() {
		List<VendorMasterDto> vendors = vendorRepository.findByIsActiveTrue(VendorMasterDto.class);
		return vendors;
	}

}
