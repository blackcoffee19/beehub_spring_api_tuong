package vn.aptech.demo.service;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.PostDto;
import vn.aptech.demo.models.Post;
import vn.aptech.demo.repository.PostRepository;
import vn.aptech.demo.service.impl.IPostService;
@Service
public class PostService implements IPostService {
	@Autowired
	private PostRepository postRep;
	
	@Override
	public List<PostDto> findByUserId(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.findByUserId(id).forEach((post)->
						listPost.add(
								new PostDto(
										post.getId(), 
										post.getText(), 
										post.getMedia(), 
										post.getUser().getId(),
										post.getGroup()!=null? post.getGroup().getId(): null, 
												post.getCreate_at())));
		return listPost;
	}
	@Override
	public List<PostDto> newestPostsForUser(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.newestPostFromFriend(id).forEach((post)->{
			System.out.println(post);
			listPost.add( new PostDto(post.getId(), post.getText(), post.getMedia(), post.getUser().getId(),post.getGroup()!=null? post.getGroup().getId(): null, post.getCreate_at()));
		});
		return listPost;
	}

}
