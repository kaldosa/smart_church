package com.laonsys.smartchurch.domain.church;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.smartchurch.domain.BaseComments;
import com.laonsys.smartchurch.domain.Postings;
import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@JsonSerialize(using = OrgPostsSerializer.class)
@ToString(callSuper = true, includeFieldNames = true, exclude = { "upload", "comments" })
@EqualsAndHashCode(callSuper = false)
public @Data
class OrgPosts extends Postings {

    private String path;

    private int orgId;

    private List<BaseComments> comments;

    @File(contentType = { "image/jpeg", "image/png", "image/gif", "image/x-png", "image/x-citrix-png",
            "image/x-citrix-jpeg", "image/pjpeg" }, limit = 10485760, groups = { Create.class, Update.class })
    // 10Mb
    private MultipartFile upload;

    private Attachment attachment;
}