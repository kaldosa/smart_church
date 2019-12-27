package com.laonsys.springmvc.extensions.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileUtils {
    public static boolean hasMultipartFile(List<MultipartFile> files) {
        if (files == null || files.isEmpty())
            return false;

        return true;
    }

    public static List<MultipartFile> getMultipartFile(List<MultipartFile> files) {
        if (!hasMultipartFile(files))
            return null;

        List<MultipartFile> result = new ArrayList<MultipartFile>();

        for (MultipartFile file : files) {
            if (file.isEmpty() || file.getSize() == 0)
                continue;

            result.add(file);
        }

        return result;
    }

    public static List<MultipartFile> getMultipartFile(List<MultipartFile> files, String[] contentTypes) {
        if (!hasMultipartFile(files))
            return null;

        List<MultipartFile> result = new ArrayList<MultipartFile>();

        for (MultipartFile file : files) {
            if (file.isEmpty() || file.getSize() == 0)
                continue;

            if (validateContentType(file, contentTypes)) {
                result.add(file);
            }
        }

        return result;
    }

    public static boolean validateContentType(MultipartFile file, String[] contentTypes) {
        String fileContentType = file.getContentType();

        for (String contentType : contentTypes) {
            if (fileContentType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }

        return false;
    }

    public static boolean validateSize(MultipartFile file, long limitSize) {
        return (file.getSize() < limitSize) ? true : false;
    }
}