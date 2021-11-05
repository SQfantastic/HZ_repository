package org.lhz.dao;

import org.lhz.entity.BunsBusCustomer;
import org.lhz.vo.BunsBusCustomerVo;

import java.util.List;

public interface CustomerMapper {
    List<BunsBusCustomer> findAllCustomerList(BunsBusCustomerVo customerVo);

    int addCustomer(BunsBusCustomer customer);

    int updateCustomer(BunsBusCustomer customer);

    int deleteCustomerById(Integer identity);

    Long getTotal();

}
