package org.lhz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lhz.entity.SysUser;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo extends SysUser {
    //设置分页参数
    private Integer page;
    private Integer limit;

    // 用于保存用户角色id
    private List<Integer> ids;
}
