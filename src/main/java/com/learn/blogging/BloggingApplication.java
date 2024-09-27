package com.learn.blogging;

import com.learn.blogging.entities.Role;
import com.learn.blogging.repository.RoleRepo;
import com.learn.blogging.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
		System.out.println("CODE STARTED");

}


	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}


	@Override
	public void run(String... args)
	{
		try{
			if(roleRepo.findAll().isEmpty()) {
				Role role1 = new Role();
				role1.setId(AppConstants.ADMIN_USER);
				role1.setRoleName("ADMIN");

				Role role2 = new Role();
				role2.setId(AppConstants.NORMAL_USER);
				role2.setRoleName("USER");

				List<Role> roles = List.of(role1, role2);
				this.roleRepo.saveAll(roles);
			}
			else
			{
				System.out.println("Roles are already Added");
			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}







}
