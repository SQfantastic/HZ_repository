package org.lhz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysNews {
    private Integer id;

    private String title;

    private String content;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String opername;

}
