package org.lhz.service.impl;

import org.lhz.dao.StatCustomerAreaMapper;
import org.lhz.dao.impl.StatCustomerAreaMapperImpl;
import org.lhz.entity.StatEntity;
import org.lhz.service.StatCustomerAreaService;

import java.util.List;

public class StatCustomerAreaServiceImpl implements StatCustomerAreaService {
    //注入dao层对象
    private StatCustomerAreaMapper statCustomerAreaMapper = new StatCustomerAreaMapperImpl();
    @Override
    public List<StatEntity> queryCustomerAreaStatic() {
        return statCustomerAreaMapper.queryCustomerAreaStatic();
    }
}
