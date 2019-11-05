package com.sfsf.platform.common.fastmap.bean;

public class UserBO {
    
    private String id;
    
    private String name;
    
    private OrgBO org;
    
    private TeamBO team;
    
    //private String createDate;
    
    public OrgBO getOrg() {
        return org;
    }

    public void setOrg(OrgBO org) {
        this.org = org;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }

	public TeamBO getTeam() {
		return team;
	}

	public void setTeam(TeamBO team) {
		this.team = team;
	}
    
    
}
