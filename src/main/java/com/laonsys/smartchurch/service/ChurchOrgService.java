package com.laonsys.smartchurch.service;

import java.util.List;

import com.laonsys.smartchurch.domain.church.ChurchOrg;
import com.laonsys.springmvc.extensions.service.GenericService;

public interface ChurchOrgService extends GenericService<ChurchOrg>{
    void update(List<ChurchOrg> churchOrgs);
}
