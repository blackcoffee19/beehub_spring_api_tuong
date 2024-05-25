package vn.aptech.demo.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Arrays;
import vn.aptech.demo.dto.GroupDto;
import vn.aptech.demo.models.GroupMember;
import vn.aptech.demo.repository.GroupMemberRepository;
import vn.aptech.demo.repository.GroupRepository;
import vn.aptech.demo.seeders.DatabaseSeeder;
import vn.aptech.demo.service.IGroupService;

@Service
public class GroupService implements IGroupService {
	private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
	@Autowired
	private GroupRepository groupRep;
	@Autowired
	private GroupMemberRepository groupMemberRep;
	@Override
	public List<GroupDto> searchNameGroup(String search, Long id_user) {
		List<GroupDto> listGroups = new LinkedList<GroupDto>();
		groupRep.findByGroupnameContains(search).forEach((group)->{
			try {
				Optional<GroupMember> groupmem = groupMemberRep.findMemberInGroup(group.getId(), id_user);
				GroupDto groupD = new GroupDto(
						group.getId(), 
						group.getGroupname(), 
						group.isPublic_group(), 
						group.getDescription(), 
						group.isActive(), 
						group.getCreated_at(), 
						group.getImage_group() !=null?group.getImage_group().getMedia():null, 
						group.getBackground_group() !=null?group.getBackground_group().getMedia():null,
						groupmem.isPresent(),
						groupmem.isPresent()? groupmem.get().getRole().toString(): null,
						group.getGroup_members().size()
						);
				listGroups.add(groupD);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		});
		return listGroups;
	}
	@Override
	public Map<String, List<GroupDto>> getListGroup(Long id_user) {
		Map<String, List<GroupDto>> mapGroup = new HashMap<String, List<GroupDto>>();
		List<GroupDto> groupJoined = new LinkedList<GroupDto>();
		groupRep.findAllGroupJoined(id_user).forEach((group)->{
			Optional<GroupMember> getMem = groupMemberRep.findMemberInGroup(group.getId(), id_user);
			groupJoined.add(new GroupDto(
					group.getId(), 
					group.getGroupname(), 
					group.isPublic_group(), 
					group.getDescription(), 
					group.isActive(), 
					group.getCreated_at(), 
					group.getImage_group() !=null?group.getImage_group().getMedia():null, 
					group.getBackground_group() !=null?group.getBackground_group().getMedia():null,
					getMem.isPresent(),
					getMem.isPresent()?getMem.get().getRole().toString():null,
					group.getGroup_members().size()
					));
		});
		List<GroupDto> groupOwn = new LinkedList<GroupDto>();
		groupRep.findAllOwnGroup(id_user).forEach((group)->{
			Optional<GroupMember> getMem = groupMemberRep.findMemberInGroup(group.getId(), id_user);
			groupOwn.add(new GroupDto(
					group.getId(), 
					group.getGroupname(), 
					group.isPublic_group(), 
					group.getDescription(), 
					group.isActive(), 
					group.getCreated_at(), 
					group.getImage_group() !=null?group.getImage_group().getMedia():null, 
					group.getBackground_group() !=null?group.getBackground_group().getMedia():null,
					getMem.isPresent(),
					getMem.isPresent()?getMem.get().getRole().toString():null,
					group.getGroup_members().size()
					));
		});
		mapGroup.put("joined_groups", groupJoined);
		mapGroup.put("own_group", groupOwn);
		return mapGroup;
	}

}
