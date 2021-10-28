package org.lhz.vo;

import lombok.Data;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysUser;

import java.util.List;

@Data
public class SysMenuVo extends SysMenu {
    //设置分页参数
    private Integer page;
    private Integer limit;

}
