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
import com.laonsys.smartchurch.domain.church.OrgPosts;
import com.laonsys.smartchurch.mapper.ChurchOrgPostsMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchOrgPostsService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.service.UpdateHitsService;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchOrgPostsEntityService")
@Transactional
public class ChurchOrgPostsEntityServiceImpl extends AbstractGenericService<OrgPosts> implements ChurchOrgPostsService, UpdateHitsService<OrgPosts> {

    @Autowired private ChurchOrgPostsMapper churchOrgPostsMapper;
    @Autowired private AttachService attachService;
    @Autowired private ReplyService replyService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    protected Mapper<OrgPosts> getMapper() {
        return churchOrgPostsMapper;
    }

    @Override
    public void create(OrgPosts orgPosts) {
        super.create(orgPosts);
        MultipartFile file = orgPosts.getUpload();
        if(file != null && !file.isEmpty()) {
            saveAttach(orgPosts.getId(), orgPosts.getPath(), file, "church_org_posts_attach");
        }
    }
    
    @Override
    public void update(OrgPosts orgPosts) {
        super.update(orgPosts);

        MultipartFile file = orgPosts.getUpload();

        if (file != null && !file.isEmpty()) {
            if(orgPosts.getAttachment() != null) {
                attachService.delete(orgPosts.getAttachment().getId(), "church_org_posts_attach");
            }
            saveAttach(orgPosts.getId(), orgPosts.getPath(), file, "church_org_posts_attach");
        }
    }
    
    @Override
    public void delete(int id) {
        attachService.deleteAll(id, "church_org_posts_attach");
        replyService.deleteAll(id, "church_org_posts_comments");
        super.delete(id);
    }
    
    @Override
    public void deleteAll(int orgId) {
        List<Integer> postsIdwithComments = churchOrgPostsMapper.selectPostsWithComments(orgId);
        
        for(Integer postsId : postsIdwithComments) {
            attachService.deleteAll(postsId, "church_org_posts_attach");
            replyService.deleteAll(postsId, "church_org_posts_comments");
        }
        churchOrgPostsMapper.deleteAll(orgId);
    }
    
    @Override
    public void addComment(BaseComments reply) {
        replyService.insert(reply, "church_org_posts_comments");
    }
    
    @Override
    public void deleteComment(int id, User user) {
        replyService.delete(id, "church_org_posts_comments", user);
    }

    @Override
    public void updateHits(int hits, int id) {
        churchOrgPostsMapper.updateHits(hits, id);
    }

    @Override
    public void updateHits(OrgPosts model) {
        churchOrgPostsMapper.updateHits(model);        
    }
    
    private Attachment saveAttach(int refId, String path, MultipartFile file, String tableName) {
        Attachment attachment = null;
        try {
            File output = (File) resourceProcessingEngine.processing(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    file.getInputStream());
            
            if(output != null) {
                attachment = attachService.insert("/" + path, output, tableName, refId);
            }
        }
        catch (IOException e) {
            throw new ServiceException("failed to save image in [" + tableName + "].");
        }
        return attachment;
    }
}
