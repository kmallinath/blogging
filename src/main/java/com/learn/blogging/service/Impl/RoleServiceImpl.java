package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.RoleDto;
import com.learn.blogging.entities.Role;
import com.learn.blogging.repository.RoleRepo;
import com.learn.blogging.repository.UserRepo;
import com.learn.blogging.service.RoleService;
import com.learn.blogging.service.UserService;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RoleDto addRole(RoleDto role) {
        Role role1=modelMapper.map(role,Role.class);
        Role saved=roleRepo.save(role1);
        RoleDto savedDto= modelMapper.map(saved,RoleDto.class);
        return savedDto;
    }
}
