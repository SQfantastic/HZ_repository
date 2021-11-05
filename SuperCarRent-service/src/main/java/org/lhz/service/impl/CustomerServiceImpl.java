package org.lhz.service.impl;

import org.lhz.dao.CustomerMapper;
import org.lhz.dao.impl.CustomerMapperImpl;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.service.CustomerService;
import org.lhz.vo.BunsBusCustomerVo;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    //注入dao层对象
    private CustomerMapper customerMapper = new CustomerMapperImpl();

    @Override
    public int addCustomer(BunsBusCustomer customer) {
        return customerMapper.addCustomer(customer);
    }

    @Override
    public List<BunsBusCustomer> findAllCustomerList(BunsBusCustomerVo customerVo) {
        return customerMapper.findAllCustomerList(customerVo);
    }

    @Override
    public int updateCustomer(BunsBusCustomer customer) {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public int deleteCustomerById(Integer identity) {
        return customerMapper.deleteCustomerById(identity);
    }

    @Override
    public Long getTotal() {
        return customerMapper.getTotal();
    }
}
