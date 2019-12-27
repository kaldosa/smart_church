package com.laonsys.smartchurch.mapper;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.church.Album;

public interface ChurchAlbumMapper extends Mapper<Album>{
    public int getAttachCount(int id);
    
    public void addComment(BaseComments comments);
    public void addCommentRefTable(BaseComments comments);
    public void deleteComment(int id);
    public void deleteCommentRefTable(int id);
}
