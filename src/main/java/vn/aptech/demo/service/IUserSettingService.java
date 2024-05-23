package vn.aptech.demo.service;

import java.util.List;

import vn.aptech.demo.dto.UserSettingDto;

public interface IUserSettingService {
	public List<UserSettingDto> allSettingOfUser(Long id);
}
