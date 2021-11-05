package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.entity.BunsBusCar;
import org.lhz.dao.BunsCarMessageDao;
import org.lhz.vo.BunsBusCarVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BunsCarMessageDaoImpl implements BunsCarMessageDao {
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
    public List<BunsBusCar> findAll(BunsBusCarVo busCarVo) {
        String carnumber = busCarVo.getCarnumber();
        String cartype = busCarVo.getCartype();
        String color = busCarVo.getColor();
        String description = busCarVo.getDescription();
        Integer isrenting = busCarVo.getIsrenting();
        Integer page = busCarVo.getPage();
        Integer limit = busCarVo.getLimit();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> carList = new ArrayList<>();

        try {
            String sql="select * from bus_car where 1=1";
            if(carnumber!=null&&!"".equals(carnumber)){
                sql=sql+" and carnumber like ?";
                carList.add("%"+carnumber+"%");
            }
            if(cartype!=null&&!"".equals(cartype)){
                sql=sql+" and cartype like ?";
                carList.add("%"+cartype+"%");
            }
            if(color!=null&&!"".equals(color)){
                sql=sql+" and color like ?";
                carList.add("%"+color+"%");
            }
            if(description!=null&&!"".equals(description)){
                sql=sql+" and description like ?";
                carList.add("%"+description+"%");
            }
            if(isrenting!=null){
                sql=sql+" and isrenting = ?";
                carList.add(isrenting);
            }
            sql+=" limit "+(page-1)*limit+","+limit;
            List<BunsBusCar> cars = runner.query(sql, new BeanListHandler<BunsBusCar>(BunsBusCar.class),carList.toArray());
            for (BunsBusCar car:cars) {
                System.out.println(car.toString());
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addCar(BunsBusCar busCar) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sql="insert into bus_car(carnumber,cartype,color,price,rentprice,deposit,isrenting,description,carimg,createtime)value (?,?,?,?,?,?,?,?,?,?)";
        try {
            int line = runner.update(sql, busCar.getCarnumber(), busCar.getCartype(), busCar.getColor(), busCar.getPrice(), busCar.getRentprice(), busCar.getDeposit(), busCar.getIsrenting(), busCar.getDescription(), busCar.getCarimg(), busCar.getCreatetime());
            return line;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateCar(BunsBusCar busCar) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sql="update bus_car set cartype=?,color=?,price=?,rentprice=?,deposit=?,isrenting=?,description=?,carimg=?,createtime=? where carnumber=?";
        try {
            int line = runner.update(sql,busCar.getCartype(),busCar.getColor(),busCar.getPrice(),busCar.getRentprice(),busCar.getDeposit(),busCar.getIsrenting(),busCar.getDescription(), busCar.getCarimg(), busCar.getCreatetime(),busCar.getCarnumber());
            return line;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteCar(String carnumber) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        String sql="delete from bus_car where carnumber like ?";
        try {
            int update = runner.update(sql, carnumber);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteBatchCar(String[] ids) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <ids.length ; i++) {
        strings.add(ids[i]);
        }
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());

        try {
            String sql="delete from bus_car where carnumber in (";
            for (int i = 0; i < ids.length; i++) {
                if(i!= ids.length-1){
                    sql+="?,";
                }else{
                    sql+="?";
                }
            }
            sql+=")";
            int updates = runner.update(sql, strings.toArray());
            return updates;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}