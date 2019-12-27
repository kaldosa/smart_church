package com.laonsys.smartchurch.mapper;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.church.Pray;

public interface ChurchPrayMapper extends Mapper<Pray>{
    public void addComment(BaseComments comments);
    public void addCommentRefTable(BaseComments comments);
    public void deleteComment(int id);
    public void deleteCommentRefTable(int id);
}
