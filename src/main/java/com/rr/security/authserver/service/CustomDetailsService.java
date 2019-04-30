package com.rr.security.authserver.service;

import com.rr.security.authserver.model.CustomUser;
import com.rr.security.authserver.dao.UserDAO;
import com.rr.security.authserver.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {

	@Autowired
	UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userDAO.getUserDetails(username);
		CustomUser customUser = new CustomUser(userModel);
		return customUser;
	}

}
