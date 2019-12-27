package com.laonsys.springmvc.extensions.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.exception.StorageException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.storage.RenamePolicy;
import com.laonsys.springmvc.extensions.storage.StorageStrategy;

@Slf4j
@Named("fileStorage")
public class FileStorage implements StorageStrategy {

    @Inject @Named("fileRenamePolicy")
    private RenamePolicy renamePolicy;

    @Setter
    private String rootPath = System.getProperty("catalina.base") + "/uploads";

    @Override
    public void makeDirectory(String path) {
    }

    @Override
    public void delDirectory(String path) {
    }
    
    @Override
    public byte[] getByteArray(Attachment attachment) throws StorageException {
        byte[] data = null;
        try {
            File file = getFile(attachment);
            data = FileUtils.readFileToByteArray(file);
        }
        catch (IOException e) {
            throw new StorageException("I/O error: " + e.getMessage(), e);
        }
        return data;
    }

    @Override
    public File getFile(Attachment attachment) throws StorageException {
        if (attachment == null) {
            log.debug("{}", "attahcment must not null.");
            throw new IllegalArgumentException("attahcment must not null.");
        }

        File file = new File(attachment.getPath());

        if (!file.exists()) {
            log.debug("file \"{}\" not found.", attachment.getFileName());
            throw new StorageException("file \"" + attachment.getFileName() + "\" not found.");
        }
        return file;
    }

    @Override
    public Attachment add(String path, MultipartFile file) throws StorageException {
        if(file == null || file.isEmpty()) return null;
        
        // FIXME path의 시작이 "/" 로 시작하지 않을 경우, "/" 추가
        if(path == null) path = "";
        if(!path.startsWith("/")) {
            path += "/";
        }
        
        File renamedFile = renamePolicy.getRenameFile(rootPath + path, file.getOriginalFilename());

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), renamedFile);
        }
        catch (IOException e) {
            throw new StorageException("I/O error: " + e.getMessage(), e);
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setRealFileName(file.getName());
        attachment.setParentPath(path);
        attachment.setPath(renamedFile.getAbsolutePath());
        attachment.setFileSize(renamedFile.length());

        return attachment;
    }

    @Override
    public Attachment add(String path, File file) throws StorageException {
        return null;
    }
    
    @Override
    public Attachment add(String path, String fileName, String contentType, long fileSize, InputStream inputStream)
            throws StorageException {
        return null;
    }
    
    @Override
    public List<Attachment> add(String path, List<MultipartFile> files) throws StorageException {
        boolean hasContents = (files == null || files.isEmpty()) ? false : true;

        List<Attachment> attachments = new ArrayList<Attachment>();

        if (hasContents) {
            for (MultipartFile file : files) {

                if (file.isEmpty())
                    continue;

                Attachment attachment = add(path, file);
                attachments.add(attachment);
            }
        }

        return attachments;
    }

    @Override
    public void markingDelete(Attachment attachment, boolean delete) throws StorageException {
        File file = getFile(attachment);
        if(!FileUtils.deleteQuietly(file)) {
            throw new StorageException(file.getName() + " delete error");
        }        
    }
    
    @Override
    public void delete(Attachment attachment) throws StorageException {
        File file = getFile(attachment);
        if(!FileUtils.deleteQuietly(file)) {
            throw new StorageException(file.getName() + " delete error");
        }
    }

}
