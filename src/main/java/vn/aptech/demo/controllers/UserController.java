
package vn.aptech.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.dto.UserDto;
import vn.aptech.demo.service.impl.IPostService;
import vn.aptech.demo.service.impl.IUserService;


@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired 
	private IUserService userService;
	@Autowired
	private IPostService postService;
	
	@GetMapping(path = "/users")
	private List<UserDto> getAllUsers(){
		return userService.findAll();
	}
	@GetMapping(path = "/user/{id}")
	private Optional<UserDto> getUser(@PathVariable Long id){
		return userService.getUser(id);
	}
	@GetMapping(path = "/post/{id}")
	private List<PostDto> getPosts(@PathVariable Long id){
		return postService.findByUserId(id);
	}
	@GetMapping(path = "/friendposts/{id}")
	private List<PostDto> getFriendPost(@PathVariable Long id){
		return postService.newestPostsForUser(id);
	}
}
