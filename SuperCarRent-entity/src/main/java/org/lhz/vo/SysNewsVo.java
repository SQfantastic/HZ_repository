package org.lhz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lhz.entity.SysNews;
import org.lhz.entity.SysUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysNewsVo extends SysNews {
    //设置分页参数
    private Integer page;
    private Integer limit;


    //接收多个id
    private Integer[] ids;

    //事件范围查询的字段
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
