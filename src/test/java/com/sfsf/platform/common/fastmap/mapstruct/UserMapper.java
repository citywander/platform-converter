package com.sfsf.platform.common.fastmap.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sfsf.platform.common.fastmap.bean.UserBO;
import com.sfsf.platform.common.fastmap.bean.UserDTO;

@Mapper(uses= OrgMapper.class)
public interface UserMapper {
    
    @Mapping(target="org.branch.name", source="org.branchName")
    UserBO toBO(UserDTO userDTO);
    
    @Mapping(target="org.branchName", source="org.branch.name")
    UserDTO toDTO(UserBO userDTO);

}
