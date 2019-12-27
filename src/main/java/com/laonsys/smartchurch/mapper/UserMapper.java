package com.laonsys.smartchurch.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;

import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.VerifyEmail;
import com.laonsys.springmvc.extensions.model.QueryParam;

public interface UserMapper {
    public User findById(Integer userId);

    public User findByEmail(@Param("email") String email, @Param("name") String name, @Param("password") String password);

    public User selectUser(User user);
    
    public int addUser(User user);

    public void addAuthority(Authority authority);

    public void delAuthority(Authority authority);
    
    public int addAuthorities(@Param("authorities") Collection<? extends GrantedAuthority> authorities);

    public void update(User user);

    public int deleteUserByEmail(String email);

    public int totalCount();

    public int count(QueryParam searchCriteria);
    
    public List<User> list(QueryParam searchCriteria);
    
    public void insertConfirmData(VerifyEmail confirmData);
    public VerifyEmail selectConfirmData(String code);
    public VerifyEmail selectConfirmDataByEmail(String email);
    public void deleteConfirmData(int id);

}
