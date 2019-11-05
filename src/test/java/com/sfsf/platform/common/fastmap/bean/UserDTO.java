package com.sfsf.platform.common.fastmap.bean;

import java.util.Date;

public class UserDTO {
    
    private String id;
    
    private String userName;
    
    private String teamName;
    
    private OrgDTO org;
    
    //private Date createDate;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public OrgDTO getOrg() {
        return org;
    }

    public void setOrg(OrgDTO org) {
        this.org = org;
    }

//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }

    public String getUserName() {
        return userName;
    }

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    
}
