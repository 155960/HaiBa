package com.zengqiang.future.pojo;

public class RolePer {
    private Integer id;

    private Integer roleId;

    private Integer perId;

    public RolePer(Integer id, Integer roleId, Integer perId) {
        this.id = id;
        this.roleId = roleId;
        this.perId = perId;
    }

    public RolePer() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }
}