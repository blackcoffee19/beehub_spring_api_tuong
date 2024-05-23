package vn.aptech.demo.service;

import java.util.List;
import java.util.Optional;

import vn.aptech.demo.dto.ProfileDto;
import vn.aptech.demo.dto.SearchingDto;
import vn.aptech.demo.dto.UserDto;

public interface IUserService {
	public List<UserDto> findAll();
	public List<UserDto> findAllFriends(Long id);
	public Optional<UserDto> getUser(Long id);
	public Optional<ProfileDto> getProfile(String username);
	public List<UserDto> getRelationship(Long id);
	public SearchingDto getSearch(Long id,String search);
}
