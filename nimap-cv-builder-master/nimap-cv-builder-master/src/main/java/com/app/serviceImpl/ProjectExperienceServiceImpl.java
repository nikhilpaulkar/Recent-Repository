package com.app.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.app.dto.AllStackDto;
import com.app.dto.IProjectExperienceDto;
import com.app.dto.MasterListDto;
import com.app.dto.ProjectAddDto;
import com.app.entities.FileUploadEntity;
import com.app.entities.ProjectExperienceBulkUploadEntity;
import com.app.entities.ProjectExperienceEntity;
import com.app.entities.UserEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.FileUploadRepository;
import com.app.repositories.ProjectExperienceBulkUploadRepository;
import com.app.repositories.ProjectExperienceRepository;
import com.app.serviceIntf.CodingLanguageServiceIntf;
import com.app.serviceIntf.ExternalComponentServiceIntf;
import com.app.serviceIntf.FileStorageServiceInterface;
import com.app.serviceIntf.FrameworkServiceIntf;
import com.app.serviceIntf.IdeServiceIntf;
import com.app.serviceIntf.OperatingSystemServiceIntf;
import com.app.serviceIntf.OtherSkillServiceIntf;
import com.app.serviceIntf.ProjectExperienceServiceIntf;
import com.app.serviceIntf.ToolsServiceIntf;
import com.app.serviceIntf.VersionControlSystemServiceIntf;
import com.app.serviceIntf.WebServicesServiceIntf;
import com.app.utils.CalculateExpUtil;
import com.app.utils.ExcelExporter;
import com.app.utils.PaginationUsingFromTo;

@Service("projectExperienceServiceImpl")
@Transactional
public class ProjectExperienceServiceImpl implements ProjectExperienceServiceIntf {

	@Autowired
	private ProjectExperienceRepository projectExperienceRepository;

	@Autowired
	private ExcelExporter excelExporter;

	@Autowired
	private FileStorageServiceInterface fileStorageServiceInterface;

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Autowired
	private ProjectExperienceBulkUploadRepository projectExperienceBulkUploadRepository;

	@Autowired
	private CodingLanguageServiceIntf codingLanguageServiceIntf;

	@Autowired
	private ExternalComponentServiceIntf externalComponentServiceIntf;

	@Autowired
	private FrameworkServiceIntf frameworkServiceIntf;

	@Autowired
	private IdeServiceIntf ideServiceIntf;

	@Autowired
	private OperatingSystemServiceIntf operatingSystemServiceIntf;

	@Autowired
	private OtherSkillServiceIntf otherSkillServiceIntf;

	@Autowired
	private ToolsServiceIntf toolsServiceIntf;

	@Autowired
	private VersionControlSystemServiceIntf versionControlSystemServiceIntf;

	@Autowired
	private WebServicesServiceIntf webServicesServiceIntf;
	@Autowired
	private CalculateExpUtil calculateExpUtil;
	@Autowired
	private CvBuildServiceImpl CvBuildServiceImpl;
	
	
	@Override
	public Page<IProjectExperienceDto> findAll(String search, String from, String to, Long userId) {

		
		Pageable paging = new PaginationUsingFromTo().getPagination(from, to);
		Page<IProjectExperienceDto> projectList;
		 

		if ((search == "") || (search == null) || (search.length() == 0)) {

			projectList = projectExperienceRepository.findByUserIdIdAndIsActiveTrueOrderByIdDesc(userId, paging,
					IProjectExperienceDto.class);

		} else {

			projectList = projectExperienceRepository.findByNameContainingIgnoreCaseAndUserIdIdAndIsActiveTrueOrderByIdDesc(
					StringUtils.trimLeadingWhitespace(search), userId, paging, IProjectExperienceDto.class);
		}
		return projectList;

	}

	@Override
	public IProjectExperienceDto findById(Long id, Long userId) throws ResourceNotFoundException {

		IProjectExperienceDto projectDetail = projectExperienceRepository
				.findByIdAndUserIdId(id, userId, IProjectExperienceDto.class)
				.orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));
		return projectDetail;

	}

	@Override
	public void addProject(ProjectAddDto projectBody, Long projectUserId, Long userId) {

		ProjectExperienceEntity newProject = new ProjectExperienceEntity();
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		UserEntity projectUserDetail = new UserEntity();
		projectUserDetail.setId(projectUserId);
		newProject.setDescription(projectBody.getDescription());
		newProject.setName(projectBody.getName());
		newProject.setProjectStartDate(projectBody.getProjectStartAt());
		newProject.setProjectEndDate(projectBody.getProjectEndAt());
		newProject.setResponsibilities(getPlainString(projectBody.getResponsibilities()));
		newProject.setTeamSize(projectBody.getTeamSize());
		newProject.setTechnicalStack(getPlainString(projectBody.getTechnicalStack()));
		newProject.setUserId(projectUserDetail);
		newProject.setCreatedBy(userDetail);
		newProject.setUpdatedBy(userDetail);
		String durationDiffernce=calculateExpUtil.getDurationByStartAndEndDate(projectBody.getProjectStartAt(), projectBody.getProjectEndAt());
		newProject.setduration(durationDiffernce);
		newProject.getduration();
		projectExperienceRepository.save(newProject);
		
				

	}

	@Override
	public void editProject(ProjectAddDto projectBody, Long projectId, Long userId, Long projectUserId)
			throws ResourceNotFoundException {

		ProjectExperienceEntity newProject = projectExperienceRepository.findByIdAndUserIdId(projectId, projectUserId)
				.orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newProject.setDescription(projectBody.getDescription());
		newProject.setName(projectBody.getName());
		newProject.setProjectStartDate(projectBody.getProjectStartAt());
		newProject.setProjectEndDate(projectBody.getProjectEndAt());
		newProject.setResponsibilities(getPlainString(projectBody.getResponsibilities()));
		newProject.setTeamSize(projectBody.getTeamSize());
		newProject.setTechnicalStack(getPlainString(projectBody.getTechnicalStack()));
		newProject.setUpdatedBy(userDetail);
		projectExperienceRepository.save(newProject);
		return;

	}

	@Override
	public void deleteProject(Long projectId, Long userId, Long projectUserId) throws ResourceNotFoundException {

		ProjectExperienceEntity newProject = projectExperienceRepository.findByIdAndUserIdId(projectId, projectUserId)
				.orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));
		UserEntity userDetail = new UserEntity();
		userDetail.setId(userId);
		newProject.setIsActive(false);
		newProject.setUpdatedBy(userDetail);
		projectExperienceRepository.save(newProject);
		return;

	}

	@Override
	public void exportProjects(Long userId) {

		List<ProjectExperienceEntity> projects = projectExperienceRepository
				.findByUserIdIdAndIsActiveTrueOrderByCreatedAtDesc(userId);
		excelExporter.writeToExcel("projects.xlsx", projects);
		return;

	}
	
	@Override
	public void projectBulkUpload(Long fileId, Long userId) throws IOException {

		FileUploadEntity fileDetail = fileUploadRepository.getById(fileId);
		Resource resource = fileStorageServiceInterface
				.loadFileAsResource(fileDetail.getType() + "/" + fileDetail.getOriginalName());
		ArrayList<ProjectExperienceBulkUploadEntity> tempProjectList = new ArrayList<>();
		XSSFWorkbook workbook = new XSSFWorkbook(resource.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			ProjectExperienceBulkUploadEntity project = new ProjectExperienceBulkUploadEntity();
			XSSFRow row = worksheet.getRow(i);

			if (row == null) {

				break;

			}

			project.setName(row.getCell(0).getStringCellValue());
			project.setFileId(fileDetail.getId());
			project.setUserId(userId);
			project.setDescription(row.getCell(1).getStringCellValue());
			project.setResponsibilities(row.getCell(2).getStringCellValue());
			project.setTeamSize((int) row.getCell(3).getNumericCellValue());
			project.setTechnicalStack(row.getCell(4).getStringCellValue());
			project.setProjectStartDate(row.getCell(5).getDateCellValue());
			project.setProjectEndDate(row.getCell(6).getDateCellValue());
			project.setCreatedBy(userId);
			project.setUpdatedBy(userId);
			tempProjectList.add(project);

		}

		projectExperienceBulkUploadRepository.saveAll(tempProjectList);

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
	public List<AllStackDto> getAllStack() {

		List<MasterListDto> langs = codingLanguageServiceIntf.findAll();
		List<MasterListDto> extComps = externalComponentServiceIntf.findAll();
		List<MasterListDto> frameworks = frameworkServiceIntf.findAll();
		List<MasterListDto> ides = ideServiceIntf.findAll();
		List<MasterListDto> os = operatingSystemServiceIntf.findAll();
		List<MasterListDto> skills = otherSkillServiceIntf.findAll();
		List<MasterListDto> tools = toolsServiceIntf.findAll();
		List<MasterListDto> vcs = versionControlSystemServiceIntf.findAll();
		List<MasterListDto> webServices = webServicesServiceIntf.findAll();
		List<AllStackDto> allStacks = new ArrayList<>();
		allStacks.add(new AllStackDto("Coding Languages", langs));
		allStacks.add(new AllStackDto("External Components", extComps));
		allStacks.add(new AllStackDto("Frameworks", frameworks));
		allStacks.add(new AllStackDto("IDE", ides));
		allStacks.add(new AllStackDto("Operating System", os));
		allStacks.add(new AllStackDto("Skills", skills));
		allStacks.add(new AllStackDto("Tools", tools));
		allStacks.add(new AllStackDto("Version Control System", vcs));
		allStacks.add(new AllStackDto("Web Services", webServices));
		return allStacks;

	}

}
