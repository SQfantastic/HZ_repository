package org.lhz.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysUser;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuVo extends SysMenu {
    //设置分页参数
    private Integer page;
    private Integer limit;

}
