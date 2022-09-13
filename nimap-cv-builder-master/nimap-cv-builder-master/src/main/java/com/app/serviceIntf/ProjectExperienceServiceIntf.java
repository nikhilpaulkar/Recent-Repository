package com.app.serviceIntf;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.app.dto.AllStackDto;
import com.app.dto.IProjectExperienceDto;
import com.app.dto.ProjectAddDto;
import com.app.exceptionsHandling.ResourceNotFoundException;

public interface ProjectExperienceServiceIntf {

	Page<IProjectExperienceDto> findAll(String search, String from, String to, Long userId);

	IProjectExperienceDto findById(Long id, Long userId) throws ResourceNotFoundException;

	void addProject(ProjectAddDto projectBody, Long projectUserId, Long userId);

	void editProject(ProjectAddDto projectBody, Long projectId, Long userId, Long projectUserId) throws ResourceNotFoundException;

	void deleteProject(Long projectId, Long userId, Long projectUserId) throws ResourceNotFoundException;

	void exportProjects(Long userId);

	void projectBulkUpload(Long fileId, Long userId) throws IOException;

	List<AllStackDto> getAllStack();

}
