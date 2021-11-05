package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lhz.dao.StatCustomerAreaMapper;
import org.lhz.entity.StatEntity;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.List;

public class StatCustomerAreaMapperImpl implements StatCustomerAreaMapper {
    @Override
    public List<StatEntity> queryCustomerAreaStatic() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select address as name ,count(*) as value from bus_customer group by address ";
            return runner.query(sql ,new BeanListHandler<StatEntity>(StatEntity.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
