package vn.aptech.demo.service;

import java.util.List;

import vn.aptech.demo.dto.GroupDto;

public interface IGroupService {
	List<GroupDto> searchNameGroup(String search, Long id_user);
}
