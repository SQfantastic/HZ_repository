package org.lhz.vo;

import lombok.Data;
import org.lhz.entity.BunsBusRent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BunsBusRentVo extends BunsBusRent {
    //设置分页参数
    private Integer page;
    private Integer limit;

    //事件范围查询的字段
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
