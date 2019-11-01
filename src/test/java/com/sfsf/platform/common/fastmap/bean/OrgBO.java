package com.sfsf.platform.common.fastmap.bean;

public class OrgBO {
    
    private String id;
    
    private String name;
    
    private BranchBO branch;

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

    public BranchBO getBranch() {
        return branch;
    }

    public void setBranch(BranchBO branch) {
        this.branch = branch;
    }
    
}
