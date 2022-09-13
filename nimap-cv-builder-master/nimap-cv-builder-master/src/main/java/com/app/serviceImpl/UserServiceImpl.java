package com.app.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.config.JwtTokenUtil;
import com.app.dto.ChangePasswordDto;
import com.app.dto.EditUserRequestDto;
import com.app.dto.ForgotPasswordDto;
import com.app.dto.IPermissionDto;
import com.app.dto.IUserListDto;
import com.app.dto.IUserRoleDetailDto;
import com.app.dto.RoleIdListDto;
import com.app.dto.UserDataDto;
import com.app.dto.UserDto;
import com.app.dto.UserRoleDto;
import com.app.entities.DesignationMasterEntity;
import com.app.entities.Forgot_password_request;
import com.app.entities.GenderEnum;
import com.app.entities.RoleEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserRoleEntity;
import com.app.entities.UserRoleId;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.ForgotPasswordRequestRepository;
import com.app.repositories.RolePermissionRepository;
import com.app.repositories.RoleRepository;
import com.app.repositories.UserRepository;
import com.app.repositories.UserRoleRepository;
import com.app.serviceIntf.UserServiceInterface;
import com.app.utils.AppSetting;
import com.app.utils.PaginationUsingFromTo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Transactional
@Service("userServiceImpl")
public class UserServiceImpl extends JwtTokenUtil implements UserServiceInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int batchSize;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private AppSetting appSetting;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	ForgotPasswordRequestRepository forgotPasswordRequestRepository;

	@Override
	public List<IPermissionDto> getUserPermission(Long userId) throws IOException {

		ArrayList<RoleIdListDto> roleIds = userRoleRepository.findByPkUserId(userId, RoleIdListDto.class);
		ArrayList<Long> roles = new ArrayList<>();

		for (int i = 0; i < roleIds.size(); i++) {

			roles.add(roleIds.get(i).getPkRoleId());

		}

		return rolePermissionRepository.findByPkRoleIdIn(roles, IPermissionDto.class);

	}

	@Override
	public UserEntity findByEmail(String email) throws ResourceNotFoundException {

		UserEntity userData = userRepository.findByEmailAndIsActiveTrue(email)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		return userData;

	}

	@Override
	public void editUser(Long userId, UserDto userBody, Long adminId) throws ResourceNotFoundException {

		UserEntity userData = userRepository.findByIdAndIsActiveTrue(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		userData.setName(userBody.getName());
		userData.setEmail(userBody.getEmail());
		userData.setAddress(userBody.getAddress());
		userData.setSummary(userBody.getSummary());
		userData.setLaptopConfiguration(userBody.getLaptopConfiguration());
		userData.setTechnicalStack(getPlainString(userBody.getTechnicalStack()));
		userData.setVendor(getPlainString(userBody.getVendor()));
		userData.setCareerStartDate(userBody.getCareerStartDate());
		userData.setHighestQualification(userBody.getHighestQualification());
		userData.setUniversityName(userBody.getUniversityName());
		userData.setYearOfPassing((userBody.getYearOfPassing()));
		userData.setDateOfJoining(userBody.getDateOfJoining());

		if (!userBody.getGender().isEmpty()) 
		{
			userData.setGender(Enum.valueOf(GenderEnum.class, userBody.getGender()));
		} else {
			userData.setGender(null);
		}

		DesignationMasterEntity designation = new DesignationMasterEntity();
		designation.setId(userBody.getDesignationId());
		userData.setDesignationId(designation);
		userData.setDob(userBody.getDob());
		UserEntity adminUser = new UserEntity();
		adminUser.setId(adminId);
		userData.setUpdatedBy(adminUser);
		userRepository.save(userData);
		userRoleRepository.deleteByPkUserId(userId);
//		int outerCount = userBody.getRoles().length / batchSize;
//		ArrayList<UserRoleEntity> userRoles = new ArrayList<>();

//		for (int i = 0; i <= outerCount; i++) {
//
//			for (int j = i * batchSize; j < (outerCount == i ? userBody.getRoles().length : (i + 1) * batchSize); j++) {
//
//				UserRoleEntity ure = new UserRoleEntity();
//				UserEntity ue = new UserEntity();
//				ue.setId(userId);
//				RoleEntity re = new RoleEntity();
//				re.setId(userBody.getRoles()[j]);
//				UserRoleId uri = new UserRoleId(ue, re);
//				ure.setPk(uri);
//				userRoles.add(ure);

//			}

//			userRoleRepository.saveAll(userRoles);

//		}

	}

	@Override
	public Page<IUserListDto> getAllUsers(String search, String from, String to) {

		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<IUserListDto> users;

		if ((search == "") || (search == null) || (search.length() == 0)) {

			users = userRepository.findByOrderByIdDesc(paging, IUserListDto.class);

		} else {

			users = userRepository
					.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDesignationIdNameContainingIgnoreCaseOrderByIdDesc(
							StringUtils.trimLeadingWhitespace(search), StringUtils.trimLeadingWhitespace(search),
							StringUtils.trimLeadingWhitespace(search), paging, IUserListDto.class);

		}

		System.out.println(userRepository.findAll().size());
		return users;

	}

	@Override
	public void updateStatus(Long userId) throws ResourceNotFoundException {

		UserEntity userData = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		userData.setIsActive(!userData.getIsActive());
		return;

	}

	@Override
	public UserDataDto getUserRole(Long userId) throws ResourceNotFoundException 
	{
		UserEntity user = userRepository.findByIdAndIsActiveTrue(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		UserDataDto userResp = new UserDataDto(userId, user.getName(), user.getEmail(), user.getUniversityName(),
				user.getHighestQualification(), user.getYearOfPassing(), user.getDateOfJoining());
		userResp.setAddress(user.getAddress());
		userResp.setCareerStartDate(user.getCareerStartDate());
		userResp.setTechnicalStack(user.getTechnicalStack());
		userResp.setLaptopConfiguration(user.getLaptopConfiguration());
//		userResp.setDesignationId(user.getDesignationId().getId());
		userResp.setDesignationName(user.getDesignationId().getName());
		userResp.setSummary(user.getSummary());
		userResp.setDob(user.getDob());
//		userResp.setGender(Enum.valueOf(GenderEnum.class, "MALE"));
		userResp.setGender(user.getGender());
		ArrayList<RoleEntity> allRoles = roleRepository.findByIsActiveTrue();
		ArrayList<IUserRoleDetailDto> userDetail = userRoleRepository.findByPkUserIdAndPkUserIsActiveTrue(userId,
				IUserRoleDetailDto.class);
		ArrayList<UserRoleDto> userRoles = new ArrayList<>();

		for (int i = 0; i < allRoles.size(); i++) {

			UserRoleDto role = new UserRoleDto();
			role.setId(allRoles.get(i).getId());
			role.setName(allRoles.get(i).getRoleName());
			role.setIsSelected(false);

			for (IUserRoleDetailDto element : userDetail) {

				if (allRoles.get(i).getId() == element.getId()) {

					role.setIsSelected(true);
					break;

				}

			}

			userRoles.add(role);

		}

		userResp.setRoles(userRoles);
		return userResp;

	}

	@Override
	public void addUser(UserDto userDetail, Long userId) 
	{
		UserEntity newUser = new UserEntity();
		DesignationMasterEntity designation = new DesignationMasterEntity();
		
		if(userDetail.getDesignationId()!=null)
		designation.setId(userDetail.getDesignationId());
		else
			designation.setId((long) 68);
//		designation.setId((long) 68);
			
		newUser.setAddress(userDetail.getAddress());
		newUser.setSummary(userDetail.getSummary());
		newUser.setLaptopConfiguration(userDetail.getLaptopConfiguration());
		newUser.setCareerStartDate(userDetail.getCareerStartDate());
		newUser.setDesignationId(designation);
		newUser.setDob(userDetail.getDob());
		newUser.setEmail(userDetail.getEmail().toLowerCase());
		newUser.setName(userDetail.getName());
		newUser.setPassword(bcryptEncoder.encode(appSetting.getAllAppSetting().getSettings().get("vinay")));
		newUser.setHighestQualification(userDetail.getHighestQualification());
		newUser.setUniversityName(userDetail.getUniversityName());
		 newUser.setVendor(getPlainString(userDetail.getVendor())); 
		newUser.setTechnicalStack(getPlainString(userDetail.getTechnicalStack()));
		newUser.setYearOfPassing((userDetail.getYearOfPassing()));
		newUser.setDateOfJoining(userDetail.getDateOfJoining());
		UserEntity creatingUser = new UserEntity();
		creatingUser.setId(userId);
		newUser.setCreatedBy(creatingUser);
		newUser.setUpdatedBy(creatingUser);
		if (!userDetail.getGender().isEmpty()) {
			newUser.setGender(Enum.valueOf(GenderEnum.class, userDetail.getGender()));
		}
		UserEntity addedUser = userRepository.save(newUser);
		UserRoleEntity userRole = new UserRoleEntity();
		UserEntity ue = new UserEntity();
		ue.setId(addedUser.getId());
		RoleEntity re = new RoleEntity();
		re.setId(Long.parseLong((appSetting.getAllAppSetting().getSettings().get("defaultRoleId"))));
		// re.setId((long) (2));
		UserRoleId uri = new UserRoleId(ue, re);
		userRole.setPk(uri);
		userRoleRepository.save(userRole);

	}

	@Override
	public void editOnlySameUser(Long userId, UserDto userBody, Long adminId, HttpServletRequest request)
			throws ResourceNotFoundException {

		UserEntity userData = userRepository.findByIdAndIsActiveTrue(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		DesignationMasterEntity designation = new DesignationMasterEntity();
		UserEntity adminUser = new UserEntity();
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		JsonObject jsonObj = null;
		jwtToken = requestTokenHeader.substring(7);
		username = jwtTokenUtil.getEmailFromToken(jwtToken);
		jsonObj = JsonParser.parseString(username).getAsJsonObject();
		UserDataDto userDatas = new UserDataDto();
		userDatas.setUserId((jsonObj.get("id").getAsLong()));

		if (userDatas.getUserId() == userData.getId()) {

			userData.setName(userBody.getName());
			userData.setEmail(userBody.getEmail());
			userData.setAddress(userBody.getAddress());
			userData.setSummary(userBody.getSummary());
			userData.setVendor(getPlainString(userBody.getVendor()));
			userData.setTechnicalStack(getPlainString(userBody.getTechnicalStack()));
			userData.setCareerStartDate(userBody.getCareerStartDate());
			userData.setHighestQualification(userBody.getHighestQualification());
			userData.setUniversityName(userBody.getUniversityName());
			userData.setLaptopConfiguration(userBody.getLaptopConfiguration());
			userData.setYearOfPassing((userBody.getYearOfPassing()));
			userData.setDateOfJoining(userBody.getDateOfJoining());
			if (!userBody.getGender().isEmpty()) {
				userData.setGender(Enum.valueOf(GenderEnum.class, userBody.getGender()));
			} else {
				userData.setGender(null);
			}
			designation.setId(userBody.getDesignationId());
			userData.setDesignationId(designation);
			userData.setDob(userBody.getDob());
			adminUser.setId(adminId);
			userData.setUpdatedBy(adminUser);
			userRepository.save(userData);
			userRoleRepository.deleteByPkUserId(userId);

		} else {

			System.out.println("dont rint the data");
			throw new ResourceNotFoundException("Access Denied");

		}

	}

	@Override
	public void changePassword(Long userId, ChangePasswordDto userBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		UserEntity userData = userRepository.findByIdAndIsActiveTrue(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		JsonObject jsonObj = null;
		jwtToken = requestTokenHeader.substring(7);
		username = jwtTokenUtil.getEmailFromToken(jwtToken);
		jsonObj = JsonParser.parseString(username).getAsJsonObject();
		UserDataDto userDatas = new UserDataDto();
		userDatas.setUserId((jsonObj.get("id").getAsLong()));

		if (userDatas.getUserId() == userData.getId()) {

			if (!bcryptEncoder.matches(userBody.getNewPassword(), userData.getPassword())) {
 
				if (bcryptEncoder.matches(userBody.getPassword(), userData.getPassword())) {

//					userData.setPassword(bcryptEncoder.encode(userBody.getNewPassword()));
					if (userBody.getNewPassword().equals(userBody.getConfPassword())) {
						userData.setPassword(bcryptEncoder.encode(userBody.getNewPassword()));
					} else {
						throw new ResourceNotFoundException("new password and confirm password must be same");
					}

				} else {

					throw new ResourceNotFoundException("Please enter old password correct");

				}

			} else {

				throw new ResourceNotFoundException("password must be differ from old password");

			}

		} else {

			throw new ResourceNotFoundException("Access Denied");

		}

	}

	@Override
	public void forgotPasswordConfirm(String token, ForgotPasswordDto userBody, HttpServletRequest request)
			throws ResourceNotFoundException {

		// Java JWT » 3.19.2 dependancy for get the token from expired token
		DecodedJWT jwt = JWT.decode(token);
		// give the current date
		Date CurrentDate = new Date(System.currentTimeMillis());

		// compare current date and expiredDate.
		if (CurrentDate.before(jwt.getExpiresAt())) {

			if (userBody.getPassword().equals(userBody.getConfirmpassword())) {

				// extract the email from token
				String username = null;
				String jwtToken = null;
				// get the token from payload
				jwtToken = userBody.getToken();
				// get the email from token
				username = jwtTokenUtil.getEmailFromToken(jwtToken);
				// check if that email exist in database
				// grab the the user entity if email exist in db.
				UserEntity userData = userRepository.findByEmailAndIsActiveTrue(username)
						.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
				// encode the new password
				// update the entity with new password
				userData.setPassword(bcryptEncoder.encode(userBody.getPassword()));

			} else {

				throw new ResourceNotFoundException("password and confirm password must be a same");

			}

		} else {

			Forgot_password_request forgot_password_request = forgotPasswordRequestRepository
					.getByTokenOrderByIdDesc(token).orElseThrow(() -> new ResourceNotFoundException("Invalid Request"));
			forgot_password_request.setIsActive(false);
			throw new ResourceNotFoundException("Reset the password time out");

		}

	}

	public static String getPlainString(String[] arrs) {

		String result = "";

		if (arrs.length > 0) {

			StringBuilder sb = new StringBuilder();

			for (String s : arrs) {

				sb.append(s).append(",");

			}

			result = sb.deleteCharAt(sb.length() - 1).toString();

		}

		return result;

	}

	@Override
	public List<UserEntity> getAllUsersCount() {

		// TODO Auto-generated method stub
		return userRepository.findAll();

	}

}
