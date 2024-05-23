package vn.aptech.demo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.GroupDto;
import vn.aptech.demo.dto.GroupMemberDto;
import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.dto.ProfileDto;
import vn.aptech.demo.dto.SearchingDto;
import vn.aptech.demo.dto.UserDto;
import vn.aptech.demo.dto.UserSettingDto;
import vn.aptech.demo.models.ERelationshipType;

import vn.aptech.demo.models.User;
import vn.aptech.demo.repository.RelationshipUsersRepository;
import vn.aptech.demo.repository.UserRepository;
import vn.aptech.demo.seeders.DatabaseSeeder;
import vn.aptech.demo.service.IGroupService;
import vn.aptech.demo.service.IPostService;
import vn.aptech.demo.service.IUserService;
import vn.aptech.demo.service.IUserSettingService;

@Service
public class UserService implements IUserService {
	private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
	@Autowired
	private UserRepository userRep;
	@Autowired
	private RelationshipUsersRepository relationshipRep;
	@Autowired 
	private IPostService postSer;
	@Autowired
	private IUserSettingService userSettingSer;
	@Autowired
	private IGroupService groupRep;
	@Autowired 
	private ModelMapper mapper;
	private UserDto toDto(User user) {
		return mapper.map(user, UserDto.class);
	}
	@Override
	public List<UserDto> findAll() {
		List<UserDto> list = new LinkedList<UserDto>();
		userRep.findAll().forEach((user)-> {
			list.add(new UserDto(
					user.getId(), 
					user.getUsername(),
					user.getFullname(),
					user.getGender(),
					user.getImage()!=null? user.getImage().getMedia():null,
					user.getImage()!=null?user.getImage().getMedia_type():null));
		});
		return list;
	}
	@Override
	public List<UserDto> getRelationship(Long id){
		List<UserDto> list = new LinkedList<UserDto>();
		userRep.findRelationship(id, ERelationshipType.FRIEND.toString()).forEach((user)->{
			list.add(new UserDto(
					user.getId(),
					user.getUsername(), 
					user.getFullname(), 
					user.getGender(), 
					user.getImage()!=null?user.getImage().getMedia():null,
					user.getImage()!=null?user.getImage().getMedia_type():null,
					ERelationshipType.FRIEND.toString()));
		});
		userRep.findRelationship(id, ERelationshipType.BLOCKED.toString()).forEach((user)->{
			list.add(new UserDto(
					user.getId(),
					user.getUsername(), 
					user.getFullname(), 
					user.getGender(), 
					user.getImage()!=null?user.getImage().getMedia():null, 
					user.getImage()!=null?user.getImage().getMedia_type():null,		
					ERelationshipType.BLOCKED.toString()));
		});
		return list;
	}
	@Override
	public Optional<UserDto> getUser(Long id) {
		return userRep.findById(id).map(t -> {
			UserDto user = new UserDto(
					t.getId(), 
					t.getUsername(), 
					t.getFullname(), 
					t.getGender(), 
					t.getImage()!=null?t.getImage().getMedia():null,
					t.getImage()!=null?t.getImage().getMedia_type():null);
			user.setGroup_counter(t.getGroup_joined().size());
			user.setFriend_counter(0);
			user.setFriend_counter(findAllFriends(id).size());
			return user;
		});
	}
	@Override
	public List<UserDto> findAllFriends(Long id) {
		List<UserDto> list = new LinkedList<UserDto>();
		userRep.findRelationship(id,ERelationshipType.FRIEND.toString()).forEach(e-> list.add(toDto(e)));
		return list;
	}
	public List<GroupMemberDto> getGroupJoined(Long id){
		List<GroupMemberDto> list = new LinkedList<GroupMemberDto>();
		userRep.findById(id).get().getGroup_joined().forEach((gm)-> {
			GroupMemberDto grouM= new GroupMemberDto(
					gm.getId(), 
					gm.getUser().getId(),
					gm.getGroup().getId(),
					gm.getGroup().getGroupname(),
					gm.getGroup().getImage_group()!=null?gm.getGroup().getImage_group().getMedia():null,
					true, 
					gm.getRole().toString());
			list.add(grouM);
		});
		return list;
	}
	
	@Override
	public Optional<ProfileDto> getProfile(String username) {
		return userRep.findByUsername(username).map((user)-> {
			List<GroupMemberDto> grList = getGroupJoined(user.getId());
			List<UserSettingDto> userSetting = userSettingSer.allSettingOfUser(user.getId());
			List<UserDto> relationshipList = getRelationship(user.getId());
			List<PostDto> posts = postSer.findByUserId(user.getId());
			
			return new ProfileDto(
					user.getId(),
					user.getUsername(),
					user.getEmail(),
					user.getFullname(),
					user.getGender(),
					user.getImage()!=null?user.getImage().getMedia():null,
					user.getBackground()!=null?user.getBackground().getMedia():null,
					user.getBio(),
					user.getBirthday(),
					user.isEmail_verified(),
					user.getPhone(),
					user.is_active(),
					user.getActive_at(),
					user.getCreate_at(),
					grList,
					userSetting,
					relationshipList,
					posts
					);
		});
		
	}
	@Override
	public SearchingDto getSearch(Long id,String search) {
		//Search posts
		List<PostDto> listPosts = postSer.getSearchPosts(search, id);
		//Search pepple
		List<UserDto> listPeople = new LinkedList<UserDto>();
		userRep.searchPeople(search,id).forEach((user)->{
			try {
				String relationship = relationshipRep.getRelationship(id, user.getId()).isPresent()?relationshipRep.getRelationship(id, user.getId()).get().getType().toString():null;
				listPeople.add(new UserDto(
						user.getId(),
						user.getUsername(), 
						user.getFullname(), 
						user.getGender(), 
						user.getImage()!=null?user.getImage().getMedia():null,
						user.getImage()!=null?user.getImage().getMedia_type():null,
						relationship,
						user.getGroup_joined().size(),
						findAllFriends(user.getId()).size()));				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		});
		//Search groups
		List<GroupDto> listGroups = groupRep.searchNameGroup(search, id);
		SearchingDto searchDto = new SearchingDto();
		searchDto.setPosts(listPosts);
		searchDto.setPeople(listPeople);
		searchDto.setGroups(listGroups);
		return searchDto;
	}
}