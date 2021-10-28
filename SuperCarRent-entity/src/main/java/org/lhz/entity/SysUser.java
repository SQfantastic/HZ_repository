package org.lhz.entity;

import lombok.Data;

@Data
public class SysUser {

    private Integer userid;

    private String loginname;

    private String identity;

    private String realname;

    private Integer sex;

    private String address;

    private String phone;

    private String pwd;

    private String position;

    private Integer type;

    private Integer available;

    public SysUser() {
    }

    public SysUser(String loginname, String pwd) {
        this.loginname = loginname;
        this.pwd = pwd;
    }
}
