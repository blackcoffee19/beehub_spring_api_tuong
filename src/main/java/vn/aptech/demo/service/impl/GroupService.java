package vn.aptech.demo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.GroupDto;
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
				GroupDto groupD = new GroupDto(
						group.getId(), 
						group.getGroupname(), 
						group.isPublic_group(), 
						group.getDescription(), 
						group.isActive(), 
						group.getCreated_at(), 
						group.getImage_group() !=null?group.getImage_group().getMedia():null, 
						group.getBackground_group() !=null?group.getBackground_group().getMedia():null,
						groupMemberRep.findMemberInGroup(group.getId(), id_user).isPresent(),
						group.getGroup_members().size()
						);
				listGroups.add(groupD);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		});
		return listGroups;
	}

}
