package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lhz.dao.StatOpernameYearGradeMapper;
import org.lhz.entity.StatEntity;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.List;

public class StatOpernameYearGradeMapperImpl implements StatOpernameYearGradeMapper {
    /**
     * Infor: 查询所有的员工年度业绩
     * @param year
     * @return : java.util.List<org.lhz.entity.StatEntity>
     * @author : LHZ
     * @date : 2021/11/3 0:30
     */
    @Override
    public List<StatEntity> queryAllOpernameGrade(String year) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="select opername as name,sum(price) as value from bus_rent" +
                    " where DATE_FORMAT(createtime,\"%Y\")=? group by opername";
            return runner.query(sql ,new BeanListHandler<StatEntity>(StatEntity.class),year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
