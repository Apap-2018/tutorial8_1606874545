package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Override
	public String encrypt(String password) {
		
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@Override
	public Boolean cekPassword(String username, PasswordModel pass) {
		UserRoleModel userTarget = userDb.findByUsername(username);
		return passwordEncoder.matches(pass.getPasswordLama(), userTarget.getPassword() );
	}
	
	@Override
	public void updatePassword(String username, PasswordModel pass) {
		UserRoleModel userTarget = userDb.findByUsername(username);
		String passBaru = encrypt(pass.getPasswordBaru());
		userTarget.setPassword(passBaru);
		userDb.save(userTarget);
	}
	
}
