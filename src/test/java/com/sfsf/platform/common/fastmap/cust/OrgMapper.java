package com.sfsf.platform.common.fastmap.cust;


import com.sfsf.platform.common.fastmap.Mapper;
import com.sfsf.platform.common.fastmap.Mapping;
import com.sfsf.platform.common.fastmap.bean.OrgBO;
import com.sfsf.platform.common.fastmap.bean.OrgDTO;

@Mapper
public interface OrgMapper {
    
    @Mapping(target="name", source="orgName")
    public OrgBO toBO(OrgDTO org);

    @Mapping(target="name", source="orgName")
    public OrgDTO toDTO(OrgBO org);
}
