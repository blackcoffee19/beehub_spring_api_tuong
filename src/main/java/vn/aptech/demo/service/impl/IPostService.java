package vn.aptech.demo.service.impl;

import java.util.List;

import vn.aptech.demo.dto.PostDto;

public interface IPostService {
	public List<PostDto> findByUserId(Long id);
}
