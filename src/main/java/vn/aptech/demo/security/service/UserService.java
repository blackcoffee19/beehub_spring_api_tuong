package vn.aptech.demo.security.service;

import java.util.Optional;

import vn.aptech.demo.dto.UserDto;


public interface UserService {
	Optional<UserDto> findByEmail(String email);
}
