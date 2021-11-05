package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.entity.BunsBusCar;
import org.lhz.entity.BunsBusChecks;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.dao.BunsCheckCarDao;
import org.lhz.vo.BunsBusChecksVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.*;

public class BunsCheckCarDaoImpl implements BunsCheckCarDao {
    @Override
    public Long getTotal() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sql = " select count(*) from bus_car ";

        try {
            Long count = runner.query(sql, new ScalarHandler<Long>());
            return  count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateCheck(BunsBusChecks bnsBusChecks) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

        String sql="update bus_checks set checkdate=?,checkdesc=?,problem=?,paymoney=?,opername=?where checkid=?";
        try {
            int update = runner.update(sql,bnsBusChecks.getCheckdate(),bnsBusChecks.getCheckdesc(),bnsBusChecks.getProblem(),bnsBusChecks.getPaymoney(),bnsBusChecks.getOpername(),bnsBusChecks.getCheckid());
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BunsBusRent queryRentByRentId(String rentid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sql="select * from bus_rent where rentid like ?";
        try {
            BunsBusRent query = runner.query(sql, new BeanHandler<BunsBusRent>(BunsBusRent.class), rentid);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> initCheckFormData(String rentid) {
        String carnumber = null;
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sqlrent="select * from bus_rent where rentid like ?";
        String sqlcustomer="select * from bus_customer where identity like ?";
        String sqlcar="select * from bus_car where carnumber like ?";
        String sqlcheck="select * from bus_checks where rentid like ?";
        try {
            BunsBusRent rent = runner.query(sqlrent, new BeanHandler<BunsBusRent>(BunsBusRent.class),"%"+ rentid+"%");
            BunsBusCustomer customer = runner.query(sqlcustomer, new BeanHandler<BunsBusCustomer>(BunsBusCustomer.class), "%"+rent.getIdentity()+"%");
            BunsBusCar car = runner.query(sqlcar, new BeanHandler<BunsBusCar>(BunsBusCar.class),"%"+rent.getCarnumber()+"%");
            System.out.println(car.toString());
            BunsBusChecks checks = runner.query(sqlcheck, new BeanHandler<BunsBusChecks>(BunsBusChecks.class), "%"+rentid+"%");

            Map<String, Object> map = new HashMap<>();
            map.put("rent",rent);
            map.put("customer",customer);
            map.put("car",car);
            map.put("check",checks);
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BunsBusChecks> lodaAll(BunsBusChecksVo bnsBusChecksvo) {
        Integer page = bnsBusChecksvo.getPage();
        Integer limit = bnsBusChecksvo.getLimit();
        String checkid = bnsBusChecksvo.getCheckid();
        String rentid = bnsBusChecksvo.getRentid();
        String opername = bnsBusChecksvo.getOpername();
        Date startTime = bnsBusChecksvo.getStartTime();
        Date endTime = bnsBusChecksvo.getEndTime();
        String checkdesc = bnsBusChecksvo.getCheckdesc();
        System.out.println(startTime);
        System.out.println(endTime);
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> checkList = new ArrayList<>();

        try {
            String sql="select *from bus_checks where 1=1";
            if(checkid!=null&&!"".equals(checkid)){
                sql+=" and checkid like ?";
                checkList.add("%"+checkid+"%");
            }
            if(rentid!=null&&!"".equals(rentid)){
                sql+=" and rentid like ?";
                checkList.add("%"+rentid+"%");
            }
            if(opername!=null&&!"".equals(opername)){
                sql+=" and opername like ?";
                checkList.add("%"+opername+"%");
            }
            if(checkdesc!=null&&!"".equals(checkdesc)){
                sql+=" and checkdesc like ?";
                checkList.add("%"+checkdesc+"%");
            }
            if(startTime!=null&&!"".equals(startTime)){
                sql+=" and checkdate >= ?";
                checkList.add(startTime);
            }
            if(endTime!=null&&!"".equals(endTime)){
                sql+=" and checkdate <= ?";
                checkList.add(endTime);
            }
            List<BunsBusChecks> checks = runner.query(sql, new BeanListHandler<>(BunsBusChecks.class),checkList.toArray());
            System.out.println(sql);
            return checks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
