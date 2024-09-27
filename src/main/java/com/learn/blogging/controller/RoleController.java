package com.learn.blogging.controller;

import com.learn.blogging.beans.RoleDto;
import com.learn.blogging.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("role/addRole")
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto role)
    {
        RoleDto created=roleService.addRole(role);
        return new ResponseEntity<RoleDto>(created, HttpStatus.CREATED);
    }
}
