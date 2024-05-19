package vn.aptech.demo.service.impl;

import java.util.List;
import java.util.Optional;

import vn.aptech.demo.dto.UserDto;

public interface IUserService {
	public List<UserDto> findAll();
	public Optional<UserDto> getUser(Long id);
}
