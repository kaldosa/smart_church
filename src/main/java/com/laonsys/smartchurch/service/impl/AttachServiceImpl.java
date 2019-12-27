package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.mapper.AttachmentMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.storage.StorageStrategy;

@Service("attachService")
@Transactional
public class AttachServiceImpl implements AttachService {

    protected transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AttachmentMapper attachmentMapper;

    @Inject
    @Named("ktStorage")
    StorageStrategy storageStrategy;

    @Inject
    private ConfigurableMimeFileTypeMap configurableMimeFileTypeMap;
    
    @Override
    public void makeDirectory(String path) {
        storageStrategy.makeDirectory(path);
    }

    @Override
    public void delDirectory(String path) {
        storageStrategy.delDirectory(path);
    }

    @Override
    public Attachment selectOne(int id) {
        log.debug("{}", "attahcment ID : " + id);

        Attachment attachment = attachmentMapper.selectOne(id);

        return attachment;
    }

    @Override
    public List<Attachment> selectList(int refId, String refTable) {
        log.debug("{}", "ref id : " + refId);
        log.debug("{}", "ref table : " + refTable);

        return attachmentMapper.selectList(refId, refTable, 0, 0);
    }

    @Override
    public Attachment insert(String path, MultipartFile file, String refTable, int refId) throws ServiceException {
        if (path == null || file == null) {
            throw new IllegalArgumentException("parameter must not null.");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("AttachService [insert] : I/O error.");
            }
            throw new ServiceException("AttachService [insert] : " + e.getMessage(), e);
        }
        
        return saveAndInsert(path, file.getOriginalFilename(), file.getContentType(), file.getSize(), inputStream, refTable, refId);
    }

    @Override
    public Attachment insert(String path, File file, String refTable, int refId) throws ServiceException {
        if (path == null || file == null) {
            throw new IllegalArgumentException("parameter must not null.");
        }

        String contentType = configurableMimeFileTypeMap.getContentType(file);
        
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("AttachService [insert] : I/O error.");
            }
            throw new ServiceException("AttachService [insert] : " + e.getMessage(), e);
        }
        
        return saveAndInsert(path, file.getName(), contentType, file.length(), inputStream, refTable, refId);
    }

    private Attachment saveAndInsert(String path, String fileName, String contentType, long fileSize,
            InputStream inputStream, String refTable, int refId) {
        
        Attachment attachment = storageStrategy.add(path, fileName, contentType, fileSize, inputStream);

        if (attachment != null) {
            try {
                attachmentMapper.insert(attachment);
                if (refTable != null && !"".equals(refTable) && refId > 0) {
                    attachmentMapper.insertRefTable(refTable, attachment.getId(), refId);
                }
            }
            catch (Exception e) {
                log.debug("failed to insert DB. path [{}]", path);
                log.debug("failed to insert DB. ref table [{}]", refTable);
                log.debug("failed to insert DB. ref id [{}]", refId);
                log.debug("failed to insert DB. file name [{}]", fileName);
                storageStrategy.delete(attachment);
                throw new ServiceException("attachment insert error", e);
            }
        }

        return attachment;
    }

    @Override
    public void delete(int id, String refTable) throws ServiceException {
        Attachment file = attachmentMapper.selectOne(id);

        if (file == null) {
            log.debug("not found attachment. {} [{}]", refTable, id);
            return;
        }

        attachmentMapper.delete(file.getId());
        if (refTable != null && !"".equals(refTable)) {
            attachmentMapper.deleteRefTable(refTable, file.getId());
        }
        try {
            storageStrategy.delete(file);
        }
        catch (Exception e) {
            log.debug("failed to delete attachment. {} [{}]", refTable, id);
            log.debug("failed to delete attachment. \"{}\" \"{}\"", file.getFileName(), file.getRealFileName());
            throw new ServiceException("attachment delete error", e);
        }
    }

    @Override
    public void deleteAll(int refId, String refTable) throws ServiceException {
        if (refTable == null || "".equals(refTable) || refId == 0) {
            log.debug("reference table must not null. {} , {}", refTable, refId);
            throw new IllegalArgumentException("reference table must not null.");
        }

        List<Attachment> list = attachmentMapper.selectList(refId, refTable, 0, 0);

        if (list == null || list.isEmpty()) {
            log.debug("attachment list is empty. {} [{}]", refTable, refId);
            return;
        }

        attachmentMapper.deleteAll(refId, refTable);
        attachmentMapper.deleteRefAll(refId, refTable);

        for (Attachment file : list) {
            try {
                storageStrategy.delete(file);
            }
            catch (Exception e) {
                log.debug("failed to delete attachment. {} [{}]", refTable, refId);
                log.debug("failed to delete attachment. \"{}\" \"{}\"", file.getFileName(), file.getRealFileName());
                throw new ServiceException("attachment delete error", e);
            }
        }
    }

    @Override
    public List<Attachment> selectList(String churchPath) {
        return attachmentMapper.selectAllByParentPath(churchPath);
    }

    @Override
    public void deleteAll(String parantPath) throws ServiceException {
        List<Attachment> list = attachmentMapper.selectAllByParentPath(parantPath);

        attachmentMapper.deleteAllByParentPath(parantPath);

        for (Attachment file : list) {
            try {
                storageStrategy.delete(file);
            }
            catch (Exception e) {
                log.debug("failed to delete attachment. [{}]", parantPath);
                log.debug("failed to delete attachment. \"{}\" \"{}\"", file.getFileName(), file.getRealFileName());
                throw new ServiceException("attachment delete error", e);
            }
        }
    }

    @Override
    public ResponseEntity<byte[]> retriveAttach(int id) throws ServiceException {
        log.debug("{}", "attahcment ID : " + id);

        Attachment attachment = attachmentMapper.selectOne(id);

        if (attachment == null) {
            log.debug("{}", "attahcment {" + id + "} not found.");
            throw new IllegalArgumentException("attahcment {" + id + "} not found.");
        }

        byte[] rawData = storageStrategy.getByteArray(attachment);

        return retrive(attachment.getFileName(), rawData, attachment.getContentType());
    }

    @Override
    public void retriveAttach(int id, HttpServletResponse response) throws ServiceException {
        log.debug("{}", "attahcment ID : " + id);

        Attachment attachment = attachmentMapper.selectOne(id);

        if (attachment == null) {
            log.debug("{}", "attahcment {" + id + "} not found.");
            throw new IllegalArgumentException("attahcment {" + id + "} not found.");
        }

        byte[] rawData = storageStrategy.getByteArray(attachment);
        retrive(rawData, attachment.getContentType(), response);
    }

    @Override
    public Long totalAttachSize(String path, boolean type) {
        return attachmentMapper.totalAttachSize(path, type);
    }

    @Override
    public ResponseEntity<byte[]> retrive(String filename, byte[] rawData, String contentType) throws ServiceException {

        HttpHeaders headers = new HttpHeaders();
        MediaType textType = new MediaType("text");
        MediaType mediaType = MediaType.parseMediaType(contentType);
        if (textType.includes(mediaType)) {
            mediaType = new MediaType(mediaType.getType(), mediaType.getSubtype(), Charset.forName("utf8"));
        }
        headers.setContentType(mediaType);
        headers.setContentLength(rawData.length);

        // String name = null;
        //
        // try {
        // name = new String(filename.getBytes("utf8"), "ISO8859-1");
        // }
        // catch (UnsupportedEncodingException e) {
        // throw new ServiceException("retrive failed.", e);
        // }

        // headers.add("Content-Disposition", "attachment;filename=\"" + name +
        // "\"");

        // FIXME 첨부파일명 한글 깨짐 문제
        try {
            headers.add("Content-Disposition", "attachment;filename*=UTF-8\'\'" + URLEncoder.encode(filename, "utf8"));
        }
        catch (UnsupportedEncodingException e) {
        }

        return new ResponseEntity<byte[]>(rawData, headers, HttpStatus.OK);
    }

    private void retrive(byte[] rawData, String contentType, HttpServletResponse response) throws ServiceException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(contentType);
        response.setContentLength(rawData.length);

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(rawData);
            outputStream.flush();
        }
        catch (IOException e) {
            throw new ServiceException("retrive failed.");
        }
        finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            catch (IOException e) {
                // ignore
            }
        }

    }
}
