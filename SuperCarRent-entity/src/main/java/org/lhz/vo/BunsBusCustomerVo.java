package org.lhz.vo;

import lombok.Data;
import org.lhz.entity.BunsBusCustomer;

@Data
public class BunsBusCustomerVo extends BunsBusCustomer {
    //设置分页参数
    private Integer page;
    private Integer limit;
}
