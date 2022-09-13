package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dto.EntityDto;
import com.app.dto.EntityPermissionDto;
import com.app.dto.IPermissionDto;
import com.app.dto.IPermissionIdList;
import com.app.dto.IRoleDetailDto;
import com.app.dto.IRoleListDto;
import com.app.dto.RoleDto;
import com.app.dto.RoleIdListDto;
import com.app.dto.RolePermissionDto;
import com.app.entities.EntityEntity;
import com.app.entities.PermissionEntity;
import com.app.entities.RoleEntity;
import com.app.entities.RolePermissionEntity;
import com.app.entities.RolePermissionId;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.AuthRepository;
import com.app.repositories.EntityRepository;
import com.app.repositories.PermissionRepository;
import com.app.repositories.RolePermissionRepository;
import com.app.repositories.RoleRepository;
import com.app.repositories.UserRoleRepository;
import com.app.serviceIntf.RoleServiceInterface;
import com.app.utils.PaginationUsingFromTo;

@Transactional
@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleServiceInterface {

	public RoleServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int batchSize;

	@Override
	public Page<IRoleListDto> getAllRoles(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<IRoleListDto> roles;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			roles = roleRepository.findByIsActiveTrue(paging, IRoleListDto.class);

		} else {

			roles = roleRepository.findByRoleNameContainingIgnoreCaseAndIsActiveTrue(StringUtils.trimLeadingWhitespace(search), paging, IRoleListDto.class);

		}

		return roles;

	}

	@Override
	public void addRole(RoleDto roleDto, Long userId) {

		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setCreatedBy(this.authRepository.getById(userId));
		roleEntity.setUpdatedBy(this.authRepository.getById(userId));
		roleEntity.setRoleName(roleDto.getRoleName());
		roleEntity.setDescription(roleDto.getDescription());
		roleRepository.save(roleEntity);

	}

	@Override
	public RoleEntity updateRole(RoleDto roleData, Long id, Long updateBy) throws ResourceNotFoundException {

		RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
		roleEntity.setRoleName(roleData.getRoleName());
		roleEntity.setDescription(roleData.getDescription());
		roleEntity.setUpdatedBy(authRepository.getById(updateBy));
		roleRepository.save(roleEntity);
		return roleEntity;

	}

	@Override
	public void deleteRole(Long id) throws ResourceNotFoundException {

		RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
		roleEntity.setIsActive(false);
		roleRepository.save(roleEntity);
		return;

	}

	@Override
	public IRoleDetailDto getRoleById(Long id) throws ResourceNotFoundException {

		IRoleDetailDto roleEntity = roleRepository.findById(id, IRoleDetailDto.class).orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
		return roleEntity;

	}

	@Override
	public RolePermissionDto getRoleAndPermissionById(Long id) throws ResourceNotFoundException {

		RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
		List<EntityEntity> entities = entityRepository.findAll();
		List<PermissionEntity> permissions = permissionRepository.findAll();
		ArrayList<IPermissionDto> rolesPermission = rolePermissionRepository.findByPkRoleId(id, IPermissionDto.class);
		ArrayList<EntityPermissionDto> entityPermission = new ArrayList<>();

		for (PermissionEntity permission : permissions) {

			EntityPermissionDto singleEntityPermisson = new EntityPermissionDto();
			singleEntityPermisson.setActionName(permission.getDescription());
			singleEntityPermisson.setId(permission.getId());
			singleEntityPermisson.setEntityId(permission.getEntityId().getId());
			singleEntityPermisson.setIsSelected(false);

			for (IPermissionDto element : rolesPermission) {

				if (permission.getId() == element.getPkPermissionId()) {

					singleEntityPermisson.setIsSelected(true);
					break;

				}

			}

			entityPermission.add(singleEntityPermisson);

		}

		ArrayList<EntityDto> entityDto = new ArrayList<>();

		for (EntityEntity element : entities) {

			Boolean isEntityEnabled = false;
			ArrayList<EntityPermissionDto> entityPermission1 = new ArrayList<>();

			for (int j = 0; j < entityPermission.size(); j++) {

				if (element.getId() == entityPermission.get(j).getEntityId()) {

					if (entityPermission.get(j).getIsSelected()) {

						isEntityEnabled = true;

					}

					entityPermission1.add(entityPermission.get(j));

				}

			}

			EntityDto singleEntityDto = new EntityDto();
			singleEntityDto.setId(element.getId());
			singleEntityDto.setEntityName(element.getEntityName());
			singleEntityDto.setIsSelected(isEntityEnabled);
			singleEntityDto.setPermissions(entityPermission1);
			entityDto.add(singleEntityDto);

		}

		RolePermissionDto rolePermissionDto = new RolePermissionDto();
		rolePermissionDto.setId(roleEntity.getId());
		rolePermissionDto.setRoleName(roleEntity.getRoleName());
		rolePermissionDto.setDescription(roleEntity.getDescription());
		rolePermissionDto.setEntity(entityDto);
		return rolePermissionDto;

	}

	@Override
	public ArrayList<String> getPermissionByUserId(Long userId) {

		ArrayList<RoleIdListDto> roleIds = userRoleRepository.findByPkUserId(userId, RoleIdListDto.class);
		ArrayList<Long> roles = new ArrayList<>();

		for (int i = 0; i < roleIds.size(); i++) {

			roles.add(roleIds.get(i).getPkRoleId());

		}

		List<IPermissionIdList> rolesPermission = rolePermissionRepository.findPkPermissionByPkRoleIdIn(roles, IPermissionIdList.class);
		ArrayList<String> permissions = new ArrayList<>();

		for (IPermissionIdList element : rolesPermission) {

			permissions.add(element.getPkPermissionActionName());

		}

		return permissions;

	}

	@Override
	public void addPermissionsToRole(Long id, Long[] permissions) throws ResourceNotFoundException {

		roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
		rolePermissionRepository.deleteByPkRoleId(id);
		int outerCount = permissions.length / batchSize;
		ArrayList<RolePermissionEntity> rolePermissionEntities = new ArrayList<>();

		for (int i = 0; i <= outerCount; i++) {

			for (int j = i * batchSize; j < (outerCount == i ? permissions.length : (i + 1) * batchSize); j++) {
				
				RolePermissionEntity rpe = new RolePermissionEntity();
				RoleEntity re = new RoleEntity();
				re.setId(id);
				PermissionEntity pe = new PermissionEntity();
				pe.setId(permissions[j]);
				RolePermissionId rpi = new RolePermissionId(re, pe);
				rpe.setPk(rpi);
				rolePermissionEntities.add(rpe);

			}

			rolePermissionRepository.saveAll(rolePermissionEntities);

		}

		return;

	}

}
