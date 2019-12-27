package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.Album;
import com.laonsys.smartchurch.mapper.ChurchAlbumMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchAlbumService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchAlbumEntityService")
@Transactional
public class ChurchAlbumEntityServiceImpl extends AbstractGenericService<Album> implements ChurchAlbumService {

    @Autowired private ChurchAlbumMapper churchAlbumMapper;
    @Autowired private ReplyService replyService;
    @Autowired private AttachService attachService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    protected Mapper<Album> getMapper() {
        return churchAlbumMapper;
    }

    @Override
    public List<Album> selectList(QueryParam queryParam) {
        List<Album> list = super.selectList(queryParam);
        
        for(Album album : list) {
            List<Attachment> attachList = attachService.selectList(album.getId(), "church_album_attach");
            if(attachList != null && !attachList.isEmpty()) {
                album.setThumbnail(attachList.get(0));
            }
        }
        
        return list;
    }
    
    @Override
    public Album selectOne(int id) {
        Album album = super.selectOne(id);
        if(album != null) {
            List<BaseComments> comments = replyService.selectList(id, "church_album_comments");
            album.setComments(comments);
        }
        return album;
    }

    @Override
    public void delete(int id) {
        replyService.deleteAll(id, "church_album_comments");
        attachService.deleteAll(id, "church_album_attach");
        super.delete(id);
    }

    @Override
    public synchronized Attachment insertAttach(String url, int refId, MultipartFile file) {
        Attachment result = null;
        try {
            File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
            
            if(output != null) {
                result = attachService.insert("/" + url, output, "church_album_attach", refId);
            }
        }
        catch (IOException e) {
            throw new ServiceException("failed to save album image.");
        }
        
        if(result == null) {
            throw new ServiceException("failed to save album image.");
        }
        return result;
    }
    
    @Override
    public void deleteAttach(int id, int attachId) {
        attachService.delete(attachId, "church_album_attach");
    }

    @Override
    public void addComment(BaseComments reply) {
        replyService.insert(reply, "church_album_comments");
    }
    
    @Override
    public void deleteComment(int id, User user) {
        replyService.delete(id, "church_album_comments", user);
    }
    
    @Override
    public int getAttachCount(int id) {
        return churchAlbumMapper.getAttachCount(id);
    }
}
