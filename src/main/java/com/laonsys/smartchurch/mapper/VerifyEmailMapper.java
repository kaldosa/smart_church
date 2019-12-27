package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.VerifyEmail;

public interface VerifyEmailMapper {
    public void insert(VerifyEmail confirmData);
    public VerifyEmail select(String code);
    public List<VerifyEmail> selectByEmail(String email);
    public void delete(int id);
}
