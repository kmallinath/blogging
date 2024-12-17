package com.learn.blogging.security;

import com.learn.blogging.beans.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthResponse {

    private String token;

    private UserDto user;
}
