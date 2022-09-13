package com.app.serviceIntf;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthInterface {

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
	// public UserEntity save(UserDto user);

	public Boolean comparePassword(String password, String hashPassword);

}
