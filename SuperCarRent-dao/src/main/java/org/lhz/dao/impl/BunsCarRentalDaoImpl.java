package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.lhz.dao.BunsCarRentalDao;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.entity.BunsBusRent;
import org.lhz.vo.BunsBusRentVo;
import utils.CalculateDate;
import utils.DruidUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BunsCarRentalDaoImpl implements BunsCarRentalDao {
    private QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

    //    根据身份证号查找客户，返回客户实体类
    @Override
    public BunsBusCustomer checkIdNum(BunsBusCustomer bunsBusCustomer) {
        String sql = "select * from bus_customer where identity=?";
        try {
            return runner.query(sql, new BeanHandler<BunsBusCustomer>(BunsBusCustomer.class), bunsBusCustomer.getIdentity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    出租单号查重
    @Override
    public BunsBusRent checkSingleNo(String single) {
        String sql = "select * from bus_rent where rentid like ?";
        try {
            return runner.query(sql, new BeanHandler<BunsBusRent>(BunsBusRent.class), single);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    添加出租单
    @Override
    public Integer addRentSingle(BunsBusRent bunsBusRent) {
        String sql = "insert into bus_rent values(?,?,?,?,?,?,?,?,?)";
        String str = "select rentprice from bus_car where carnumber = ?";
        try {
            int days = new CalculateDate().Calculate(bunsBusRent.getBegindate(), bunsBusRent.getReturndate());
            Double price = runner.query(str, new ScalarHandler<>(), bunsBusRent.getCarnumber());
            if (days>0){
                price = price*days;
                return runner.update(sql, bunsBusRent.getRentid(), price, bunsBusRent.getBegindate(), bunsBusRent.getReturndate(),
                        1, bunsBusRent.getIdentity(), bunsBusRent.getCarnumber(), bunsBusRent.getOpername(), bunsBusRent.getCreatetime());
            }else {
                return runner.update(sql, bunsBusRent.getRentid(), price, bunsBusRent.getBegindate(), bunsBusRent.getReturndate(),
                        1, bunsBusRent.getIdentity(), bunsBusRent.getCarnumber(), bunsBusRent.getOpername(), bunsBusRent.getCreatetime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    查询出租单条数
    @Override
    public Long total() {
        String sql1 = "select count(*) from bus_rentr";
        try {
            return runner.query(sql1,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    模糊搜索车辆信息
    @Override
    public List<BunsBusRent> checkRentSingle(BunsBusRentVo bunsBusRentVo) {
        String rentid = bunsBusRentVo.getRentid();
        String identity = bunsBusRentVo.getIdentity();
        String carnumber = bunsBusRentVo.getCarnumber();
        Date startTime = bunsBusRentVo.getStartTime();
        Date endTime = bunsBusRentVo.getEndTime();
        Integer rentflag = bunsBusRentVo.getRentflag();
        Integer page = bunsBusRentVo.getPage();
        Integer limit = bunsBusRentVo.getLimit();
        ArrayList<Object> paramsList = new ArrayList<>();
        try {
            String sql = "select * from bus_rent where 1=1";
            if (rentid != null && !"".equals(rentid)) {
                sql += " and rentid like  ? ";
                paramsList.add("%" + rentid + "%");
            }
            if (identity != null && !"".equals(identity)) {
                sql += " and identity = ?";
                paramsList.add(identity);
            }
            if (carnumber != null && !"".equals(carnumber)) {
                sql += " and carnumber like ?";
                paramsList.add(carnumber);
            }
            if (startTime != null) {
                sql += " and begindate >= ?";
                paramsList.add(startTime);
            }
            if (endTime != null) {
                sql += " and returndate <= ?";
                paramsList.add(endTime);
            }
            if (rentflag != null && !"".equals(rentflag)) {
                sql += " and rentflag = ?";
                paramsList.add(rentflag);
            }
            sql += " limit " + (page - 1) * limit + "," + limit;
            return runner.query(sql, new BeanListHandler<BunsBusRent>(BunsBusRent.class), paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    修改出租单数据
    @Override
    public Integer updateRentSingle(BunsBusRent bunsBusRent) throws ParseException {
        Date begindate = bunsBusRent.getBegindate();
        Date returndate = bunsBusRent.getReturndate();
        String rentid = bunsBusRent.getRentid();
        System.out.println(rentid);
        Double price = null;
        String carnumber = bunsBusRent.getCarnumber();
        String opername = bunsBusRent.getOpername();
        String str = "select rentprice from bus_car where carnumber = ?";
        String sql = "update bus_rent set begindate=?,returndate=?,price=?,opername=?,createtime=? where rentid=?";
        try {
            int days = new CalculateDate().Calculate(begindate, returndate);
            price = runner.query(str, new ScalarHandler<>(), carnumber);
            System.out.println(price);
            if (days>0){
                price = price*days;
                return runner.update(sql,begindate,returndate,price,opername, bunsBusRent.getCreatetime(),rentid);
            }else {
                return runner.update(sql,begindate,returndate,price,opername, bunsBusRent.getCreatetime(),rentid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    删除出租单
    @Override
    public Integer delRentSingle(String rentid) {
        String sql = "delete from bus_rent where rentid=?";
        try {
            return runner.update(sql, rentid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
