package com.laonsys.smartchurch.service;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;

public interface ChurchOrgPostsService {
    public void addComment(BaseComments comments);
    public void deleteComment(int id, User user);
    public void deleteAll(int orgId);
}
