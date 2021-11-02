package org.lhz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogInfo{
    private Integer id;

    private String loginname;

    private String loginip;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date logintime;

}
