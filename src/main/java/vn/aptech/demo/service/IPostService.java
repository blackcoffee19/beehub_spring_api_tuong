package vn.aptech.demo.service;

import java.util.List;

import vn.aptech.demo.dto.PostDto;

public interface IPostService {
	public List<PostDto> findByUserId(Long id);
	public List<PostDto> newestPostsForUser(Long id,int limit);
	public List<PostDto> getSearchPosts(String search,Long id);
	public List<PostDto> newestPostInGroup(Long id_group, Long id_user, int limit);
	public List<PostDto> getPostsForUser(Long id,int page, int limit);
	public List<PostDto>  getAllPostForUser(Long id);
}
