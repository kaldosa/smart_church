package com.laonsys.smartchurch.service;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.model.Attachment;

public interface ChurchAlbumService {
    public void addComment(BaseComments comments);

    public void deleteComment(int id, User user);

    public int getAttachCount(int id);

    public Attachment insertAttach(String url, int refId, MultipartFile file);

    public void deleteAttach(int id, int attachId);
}
