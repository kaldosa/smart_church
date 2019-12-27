package com.laonsys.springmvc.extensions.storage.kt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.exception.StorageException;
import com.laonsys.springmvc.extensions.exception.UnsupportedException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.storage.RenamePolicy;
import com.laonsys.springmvc.extensions.storage.StorageStrategy;

@Slf4j
@Named("ktStorage")
public class KTCloudStorage implements StorageStrategy {

    @Inject @Named("ktRenamePolicy")
    private RenamePolicy renamePolicy;

    private @Value("#{envvars['storage.container']}") String container;
    private @Value("#{envvars['storage.container.vod']}") String vodcontainer;
    private @Value("#{envvars['storage.host']}") String resourceHost;
    private @Value("#{envvars['storage.host.vod']}") String vodHost;
    
    @Inject
    private KTCloudStorageApis ktCloudStorageApis;

    @Inject
    private ConfigurableMimeFileTypeMap configurableMimeFileTypeMap;
    
    @Override
    public void makeDirectory(String path) {
        ktCloudStorageApis.makeDirectoryObject(container, path);
        ktCloudStorageApis.makeDirectoryObject(vodcontainer, path);
    }

    @Override
    public void delDirectory(String path) {
        ktCloudStorageApis.deleteObject(container, path);
        ktCloudStorageApis.deleteObject(vodcontainer, path);
    }
    
    @Override
    public byte[] getByteArray(Attachment attachment) throws StorageException {
        if (attachment == null) {
            throw new IllegalArgumentException("attahcment must not null.");
        }

        String container = getContainer(attachment.getContentType());

        log.debug("KTCloudStorage [getByteArray] : FileBox(Container) {}", container);

        byte[] rawData = null;
        
        try {
            rawData = ktCloudStorageApis.getObject(container, attachment.getRealFileName());
        } catch (RestClientException e) {
            log.debug("KTCloudStorage [getByteArray] : occurred exception during attachment receive. [{}]", attachment.getFileName());
            throw new StorageException("KTCloudStorage [getByteArray] : occurred exception during attachment receive. [" + attachment.getFileName() + "]", e);
        }

        log.debug("KTCloudStorage [getByteArray] : attachment received successfully. [{}]", attachment.getFileName());
        return rawData;
    }

    @Override
    public File getFile(Attachment attachment) throws StorageException {
        throw new UnsupportedException();
    }

    @Override
    public Attachment add(String path, MultipartFile file) throws StorageException {
        if (file == null || file.isEmpty()) {
            log.debug("KTCloudStorage [add] : file must not null and empty.");
            return null;
        }
        
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("KTCloudStorage [add] : I/O error.");
            }
            throw new StorageException("KTCloudStorage [add] : " + e.getMessage(), e);
        }
        
        return add(path, file.getOriginalFilename(), file.getContentType(), file.getSize(), inputStream);
    }

    @Override
    public Attachment add(String path, File file) throws StorageException {
        if (file == null) {
            log.debug("KTCloudStorage [add] : file must not null.");
            return null;
        }
        
        String contentType = configurableMimeFileTypeMap.getContentType(file);
        
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        }
        catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("KTCloudStorage [add] : I/O error.");
            }
            throw new StorageException("KTCloudStorage [add] : " + e.getMessage(), e);
        }
        
        return add(path, file.getName(), contentType, file.length(), inputStream);
    }
    
    @Override
    public Attachment add(String path, String fileName, String contentType, long fileSize, InputStream inputStream) throws StorageException {
        // FIXME path의 앞뒤 "/" 제거
        if (path == null)
            path = "";
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.matches(".*/$")) {
            path = path.substring(0, path.length() - 1);
        }

        Map<String, String> storageUrl = getStorageUrl(contentType);

        String host = storageUrl.get("host");
        String container = storageUrl.get("container");
        String objectname = path + "/" + fileName;
        
        log.debug("KTCloudStorage [add] : Host [{}]", host);
        log.debug("KTCloudStorage [add] : FileBox(Container) [{}]", container);
        log.debug("KTCloudStorage [add] : Object name [{}]", objectname);

        objectname = renamePolicy.getRename(container, path + "/" + fileName);

        log.debug("KTCloudStorage [add] : Object rename [{}]", objectname);

        try {
            ktCloudStorageApis.putObject(container, objectname, fileName, fileSize, contentType, inputStream);
        } catch(Exception e) {
            throw new StorageException("KTCloudStorage [add] : failed to save file. [" + e.getMessage() + "]", e);
        }
        
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setContentType(contentType);
        attachment.setRealFileName(objectname);
        attachment.setParentPath(path);
        attachment.setPath(host + objectname);
        attachment.setFileSize(fileSize);
        
        return attachment;
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
    public void delete(Attachment attachment) throws StorageException {
        if (attachment == null) {
            throw new IllegalArgumentException("attahcment must not null.");
        }

        String container = getContainer(attachment.getContentType());

        try {
            ktCloudStorageApis.deleteObject(container, attachment.getRealFileName());
        } catch(Exception e) {
            throw new StorageException("KTCloudStorage [delete] : failed to delete file. [" + e.getMessage() + "]", e);
        }
    }
    
    @Override
    public void markingDelete(Attachment attachment, boolean delete) throws StorageException {
        if (attachment == null) {
            throw new IllegalArgumentException("attahcment must not null.");
        }

        String container = getContainer(attachment.getContentType());

        ktCloudStorageApis.markingDelete(container, attachment.getRealFileName(), delete);
    }

    private String getContainer(String contentType) {
        return ("video/x-flv".equals(contentType) || "video/mp4".equals(contentType)) ? vodcontainer : container;
    }

    private Map<String, String> getStorageUrl(String contentType) {
        Map<String, String> storageUrl = new HashMap<String, String>();

        if ("video/x-flv".equals(contentType) || "video/mp4".equals(contentType)) {
            storageUrl.put("container", vodcontainer);
            storageUrl.put("host", vodHost);
        }
        else {
            storageUrl.put("container", container);
            storageUrl.put("host", resourceHost);
        }
        return storageUrl;
    }
}
