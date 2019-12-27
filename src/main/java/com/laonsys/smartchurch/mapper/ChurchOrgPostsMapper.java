package com.laonsys.smartchurch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.church.OrgPosts;

public interface ChurchOrgPostsMapper extends Mapper<OrgPosts>{
    public List<Integer> selectPostsWithComments(int orgId);
    public void deleteAll(int orgId);
    
    public void addComment(BaseComments comments);
    public void addCommentRefTable(BaseComments comments);
    public void deleteComment(int id);
    public void deleteComments(@Param("comments") List<BaseComments> comments);
    public void deleteCommentRefTable(int id);
}
