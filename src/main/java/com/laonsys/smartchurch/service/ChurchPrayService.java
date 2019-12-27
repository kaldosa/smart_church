package com.laonsys.smartchurch.service;

import com.laonsys.smartchurch.domain.BaseComments;

public interface ChurchPrayService {
    public void addComment(BaseComments comments);
    public void deleteComment(int id);
}
