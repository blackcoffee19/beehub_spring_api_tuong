package vn.aptech.demo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.GalleryDto;
import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.models.ESettingType;
import vn.aptech.demo.repository.PostRepository;
import vn.aptech.demo.service.IPostService;
@Service
public class PostService implements IPostService {
	@Autowired
	private PostRepository postRep;
	
	@Override
	public List<PostDto> findByUserId(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.findByUserId(id).forEach((post)-> {
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getUser().getId(), m.getMedia(), m.getMedia_type()));
			});
			listPost.add(new PostDto(
								post.getId(), 
								post.getText(), 
								media, 
								post.getUser().getId(),
								post.getGroup()!=null? post.getGroup().getId(): null, 
								post.getCreate_at(),
								post.getUser().getFullname(),
								post.getUser().getImage()!=null?post.getUser().getImage().getMedia():null,
								post.getUser().getGender(),
								post.getGroup()!=null?post.getGroup().getGroupname():null,
								post.getGroup()!=null?post.getGroup().isPublic_group():false,
								post.getGroup()!=null && post.getGroup().getImage_group()!=null?post.getGroup().getImage_group().getMedia():null,
								post.getUser_setting()!=null?post.getUser_setting().getSetting_type().toString():ESettingType.PUBLIC.toString()
								));
		});
		return listPost;
	}
	@Override
	public List<PostDto> newestPostsForUser(Long id, int limit) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.randomNewestPostFromGroupAndFriend(id,limit).forEach((post)->{
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getUser().getId(), m.getMedia(), m.getMedia_type()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getImage()!=null? post.getUser().getImage().getMedia():null,
					post.getUser().getGender(),
					post.getGroup()!=null?post.getGroup().getGroupname():null,
					post.getGroup()!=null?post.getGroup().isPublic_group():false,
					post.getGroup()!=null && post.getGroup().getImage_group()!=null?post.getGroup().getImage_group().getMedia():null,
					post.getUser_setting()!=null?post.getUser_setting().getSetting_type().toString():ESettingType.PUBLIC.toString()
					));});
		return listPost;
	}
	@Override
	public List<PostDto> getSearchPosts(String search, Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.searchPublicPostsContain(search, id).forEach((post)->{
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getUser().getId(), m.getMedia(), m.getMedia_type()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getImage()!=null? post.getUser().getImage().getMedia():null,
					post.getUser().getGender(),
					post.getGroup()!=null?post.getGroup().getGroupname():null,
					post.getGroup()!=null?post.getGroup().isPublic_group():false,
					post.getGroup()!=null && post.getGroup().getImage_group() !=null?post.getGroup().getImage_group().getMedia():null,
					post.getUser_setting()!=null?post.getUser_setting().getSetting_type().toString():ESettingType.PUBLIC.toString()
					));});
		postRep.searchPostsInGroupJoinedContain(search, id).forEach((post)->{
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getUser().getId(), m.getMedia(), m.getMedia_type()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getImage()!=null? post.getUser().getImage().getMedia():null,
					post.getUser().getGender(),
					post.getGroup()!=null?post.getGroup().getGroupname():null,
					post.getGroup()!=null?post.getGroup().isPublic_group():false,
					post.getGroup()!=null && post.getGroup().getImage_group()!=null?post.getGroup().getImage_group().getMedia():null,
					post.getUser_setting()!=null?post.getUser_setting().getSetting_type().toString():ESettingType.PUBLIC.toString()
					));});
		return listPost;
	}

}
