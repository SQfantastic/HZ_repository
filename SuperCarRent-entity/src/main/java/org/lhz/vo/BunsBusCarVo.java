package org.lhz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BunsBusCarVo {
    private Integer page ;
    private Integer limit ;
    private String carnumber;
    private String cartype;
    private  String color;
    private  String description;
    private Integer isrenting;

}
