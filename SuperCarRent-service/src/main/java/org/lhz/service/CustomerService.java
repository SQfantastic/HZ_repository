package org.lhz.service;

import org.lhz.entity.BunsBusCustomer;
import org.lhz.vo.BunsBusCustomerVo;

import java.util.List;

public interface CustomerService {

    int  addCustomer(BunsBusCustomer customer);
    List<BunsBusCustomer> findAllCustomerList(BunsBusCustomerVo customerVo);

    int updateCustomer(BunsBusCustomer customer);

    int deleteCustomerById(Integer identity);

    Long getTotal();
}
