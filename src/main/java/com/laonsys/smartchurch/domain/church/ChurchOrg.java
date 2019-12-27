package com.laonsys.smartchurch.domain.church;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@JsonSerialize(using = ChurchOrgSerializer.class)
@ToString(callSuper = true, exclude = {"intro", "manager", "sort", "upload"})
public class ChurchOrg extends AbstractEntity<ChurchOrg> {

    @Setter
    @Getter
    private int churchId;

    @Setter
    @Getter
    private String path;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 20, groups = { Create.class, Update.class })
    @Setter
    @Getter
    private String name;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 200, groups = { Create.class, Update.class })
    @Setter
    @Getter
    private String intro;

    @NotEmpty(groups = { Create.class, Update.class })
    @Size(max = 15, groups = { Create.class, Update.class })
    @Setter
    @Getter
    private String manager;

    @Setter
    @Getter
    private int sort;

    @Setter
    @Getter
    private Attachment attachment;

    @File(contentType = { "image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png",
            "image/x-citrix-jpeg", "image/pjpeg" }, limit = 1048576, groups = { Create.class, Update.class })
    @Setter
    @Getter
    private MultipartFile upload;
}
