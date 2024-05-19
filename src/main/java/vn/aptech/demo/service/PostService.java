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
	@Autowired 
	private ModelMapper mapper;
	private PostDto toDto(Post post) {
		return mapper.map(post, PostDto.class);
	}
	@Override
	public List<PostDto> findByUserId(Long id) {
		List<PostDto> listPost = new LinkedList<PostDto>();
		postRep.findByUserId(id).forEach((post)->listPost.add(toDto(post)));
		return listPost;
	}

}
