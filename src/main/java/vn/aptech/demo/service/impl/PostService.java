package vn.aptech.demo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.GalleryDto;
import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.models.ESettingType;
import vn.aptech.demo.models.Group;
import vn.aptech.demo.models.GroupMember;
import vn.aptech.demo.repository.GroupMemberRepository;
import vn.aptech.demo.repository.GroupRepository;
import vn.aptech.demo.repository.PostRepository;
import vn.aptech.demo.service.IPostService;
@Service
public class PostService implements IPostService {
	@Autowired
	private PostRepository postRep;
	@Autowired 
	private GroupMemberRepository groupMemberRep;
	@Autowired
	private GroupRepository groupRep;
	
	@Override
	public List<PostDto> findByUserId(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.findByUserId(id).forEach((post)-> {
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
			});
			
			listPost.add(new PostDto(
								post.getId(), 
								post.getText(), 
								media, 
								post.getUser().getId(),
								post.getGroup()!=null? post.getGroup().getId(): null, 
								post.getCreate_at(),
								post.getUser().getFullname(),
								post.getUser().getUsername(),
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
				media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getUsername(),
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
				media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getUsername(),
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
				media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getUsername(),
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
	public List<PostDto> newestPostInGroup(Long id_group, Long id_user, int limit) {
		List<PostDto> list = new LinkedList<PostDto>();
		Optional<GroupMember> groupMem = groupMemberRep.findMemberInGroupWithUser(id_group, id_user);
		Optional<Group> group = groupRep.findById(id_group);
		if( groupMem.isPresent()  || (group.get().isActive()&& group.get().isPublic_group())) {
			postRep.randomNewestPostFromGroup(id_group, id_user, limit).forEach((post)->{
				List<GalleryDto> media = new LinkedList<GalleryDto>();
				post.getMedia().forEach((m)-> {
					media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
				});
				list.add(new PostDto(
						post.getId(), 
						post.getText(), 
						media, post.getUser().getId(), 
						id_group, 
						post.getCreate_at(), 
						post.getUser().getFullname(),
						post.getUser().getUsername(), 
						post.getUser().getImage()!=null? post.getUser().getImage().getMedia():null, 
								post.getUser().getGender(), 
								post.getGroup().getGroupname(), 
								post.getGroup().isPublic_group(), 
								post.getGroup().getImage_group()!=null? post.getGroup().getImage_group().getMedia():null, 
										post.getUser_setting()!=null? post.getUser_setting().getSetting_type().toString(): ESettingType.PUBLIC.toString()
						));
			});;
			
		}
		return list;
	}
	@Override
	public List<PostDto> getPostsForUser(Long id, int page, int limit) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		int firsIndex = page*limit <getAllPostForUser(id).size() ?page* limit :-1;
		int lastIndex = (page*limit) +5 <=getAllPostForUser(id).size()?(page*limit) +5 :getAllPostForUser(id).size();
		if(firsIndex>=0) {
			listPost = getAllPostForUser(id).subList(firsIndex, lastIndex);			
		}
		return listPost;
	}
	@Override
	public List<PostDto> getAllPostForUser(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.getAllPostsFromGroupAndFriend(id).forEach((post)->{
			List<GalleryDto> media = new LinkedList<GalleryDto>();
			post.getMedia().forEach((m)-> {
				media.add(new GalleryDto(m.getId(),m.getUser().getId(), m.getPost().getId(),m.getMedia(), m.getMedia_type(), m.getCreate_at()));
			});
			listPost.add( new PostDto(
					post.getId(), 
					post.getText(), 
					media,
					post.getUser().getId(),
					post.getGroup()!=null? post.getGroup().getId(): null, 
					post.getCreate_at(),
					post.getUser().getFullname(),
					post.getUser().getUsername(),
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
