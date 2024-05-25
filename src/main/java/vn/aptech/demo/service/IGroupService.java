package vn.aptech.demo.service;

import java.util.List;
import java.util.Map;

import vn.aptech.demo.dto.GroupDto;

public interface IGroupService {
	List<GroupDto> searchNameGroup(String search, Long id_user);
	public Map<String, List<GroupDto>> getListGroup(Long id);
}
