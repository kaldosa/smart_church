package com.laonsys.smartchurch.domain.church;

import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.User;
import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Attach;

public class OurChurch extends AbstractEntity<OurChurch> {
    
    private ChurchMeta churchMeta;
    
    private User applicant;
    
    private String path;

    private boolean enabled;
    
    private ServiceStatus status;
    
    private Attachment logo;
    
    @File(contentType = {"image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png", "image/x-citrix-jpeg", "image/pjpeg"}, limit = 1048576, groups = {Attach.class})
    private MultipartFile file;

    public ChurchMeta getChurchMeta() {
        return churchMeta;
    }
    
    public void setChurchMeta(ChurchMeta churchMeta) {
        this.churchMeta = churchMeta;
    }
    
    public User getApplicant() {
        return applicant;
    }
    
    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Attachment getLogo() {
        return logo;
    }

    public void setLogo(Attachment logo) {
        this.logo = logo;
    }
    
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ServiceStatus getStatus() {
        return status;
    }
    
    public void setStatus(ServiceStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        String toString = "OURCHURCH ID: "
                            + getId()
                            + ", PATH: "
                            + path
                            + ", status: "
                            + status
                            + ", enabled: "
                            + enabled;
        StringBuffer buffer = new StringBuffer(toString);
        buffer.append("\n");
        buffer.append(churchMeta.toString() + "\n");
        buffer.append(applicant.toString() + "\n");
        return buffer.toString();
    }
}
