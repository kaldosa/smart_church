package com.laonsys.smartchurch.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;

public interface AttachService {
    Attachment selectOne(int id);
    List<Attachment> selectList(int refId, String refTable);
    List<Attachment> selectList(String churchPath);
    
    void makeDirectory(String path);
    void delDirectory(String path);
    
    Attachment insert(String path, MultipartFile file, String refTable, int refId) throws ServiceException;
    Attachment insert(String path, File file, String refTable, int refId) throws ServiceException;
    
    void delete(int id, String refTable) throws ServiceException;
    void deleteAll(int refId, String refTable) throws ServiceException;
    void deleteAll(String churchPath) throws ServiceException;
    
    ResponseEntity<byte[]> retriveAttach(int id) throws ServiceException;
    ResponseEntity<byte[]> retrive(String filename, byte[] rawData, String contentType) throws ServiceException;
    void retriveAttach(int id, HttpServletResponse response) throws ServiceException;
    
    Long totalAttachSize(String path, boolean type);
}