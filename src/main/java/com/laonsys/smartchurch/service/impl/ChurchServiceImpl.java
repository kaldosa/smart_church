package com.laonsys.smartchurch.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.PhotoFormBean;
import com.laonsys.smartchurch.domain.Authority;
import com.laonsys.smartchurch.domain.User;
import com.laonsys.smartchurch.domain.church.OurChurch;
import com.laonsys.smartchurch.domain.church.ServiceStatus;
import com.laonsys.smartchurch.mapper.ChurchServiceMapper;
import com.laonsys.smartchurch.service.AttachService;
import com.laonsys.smartchurch.service.ChurchService;
import com.laonsys.smartchurch.service.MailService;
import com.laonsys.smartchurch.service.ReplyService;
import com.laonsys.smartchurch.service.UserService;
import com.laonsys.springmvc.extensions.exception.ServiceException;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.utils.ResourceProcessingEngine;

@Service("churchService")
@Transactional
public class ChurchServiceImpl implements ChurchService {

    protected Logger log = LoggerFactory.getLogger(ChurchServiceImpl.class);
    
    @Autowired ChurchServiceMapper churchServiceMapper;
    @Autowired UserService userService;
    @Autowired MailService mailService;
    @Autowired AttachService attachService;
    @Autowired ReplyService replyService;
    @Autowired private ResourceProcessingEngine resourceProcessingEngine;
    
    @Override
    public int count(QueryParam queryParam) {
        return churchServiceMapper.count(queryParam);
    }

    @Override
    public List<OurChurch> selectList(QueryParam queryParam) {
        List<OurChurch> list = churchServiceMapper.selectList(queryParam);
        int totalCount = count(queryParam);
        queryParam.getPaginate().paging(totalCount);
        return list;
    }

    @Override
    public List<OurChurch> selectRecent() {
        return churchServiceMapper.selectRecent();
    }

    @Override
    public OurChurch selectOne(int id) {
        return churchServiceMapper.selectOne(id);
    }
    
    @Override
    public List<Attachment> selectMainImages(int id) {
        return attachService.selectList(id, "church_main_images");
    }

    @Override
    public void updateLogo(OurChurch ourChurch, PhotoFormBean photoFormBean) {
        Attachment logo = ourChurch.getLogo();
        
        if(logo != null) {
            attachService.delete(logo.getId(), "church_logo");
        }
        saveAttach(ourChurch.getId(), ourChurch.getPath(), photoFormBean.getFile(), "church_logo");
    }
    
    @Override
    public Attachment insertMainImage(String url, int refId, MultipartFile file) {
        return saveAttach(refId, url, file, "church_main_images");
    }
    
    @Override
    public void deleteMainImage(int attachId) {
        attachService.delete(attachId, "church_main_images");
    }
    
    @Override
    public void insert(OurChurch ourChurch) {
        churchServiceMapper.insert(ourChurch);
    }

    @Override
    public void update(OurChurch ourChurch) {
        churchServiceMapper.update(ourChurch);
    }

    @Override
    public void application(OurChurch ourChurch, BindingResult result) {
        if(log.isDebugEnabled()) {
            log.debug("request sign up of our church service. [{}]" , ourChurch);
        }
        
        if(!churchServiceMapper.isAvailableApplicant(ourChurch.getApplicant().getId())) {
            result.reject(null, "이미 서비스를 신청한 사용자입니다.");
            return ;
        }
        
        String path = "";
        
        do {
            path = RandomStringUtils.randomAlphanumeric(16);
        } while(!isAvailablePath(path));
        
        // 우리교회 서비스 등록

        ourChurch.setPath(path);
        ourChurch.setCreatedDate(Calendar.getInstance().getTime());
        ourChurch.setStatus(ServiceStatus.WAITING);
        insert(ourChurch);
        
        if(log.isDebugEnabled()) {
            log.debug("signed up our church service successfully. [{}]" , ourChurch);
        }
    }
    
    @Override
    public void cancelApplication(int id) {
        churchServiceMapper.delete(id);
    }
    
    @Override
    public void approve(OurChurch ourChurch) {
        if(log.isDebugEnabled()) {
            log.debug("request approve of our church service. [{}]" , ourChurch);
        }

        // 신청인 교회관리자 권한 추가
        User applicant = ourChurch.getApplicant();
        String path = ourChurch.getPath();
        String role = "ROLE_" + path;
        userService.addAuthority(new Authority(applicant.getId(), "ROLE_CHURCHADMIN"));
        userService.addAuthority(new Authority(applicant.getId(), role));
        
        // KT Cloud 디렉토리 생성
        attachService.makeDirectory(path);
        
        // 서비스 상태 및 활성화
        ourChurch.setStatus(ServiceStatus.SERVICE);
        ourChurch.setEnabled(true);
        ourChurch.setModifiedDate(Calendar.getInstance().getTime());
        
        update(ourChurch);
        
        mailService.transferApprovalMail(ourChurch);
        
        if(log.isDebugEnabled()) {
            log.debug("approved our church service successfully. [{}]" , ourChurch);
        }
    }
    
    @Override
    public void delete(int id) {
        OurChurch ourChurch = churchServiceMapper.selectOne(id);
        
        String path = ourChurch.getPath();
        
        // 해당 교회 첨부파일 및 코멘트 모두 삭제
        attachService.deleteAll(path);
        replyService.deleteAll(id);
        
        // 교회 관리자 권한 삭제
        User applicant = ourChurch.getApplicant();
        String role = "ROLE_" + path;        
        userService.delAuthority(new Authority(applicant.getId(), "ROLE_CHURCHADMIN"));
        userService.delAuthority(new Authority(applicant.getId(), role));
        
        // KT Cloud 디렉토리 삭제
        attachService.delDirectory(path);
        
        // 우리교회 삭제
        churchServiceMapper.delete(id);
        
        //TODO 서비스 삭제 안내 메일 발송
    }

    @Override
    public boolean isAvailablePath(String path) {
        return churchServiceMapper.isAvailablePath(path);
    }
    
    @Override
    public Long totalAttachSize(String path, boolean type) {
        return attachService.totalAttachSize(path, type);
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
