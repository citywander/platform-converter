package com.sfsf.platform.common.fastmap.cust;


import com.sfsf.platform.common.fastmap.Mapper;
import com.sfsf.platform.common.fastmap.Mapping;
import com.sfsf.platform.common.fastmap.bean.UserBO;
import com.sfsf.platform.common.fastmap.bean.UserDTO;

@Mapper
public interface UserMapper {
    
    @Mapping(target="org.branch.name", source="org.branchName")
    UserBO toBO(UserDTO userDTO);
    
    @Mapping(target="org.branchName", source="org.branch.name")
    @Mapping(target="teamName", source="team.name")
    UserDTO toDTO(UserBO userBO);

}
