package com.laonsys.springmvc.extensions.validation.support;

import java.util.Iterator;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractFileValidator {
    /**
     * 빈 MultipartFile은 목록에서 제거
     * 
     * @param files
     *        MultipartFile 목록
     */
    public void trim(List<MultipartFile> files) {
        if(files == null) return;
        
        for (Iterator<MultipartFile> it = files.iterator(); it.hasNext();) {
            MultipartFile file = it.next();
            if (file.isEmpty() || file.getSize() == 0) {
                it.remove();
            }
        }
    }

    /**
     * 첨부파일 content type 검사
     * 
     * @param file
     *        MultipartFile
     * @param contentTypes
     *        체크할 content type(media type)
     * @return 허용되는 content type 일 경우 true, 아니면 false
     */
    public boolean validateContentType(MultipartFile file, String[] contentTypes) {
        if(contentTypes.length == 0) return true;
        
        String fileContentType = file.getContentType();

        for (String contentType : contentTypes) {
            if (fileContentType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 첨부파일 사이즈에 대한 검사
     * 
     * @param file
     *        첨부파일
     * @param limitSize
     *        제한 파일 사이즈
     * @return 제한 사이즈보다 작을 경우 true, 아니면 false
     */
    public boolean validateSize(MultipartFile file, long limitSize) {
        return (file.getSize() < limitSize) ? true : false;
    }
}
