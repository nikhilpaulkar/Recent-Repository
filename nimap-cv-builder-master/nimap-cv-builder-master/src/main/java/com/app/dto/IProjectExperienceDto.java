package com.app.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public interface IProjectExperienceDto 
{	
	
	
	public String getName();
	 
	public Long getId();
	
	@Value("#{@calculateExpUtil.getDurationByStartAndEndDate(target.getProjectStartDate,target.getProjectEndDate)}")
	public String getProjectDuration();
  
	public String getDescription();
	
	public String getResponsibilities();
  
	public int getTeamSize();

	public String getTechnicalStack();
	
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	public String getProjectStartDate();
 
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	public String getProjectEndDate();

	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	public Date getCreatedAt();
	
	@Value("#{target.userId.id}")
	public Long getUserIdId();
}
