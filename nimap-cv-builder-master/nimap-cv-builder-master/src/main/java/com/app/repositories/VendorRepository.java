package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.app.dto.VendorMasterDto;
import com.app.entities.VendorMasterEntity;
import java.util.*;
@Repository
public interface VendorRepository extends JpaRepository<VendorMasterEntity, Long>{
	Page<VendorMasterDto> findByNameContainingIgnoreCaseOrOrganizationContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCaseOrderByIdDesc(String name,String org,String email,String mobile, Pageable pageable, Class<VendorMasterDto> VendorListDto);
	
	Page<VendorMasterDto> findByOrderByIdDesc(Pageable page, Class<VendorMasterDto> MasterListDto);
	
	Optional<VendorMasterDto> findByIdAndIsActiveTrue(Long id, Class<VendorMasterDto> IMasterDetailDto);
	
	Optional<VendorMasterEntity> findByIdAndIsActiveTrue(Long id);
	
	ArrayList<VendorMasterDto> findByIdIn(ArrayList<Long> vendorId, Class<VendorMasterDto> MasterListDto);
	
	List<VendorMasterDto> findByIsActiveTrue(Class<VendorMasterDto> MasterListDto);
	
	Optional<VendorMasterEntity> findByNameContainingIgnoreCase(String name);
	
}
