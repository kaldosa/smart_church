package com.laonsys.smartchurch.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.laonsys.smartchurch.domain.JoinFormBean;
import com.laonsys.smartchurch.domain.Response;
import com.laonsys.smartchurch.domain.StatusCode;
import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface UserService extends UserDetailsService {
	public User findById(int userId);

	public User findByEmail(String email, String name);

	public User selectUser(int id, String email, String name, String password);
	
	public User selectUser(User user);
	
	public User addUser(User user);

	public void updateUser(User user, User activeUser) throws IOException;
	
	public void addAuthority(Authority authority);
	
	public void delAuthority(Authority authority);
	
	public int addAuthorities(Collection<? extends GrantedAuthority> authorities);

	public void deleteUserByEmail(String email);

	public void deleteUsers(List<String> users);

	public int totalCount();

	public int count(QueryParam searchCriteria);
	
	public List<User> list(QueryParam searchCriteria);
	
	public void confirmEmail(User user, boolean isMobile) throws Exception;
	
	public void sendConfirmEmailCode(JoinFormBean user) throws Exception;
	
	public StatusCode sendPasswordEmail(String email, String name);
	
	public Response enableUser(String code);
}
