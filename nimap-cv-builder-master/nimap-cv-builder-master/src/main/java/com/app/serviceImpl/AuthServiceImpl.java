package com.app.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entities.UserEntity;
import com.app.repositories.AuthRepository;
import com.app.serviceIntf.AuthInterface;
import com.app.serviceIntf.RoleServiceInterface;
import com.app.utils.CacheOperation;


@Service
public class AuthServiceImpl implements AuthInterface {

	public AuthServiceImpl() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private RoleServiceInterface roleServiceInterface;

	@Autowired
	private CacheOperation cache;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity user;

		if (!cache.isKeyExist(email, emai
				l)) {

			user = authRepository.findByEmail(email);
			cache.addInCache(email, email, user);

		} else {

			user = (UserEntity) cache.getFromCache(email, email); // redisTemplate.opsForHash().get(email, email);

		}

		if (user == null) {

			throw new UsernameNotFoundException("User not found with Email: " + email);

		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));

	}
	// @Override
	// public UserEntity save(UserDto user) {
	// UserEntity newUser = new UserEntity();
	// newUser.setEmail(user.getEmail());
	// newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	// newUser.setName(user.getName());
	// return authRepository.save(newUser);
	// }

	
	
	@Override
	public Boolean comparePassword(String password, String hashPassword) {

		return bcryptEncoder.matches(password, hashPassword);

	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {

		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

		if (!cache.isKeyExist(user.getId() + "permission", user.getId() + "permission")) {

			ArrayList<SimpleGrantedAuthority> authorities1 = new ArrayList<>();
			ArrayList<String> permissions = roleServiceInterface.getPermissionByUserId(user.getId());
			permissions.forEach(permission -> {

				authorities1.add(new SimpleGrantedAuthority("ROLE_" + permission));

			});
			authorities = authorities1;
			cache.addInCache(user.getId() + "permission", user.getId() + "permission", authorities1);

		} else {

			authorities = (ArrayList<SimpleGrantedAuthority>) cache.getFromCache(user.getId() + "permission", user.getId() + "permission");

		}

		return authorities;

	}
	
	
	
	


}
