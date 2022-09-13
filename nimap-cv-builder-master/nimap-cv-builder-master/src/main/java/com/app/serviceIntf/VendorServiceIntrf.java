package com.app.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.MasterListVendor;
import com.app.dto.VendorMasterDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface VendorServiceIntrf {

	Page<VendorMasterDto> findAll(String search, String from, String to);

	VendorMasterDto findById(Long id) throws ResourceNotFoundException;

	void addVendor(MasterListVendor vendorBody, Long userId);

	void editVendor(MasterListVendor vedorBody, Long vedorId, Long userId) throws ResourceNotFoundException;

	void deleteVendor(Long vedorId, Long userId) throws ResourceNotFoundException;

	void updateVendorStatus(Long vedorId, Long userId) throws ResourceNotFoundException;

	List<VendorMasterDto> findAll();
}
