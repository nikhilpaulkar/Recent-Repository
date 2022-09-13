package com.app.serviceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.NoSuchElementException;

import java.util.Optional;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.app.dto.CvRequestAddDto;
import com.app.dto.ICvMasterDto;
import com.app.dto.MasterListDto;
import com.app.entities.CvRequestEntity;
import com.app.entities.ProjectExperienceEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.CodingLanguageRepository;
import com.app.repositories.CvRepository;
import com.app.repositories.CvRequestRepository;
import com.app.repositories.ExternalComponentRepository;
import com.app.repositories.FrameworkRepository;
import com.app.repositories.IdeRepository;
import com.app.repositories.OperatingSystemRepository;
import com.app.repositories.OtherSkillRepository;
import com.app.repositories.ProjectExperienceRepository;
import com.app.repositories.ToolRepository;
import com.app.repositories.UserRepository;
import com.app.repositories.VersionControlSystemRepository;
import com.app.repositories.WebServicesRepository;
import com.app.serviceIntf.CvBuildServiceIntf;
import com.app.serviceIntf.ICvInfoMasterDto;
import com.app.utils.PaginationUsingFromTo;
import com.app.utils.PdfGenaratorUtil;
import org.springframework.util.StringUtils;

@Service("cvBuildServiceImpl")
public class CvBuildServiceImpl implements CvBuildServiceIntf {

	@Autowired
	private CvRepository cvRepository;
	
	@Autowired
	private CvRequestRepository cvRequestRepository;

	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;

	@Autowired
	private FrameworkRepository frameworkRepository;

	@Autowired
	private CodingLanguageRepository codingLanguageRepository;

	@Autowired
	private ExternalComponentRepository externalComponentRepository;

	@Autowired
	private IdeRepository ideRepository;

	@Autowired
	private OperatingSystemRepository operatingSystemRepository;

	@Autowired
	private OtherSkillRepository otherSkillRepository;

	@Autowired
	private VersionControlSystemRepository versionControlSystemRepository;

	@Autowired
	private WebServicesRepository webServicesRepository;

	@Autowired
	private ProjectExperienceRepository projectExperienceRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ToolRepository toolRepository;

	// @Autowired
	// private EmailServiceIntf emailServiceIntf;

	@Override
	public String createCv(CvRequestAddDto cvBody, Long userId, Long createdById) throws ResourceNotFoundException {
		CvRequestEntity addedRequest = createCvRequest(cvBody, userId, createdById);
		
		String path = cvBuilder(addedRequest);
		return path;

	}

	@Override
	public String cvBuilder(CvRequestEntity addedRequest) throws ResourceNotFoundException {

		Map<String, Object> data = new HashMap<>();

		UserEntity userDetail = userRepository.findByIdAndIsActiveTrue(addedRequest.getUserId().getId())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		data.put("name", userDetail.getName());
		data.put("designation", userDetail.getDesignationId().getName());
		data.put("summary",userDetail.getSummary());
		data.put("university",userDetail.getUniversityName());
		data.put("YearOfPassing",userDetail.getYearOfPassing());
		data.put("HighestQualification",userDetail.getHighestQualification());
		data.put("DateOfJoining",userDetail.getDateOfJoining());
		
		data.put("experience", getExperienceYears(userDetail.getCareerStartDate()));
		data.put("languages", getSkills("codeLang", addedRequest.getCodeLanguageId()));
		data.put("frameworks", getSkills("framework", addedRequest.getFrameworkId()));
		data.put("ides", getSkills("ide", addedRequest.getIdeId()));
		data.put("summary",userDetail.getSummary());
		data.put("projects", getProjectExp(addedRequest.getUserId().getId()));
		data.put("vcs", getSkills("vcs", addedRequest.getVersionControlSystemId()));
		data.put("extComp", getSkills("extComp", addedRequest.getExternalComponentId()));
		data.put("webServices", getSkills("webService", addedRequest.getWebServiceId()));
		data.put("skills", getSkills("skill", addedRequest.getOtherSkillId()));
		data.put("os", getSkills("os", addedRequest.getOperatingSystemId()));
		data.put("tool", getSkills("tool", addedRequest.getToolId()));
		data.put("projects", getProjectExp(addedRequest.getUserId().getId()));

        
		try 
		{
			String path = pdfGenaratorUtil.createPdf("cvTemplate", data);

			addedRequest.setPath(path);
			cvRequestRepository.save(addedRequest);
			//emailServiceIntf.sendSimpleMessage();
			
			return path;

		} catch (Exception e) {

			System.out.println("e " + e);
			return null;

		}

	}

	

	@Override
	public CvRequestEntity createCvRequest(CvRequestAddDto cvBody, Long userId, Long createdById)
			throws ResourceNotFoundException {
		CvRequestEntity newRequest = new CvRequestEntity();
		newRequest.setCodeLanguageId(cvBody.getCodeLanguageId());
		newRequest.setExternalComponentId(cvBody.getExternalComponentId());
		newRequest.setFrameworkId(cvBody.getFrameworkId());
		newRequest.setIdeId(cvBody.getIdeId());
		newRequest.setOperatingSystemId(cvBody.getOperatingSystemId());
		newRequest.setOtherSkillId(cvBody.getOtherSkillId());
		//newRequest.setProjectExperienceId(cvBody.getProjectExperienceId());
        newRequest.setVersionControlSystemId(cvBody.getVersionControlSystemId());
		newRequest.setWebServiceId(cvBody.getWebServiceId());
		newRequest.setToolId(cvBody.getToolId());
	
		UserEntity userData = new UserEntity();
		userData.setId(cvBody.getUserId());
		UserEntity creatingUser = new UserEntity();
		creatingUser.setId(createdById);

		newRequest.setUserId(userData);
		newRequest.setCreatedBy(creatingUser);	 
		try 
		{
			Optional<CvRequestEntity> databaseData = cvRequestRepository.getuser(cvBody.getUserId());
			Long d = databaseData.get().getUserId().getId();
			if (cvBody.getUserId() != d) 
			{
				CvRequestEntity addedRequest = cvRequestRepository.save(newRequest);
				return addedRequest;
			}
			{
				throw new ResourceNotFoundException("Cv of user already exist");
			}
		} 
		catch (NoSuchElementException e) 
		{
			CvRequestEntity addedRequest = cvRequestRepository.save(newRequest);
			return addedRequest;
		}  
	}

//		Long id = cvBody.getUserId();

	private String getExperienceYears(Date careerStartDate) {

		LocalDate date1 = LocalDate.parse(careerStartDate.toString());
		
		LocalDate date2 = LocalDate.now();
		Period period = date1.until(date2);
		float yearsBetween = period.getYears() + ((float) period.getMonths() / 12);
		return String.format("%.1f", yearsBetween);

	}

	private String getSkills(String entityName, ArrayList<Long> idArr) {

		ArrayList<MasterListDto> list = new ArrayList<>();

		switch (entityName) {

		case "framework":
			list = frameworkRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "codeLang":
			list = codingLanguageRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "extComp":
			list = externalComponentRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "ide":
			list = ideRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "os":
			list = operatingSystemRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "skill":
			list = otherSkillRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "vcs":
			list = versionControlSystemRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "webService":
			list = webServicesRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		case "tool":
			list = toolRepository.findByIdIn(idArr, MasterListDto.class);
			break;

		default:
			break;

		}

		StringBuilder frameworkList = new StringBuilder("");

		if (list.size() > 0) {
			for (MasterListDto eachstring : list) {

				frameworkList.append(eachstring.getName()).append(",");

				System.out.println("$$$$$$$$$" + eachstring.getName().length());

			}
		}

//		return frameworkList.toString();

		return frameworkList.deleteCharAt(frameworkList.length() - 1).toString();

	}
	
    
	private List<ProjectExperienceEntity> getProjectExp(Long userId) {

		List<ProjectExperienceEntity> projects = projectExperienceRepository
				.findByUserIdIdAndIsActiveTrueOrderByCreatedAtDesc(userId);
		return projects;

	}

	@Override
	public List<CvRequestEntity> getAllEmployee() {

		return cvRequestRepository.findAll();

	}
	

	@Override
	public Page<ICvMasterDto> findAll(String search, String from, String to) {
		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<ICvMasterDto> cvList;

		if ((search == "") || (search == null) || (search.length() == 0)) {
			
			cvList = cvRequestRepository.findByOrderByIdDesc(paging, ICvMasterDto.class);

		} else {

			cvList = cvRequestRepository.findByOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging,
					CvRequestEntity.class);

		}

		return cvList;
	}

	@Override
	public Page<ICvInfoMasterDto> findAllCv(String search, String from, String to) {
		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<ICvInfoMasterDto> cvList;
		

		if ((search == "") || (search == null) || (search.length() == 0)) {
			
 
			cvList = cvRepository.findByOrderByIdDesc(paging, ICvInfoMasterDto.class);

		} 
		else {

			cvList = cvRepository.findByOrderByIdDesc(StringUtils.trimLeadingWhitespace(search), paging,
					ICvInfoMasterDto.class);

		}

		return cvList;
	}

	@Override
	public ICvInfoMasterDto findById(Long id) throws ResourceNotFoundException {

		ICvInfoMasterDto cv = cvRepository.findByIdAndIsActiveTrue(id, ICvInfoMasterDto.class).orElseThrow(() -> new ResourceNotFoundException("cv Not Found"));

		return cv;

	}

	@Override
	public void editCv(CvRequestAddDto cvBody, Long cvId, Long userId) throws ResourceNotFoundException {
		 CvRequestEntity cvDetail =  cvRepository.findById(cvId).orElseThrow(() -> new ResourceNotFoundException("cv Not Found"));
		 cvDetail.setVersionControlSystemId(cvBody.getVersionControlSystemId());
		 cvDetail.setExternalComponentId(cvBody.getExternalComponentId());
		 cvDetail.setOperatingSystemId(cvBody.getOperatingSystemId());
		 cvDetail.setOtherSkillId(cvBody.getOtherSkillId());
		 cvDetail.setWebServiceId(cvBody.getWebServiceId());
		 cvDetail.setToolId(cvBody.getToolId());
		 cvDetail.setCodeLanguageId(cvBody.getCodeLanguageId());
		 cvDetail.setFrameworkId(cvBody.getFrameworkId());
		 cvDetail.setIdeId(cvBody.getIdeId());		
		 UserEntity userDetail = new UserEntity();
		 userDetail.setId(userId);
		 cvRepository.save(cvDetail);
		 
	}
 
}
