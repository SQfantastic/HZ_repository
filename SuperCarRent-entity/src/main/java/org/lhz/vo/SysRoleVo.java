package org.lhz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lhz.entity.SysRole;
import org.lhz.entity.SysUser;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVo extends SysRole {
    //设置分页参数
    private Integer page;
    private Integer limit;

}
