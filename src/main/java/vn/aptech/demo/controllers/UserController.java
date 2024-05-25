
package vn.aptech.demo.controllers;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import vn.aptech.demo.dto.FileInfo;
import vn.aptech.demo.dto.GroupDto;
import vn.aptech.demo.dto.GroupMemberDto;
import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.dto.ProfileDto;
import vn.aptech.demo.dto.SearchingDto;
import vn.aptech.demo.dto.UserDto;
import vn.aptech.demo.service.IFilesStorageService;
import vn.aptech.demo.service.IGroupService;
import vn.aptech.demo.service.IPostService;
import vn.aptech.demo.service.IUserService;


@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired 
	private IUserService userService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IFilesStorageService storageService;
	
	@GetMapping(path = "/users")
	private List<UserDto> getAllUsers(){
		return userService.findAll();
	}
	@GetMapping(path = "/user/{id}")
	private Optional<UserDto> getUser(@PathVariable Long id){
		return userService.getUser(id);
	}
	@GetMapping(path="/profile/{username}")
	private Optional<ProfileDto> getProfile(@PathVariable String username){
		return userService.getProfile(username);
	}
	@GetMapping(path="/friends/{id}")
	private List<UserDto> getFriends(@PathVariable Long id){
		return userService.findAllFriends(id);
	}
	@GetMapping(path="/groups_friends/{id}")
	private Map<String, List<Object>> getGroupsAndFriends(@PathVariable Long id){
		return userService.getGroupJoinedAndFriends(id);
	}
	@GetMapping(path = "/post/{id}")
	private List<PostDto> getPosts(@PathVariable Long id){
		return postService.findByUserId(id);
	}
	@GetMapping(path = "/homepage/{id}")
	private List<PostDto> getFriendPost(@PathVariable Long id,@RequestParam(defaultValue = "5") int limit){
		return postService.newestPostsForUser(id, limit);
	}
	@GetMapping(path = "/peoplepage/{id}")
	private Map<String, List<UserDto>> getPeople(@PathVariable Long id ){
		return userService.getPeople(id);
	}
	@GetMapping(path = "/listgroup_page/{id}")
	private Map<String, List<GroupDto>> getListGroups(@PathVariable Long id){
		return groupService.getListGroup(id);
	}
	@GetMapping(path = "/user/{id}/search_all")
	private SearchingDto getSearchString(@PathVariable Long id,@RequestParam(required = true) String search){
		return userService.getSearch(id,search);
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	 }
	@GetMapping("/files")
	  public ResponseEntity<List<FileInfo>> getListFiles() {
	    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
	      String filename = path.getFileName().toString();
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(UserController.class, "getFile", path.getFileName().toString()).build().toString();

	      return new FileInfo(filename, url);
	    }).collect(Collectors.toList());

	    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	  }
	
}
