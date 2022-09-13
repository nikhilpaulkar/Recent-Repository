package com.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.AddSettingDto;
import com.app.entities.AppSettingsEntity;
import com.app.exceptionsHandling.ResourceNotFoundException;
import com.app.repositories.AppSettingsRepository;
import com.app.serviceIntf.AppSettingServiceInterface;

@Service("appSettingsServiceImpl")
public class AppSettingsServiceImpl implements AppSettingServiceInterface {

	@Autowired
	private AppSettingsRepository appSettingsRepository;

	@Override
	public List<AppSettingsEntity> getAllSetting() {

		List<AppSettingsEntity> appSettings = appSettingsRepository.findAll();
		return appSettings;

	}

	@Override
	public void addSetting(AddSettingDto settingDetail) {

		AppSettingsEntity newSetting = new AppSettingsEntity();
		newSetting.setIsAdminOnly(settingDetail.getIsAdminOnly());
		newSetting.setKey(settingDetail.getKey());
		newSetting.setValue(settingDetail.getValue());
		appSettingsRepository.save(newSetting);
		return;

	}

	@Override
	public void updateSettingById(Long id, AddSettingDto settingDetail) throws ResourceNotFoundException {

		AppSettingsEntity appSetting = appSettingsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("App Setting Not Found"));
		appSetting.setIsAdminOnly(settingDetail.getIsAdminOnly());
		appSetting.setKey(settingDetail.getKey());
		appSetting.setValue(settingDetail.getValue());
		appSettingsRepository.save(appSetting);
		return;

	}

}
