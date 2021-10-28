package org.lhz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//菜单实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {
    private Integer id;

    private Integer pid;

    private String title;

    private String href;

    private Integer spread;

    private String target;

    private String icon;

    private Integer available;

}
