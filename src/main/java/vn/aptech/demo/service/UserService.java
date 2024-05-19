package vn.aptech.demo.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.aptech.demo.dto.UserDto;
import vn.aptech.demo.models.User;
import vn.aptech.demo.repository.UserRepository;
import vn.aptech.demo.service.impl.IUserService;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRep;
	@Autowired 
	private ModelMapper mapper;
	private UserDto toDto(User user) {
		return mapper.map(user, UserDto.class);
	}
	@Override
	public List<UserDto> findAll() {
		List<UserDto> list = new LinkedList<UserDto>();
		userRep.findAll().forEach((user)-> list.add(toDto(user)));
		return list;
	}
	@Override
	public Optional<UserDto> getUser(Long id) {
		return userRep.findById(id).map(this::toDto);
	}

}
