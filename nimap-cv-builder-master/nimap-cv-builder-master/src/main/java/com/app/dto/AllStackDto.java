package com.app.dto;

import java.util.List;

public class AllStackDto {

	public AllStackDto() {

		super();

		// TODO Auto-generated constructor stub
	}

	public AllStackDto(String masterName, List<MasterListDto> stacks) {

		super();
		this.masterName = masterName;
		this.stacks = stacks;

	}

	private String masterName;

	private List<MasterListDto> stacks;

	public String getMasterName() {

		return masterName;

	}

	public void setMasterName(String masterName) {

		this.masterName = masterName;

	}

	public List<MasterListDto> getStacks() {

		return stacks;

	}

	public void setStacks(List<MasterListDto> stacks) {

		this.stacks = stacks;

	}

}
