package com.springboot.enotes.ServiceImpl;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.springboot.enotes.Dto.UserDto;
import com.springboot.enotes.Entity.Role;
import com.springboot.enotes.Entity.User;
import com.springboot.enotes.Repository.RoleRepository;
import com.springboot.enotes.Repository.UserRepository;
import com.springboot.enotes.Service.UserService;
import com.springboot.enotes.util.CustomValidation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private CustomValidation validation; 
	

	@Override
	public Boolean register(UserDto userDto) {

	validation.userValidation(userDto);
		
	User user=mapper.map(userDto, User.class);
	
	setRole(userDto,user);
	User Saveuser=userRepo.save(user);
	if(!ObjectUtils.isEmpty(Saveuser)) {
		return true;
	}
	return false;
	
	
	}


	private void setRole(UserDto userDto, User user) {
		
	List<Integer> reqRoleid= userDto.getRoles().stream().map(r->r.getId()).toList();	
	List<Role> roles=roleRepo.findAllById(reqRoleid);
	user.setRoles(roles);
	
	
		
	}

}
