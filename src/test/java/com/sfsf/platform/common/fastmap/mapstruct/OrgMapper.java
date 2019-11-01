package com.sfsf.platform.common.fastmap.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sfsf.platform.common.fastmap.bean.OrgBO;
import com.sfsf.platform.common.fastmap.bean.OrgDTO;

@Mapper
public interface OrgMapper {
    
    @Mapping(target="name", source="orgName")
    public OrgBO toBO(OrgDTO org);

    @Mapping(target="orgName", source="name")
    public OrgDTO toBO(OrgBO org);
}
