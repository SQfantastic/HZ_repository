package org.lhz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    private Integer roleid;

    private String rolecode;

    private String rolename;

    private String roledesc;

    private Integer available;

}
