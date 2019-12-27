package com.laonsys.smartchurch.service.impl;

import static org.springframework.util.Assert.notNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.church.Sermon;
import com.laonsys.smartchurch.mapper.ChurchSermonMapper;
import com.laonsys.smartchurch.mapper.Mapper;
import com.laonsys.smartchurch.mapper.VideoMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchSermonService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.service.impl.AbstractGenericService;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchSermonEntityService")
@Transactional
public class ChurchSermonEntityServiceImpl extends AbstractGenericService<Sermon> implements ChurchSermonService {

    @Autowired
    private ChurchSermonMapper churchSermonMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private AttachService attachService;

    @Autowired
    private ResourceProcessingEngine resourceProcessingEngine;

    @Override
    protected Mapper<Sermon> getMapper() {
        return churchSermonMapper;
    }

    @Override
    public void create(Sermon sermon) {
        notNull(sermon);

        if (sermon.getVideo() == 0) {
            throw new ServiceException("sermon video is empty.");
        }

        super.create(sermon);

        getLog().debug("sermon data has been insert to DB successfully. {}", sermon.getId());

        MultipartFile note = sermon.getNote();
        if (!note.isEmpty()) {
            attachService.insert("/" + sermon.getPath(), note, "church_sermon_attach", sermon.getId());
        }

        MultipartFile mp3 = sermon.getMp3();
        if (!mp3.isEmpty()) {
            attachService.insert("/" + sermon.getPath(), mp3, "church_sermon_attach", sermon.getId());
        }
        
        if (sermon.getVideo() > 0) {
            convertAndInsert(sermon);
        }
    }

    @Override
    public List<Sermon> selectList(QueryParam queryParam) {
        List<Sermon> list = super.selectList(queryParam);
        for (Sermon sermon : list) {
            List<Attachment> attachments = attachService.selectList(sermon.getId(), "church_sermon_attach");
            sermon.setAttachments(attachments);
        }
        return list;
    }

    @Override
    public void update(Sermon sermon) {
        notNull(sermon);

        super.update(sermon);

        MultipartFile note = sermon.getNote();
        MultipartFile mp3 = sermon.getMp3();
        
        if (!note.isEmpty() || !mp3.isEmpty()) {
            List<Attachment> attachments = attachService.selectList(sermon.getId(), "church_sermon_attach");
            Attachment noteAttach = null;
            Attachment mp3Attach = null;
            for (Attachment attachment : attachments) {
                if(attachment.getContentType().startsWith("audio")) {
                    mp3Attach = attachment;
                } else if (!attachment.getContentType().startsWith("video") 
                        && !attachment.getContentType().startsWith("audio")
                        && !attachment.getContentType().startsWith("image")) {
                    noteAttach = attachment;
                }
            }
            
            if(!note.isEmpty()) {
                if(noteAttach != null) {
                    attachService.delete(noteAttach.getId(), "church_sermon_attach");
                }
                attachService.insert("/" + sermon.getPath(), note, "church_sermon_attach", sermon.getId());
            }
            
            if(!mp3.isEmpty()) {
                if(mp3Attach != null) {
                    attachService.delete(mp3Attach.getId(), "church_sermon_attach");
                }
                attachService.insert("/" + sermon.getPath(), mp3, "church_sermon_attach", sermon.getId());
            }
        }
    }

    @Override
    public void delete(int id) {
        attachService.deleteAll(id, "church_sermon_attach");
        super.delete(id);
    }

    @Override
    public Sermon lastOne(int id) {
        Sermon sermon = churchSermonMapper.lastOne(id);
        if(sermon == null) {
            return null;
        }
        List<Attachment> attachments = attachService.selectList(sermon.getId(), "church_sermon_attach");
        sermon.setAttachments(attachments);
        return sermon;
    }

    @Override
    public int storeToTemporaryFile(MultipartFile file) {
        File tempfile = null;

        try {
            tempfile = File.createTempFile("video", ".tmp");
            file.transferTo(tempfile);
        }
        catch (IOException e) {
            throw new ServiceException("Failed to store temporary files.", e);
        }

        Attachment temp = new Attachment();
        temp.setFileName(file.getOriginalFilename());
        temp.setRealFileName(tempfile.getName());
        temp.setContentType(file.getContentType());
        temp.setPath(tempfile.getAbsolutePath());
        temp.setFileSize(file.getSize());

        videoMapper.insert(temp);

        return temp.getId();
    }

    private void convertAndInsert(Sermon sermon) {

        Attachment video = videoMapper.selectOne(sermon.getVideo());

        String fileName = video.getFileName();
        String contentType = video.getContentType();
        long fileSize = video.getFileSize();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(video.getPath()));
        }
        catch (FileNotFoundException e) {
            throw new ServiceException("Failed to convert to flv file.", e);
        }

        @SuppressWarnings("unchecked")
        Map<String, File> result = (Map<String, File>) resourceProcessingEngine.processing(
                fileName, contentType, fileSize, inputStream);

        if (result != null) {
            attachService.insert("/" + sermon.getPath(), result.get("mp4"), "church_sermon_attach", sermon.getId());
            attachService.insert("/" + sermon.getPath(), result.get("flv"), "church_sermon_attach", sermon.getId());
            attachService.insert("/" + sermon.getPath(), result.get("poster"), "church_sermon_attach", sermon.getId());
        }

        videoMapper.delete(video.getId());
    }
}