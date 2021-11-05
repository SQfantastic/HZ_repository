package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.StatCompanyYearGradeMapper;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatCompanyYearGradeMapperImpl implements StatCompanyYearGradeMapper {
    /**
     * Infor: 根据年份查询该年份中每个月的业绩
     * @param year
     * @return : java.util.List<java.lang.Double>
     * @author : LHZ
     * @date : 2021/11/3 11:23
     */
    @Override
    public List<Double> queryCompanyYearGradeStaticList(String year) {
        ArrayList<Double> list = new ArrayList<>();
        try {
            QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
                String sql1 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'01')";
                Double sumPrice1 = runner.query(sql1, new ScalarHandler<>(), year);
                list.add(sumPrice1);

                String sql2 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'02')";
                Double sumPrice2 = runner.query(sql2, new ScalarHandler<>(), year);
                list.add(sumPrice2);

                String sql3 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'03')";
                Double sumPrice3 = runner.query(sql3, new ScalarHandler<>(), year);
                list.add(sumPrice3);

                String sql4 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'04')";
                Double sumPrice4 = runner.query(sql4, new ScalarHandler<>(), year);
                list.add(sumPrice4);

                String sql5 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'05')";
                Double sumPrice5 = runner.query(sql5, new ScalarHandler<>(), year);
                list.add(sumPrice5);
                String sql6 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'06')";
                Double sumPrice6= runner.query(sql6, new ScalarHandler<>(), year);
                list.add(sumPrice6);

                String sql7 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'07')";
                Double sumPrice7 = runner.query(sql7, new ScalarHandler<>(), year);
                list.add(sumPrice7);

                String sql8 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'08')";
                Double sumPrice8 = runner.query(sql8, new ScalarHandler<>(), year);
                list.add(sumPrice8);

                String sql9 =" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,'09')";
                Double sumPrice9 = runner.query(sql9, new ScalarHandler<>(), year);
                list.add(sumPrice9);
            for (int i = 10; i <13; i++) {
                String sqls=" select sum(price) from bus_rent where " +
                        "DATE_FORMAT(createtime,\"%Y%m\")=concat(?,"+i+")";
                Double sumPrices = runner.query(sqls, new ScalarHandler<>(), year);
                list.add(sumPrices);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
