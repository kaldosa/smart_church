package com.laonsys.springmvc.extensions.storage;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.exception.StorageException;
import com.laonsys.springmvc.extensions.model.Attachment;

public interface StorageStrategy {
    void makeDirectory(String path) throws StorageException;

    byte[] getByteArray(Attachment attachment) throws StorageException;

    File getFile(Attachment attachment) throws StorageException;

    Attachment add(String path, MultipartFile file) throws StorageException;
    
    Attachment add(String path, File file) throws StorageException;
    
    Attachment add(String path, String fileName, String contentType, long fileSize, InputStream inputStream) throws StorageException;
    
    List<Attachment> add(String path, List<MultipartFile> files) throws StorageException;
    
    void delete(Attachment attachment) throws StorageException;
    
    void markingDelete(Attachment attachment, boolean delete) throws StorageException;

    void delDirectory(String path);
}
