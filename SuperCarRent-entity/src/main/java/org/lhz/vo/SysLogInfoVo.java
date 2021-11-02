package org.lhz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lhz.entity.SysLogInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogInfoVo extends SysLogInfo {
    //对应layui的请求形式：?page=1&limit=30（该参数可通过 request 自定义）
    //page 代表当前页码、limit 代表每页数据量
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
