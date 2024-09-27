package com.learn.blogging.repository;

import com.learn.blogging.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo  extends JpaRepository<Role, Integer> {


}
