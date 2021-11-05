package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.CustomerMapper;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.vo.BunsBusCustomerVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerMapperImpl implements CustomerMapper {

    /**
     * Infor: 查询所有的用户信息
     * @param customerVo
     * @return : java.util.List<org.lhz.entity.Customer>
     * @author : LHZ
     * @date : 2021/11/3 11:59
     */
    @Override
    public List<BunsBusCustomer> findAllCustomerList(BunsBusCustomerVo customerVo) {
        //获取参数
        Integer limit = customerVo.getLimit();
        Integer page = customerVo.getPage();
        String custname = customerVo.getCustname();
        String identity = customerVo.getIdentity();
        String phone = customerVo.getPhone();
        String address = customerVo.getAddress();
        String career = customerVo.getCareer();
        Integer sex = (Integer)customerVo.getSex();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> paramsList = new ArrayList<>();
        try {
            String sql ="select * from bus_customer where 1=1";
            if (custname!=null && !"".equals(custname)) {
                sql+=" and custname like  ? ";
                paramsList.add("%"+custname+"%");
            }
            if (career!=null&&!"".equals(career)){
                sql+=" and career like ?";
                paramsList.add("%"+career+"%");
            }
            if (identity !=null&& !"".equals(identity)){
                sql+=" and identity like ?";
                paramsList.add("%"+identity+"%");
            }
            if (address !=null&& !"".equals(address)){
                sql+=" and address like ?";
                paramsList.add("%"+address+"%");
            }
            if (phone !=null&& !"".equals(phone)){
                sql+=" and phone like ?";
                paramsList.add("%"+phone+"%");
            }
            if (sex !=null){
                sql+=" and sex = ? ";
                paramsList.add(sex);
            }
            sql+=" limit "+(page-1)*limit+","+limit;
            return  runner.query(sql, new BeanListHandler<BunsBusCustomer>(BunsBusCustomer.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 增加客户信息
     * @param customer
     * @return : int
     * @author : LHZ
     * @date : 2021/11/3 19:40
     */
    @Override
    public int addCustomer(BunsBusCustomer customer) {
        Integer sex = customer.getSex();
        String custname = customer.getCustname();
        String address = customer.getAddress();
        String identity = customer.getIdentity();
        String career = customer.getCareer();
        String phone = customer.getPhone();
        Date createtime = customer.getCreatetime();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "insert into bus_customer values(?,?,?,?,?,?,?)";
            return runner.update(sql,identity,custname,sex,address,phone,career,createtime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Infor: 更新客户信息
     * @param customer
     * @return : int
     * @author : LHZ
     * @date : 2021/11/3 19:40
     */
    @Override
    public int updateCustomer(BunsBusCustomer customer) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update bus_customer set custname=?,career=?,phone=?," +
                    "address=?,sex=? where identity =?";
            return runner.update(sql,customer.getCustname(),customer.getCareer(),
                    customer.getPhone(),customer.getAddress(),customer.getSex(),
                    customer.getIdentity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 根据客户身份证号删除该用户
     * @param identity
     * @return : int
     * @author : LHZ
     * @date : 2021/11/3 19:41
     */
    @Override
    public int deleteCustomerById(Integer identity) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "delete from bus_customer where identity =?";
            return runner.update(sql,identity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 获取总数
     * @param
     * @return : java.lang.Long
     * @author : LHZ
     * @date : 2021/11/4 23:10
     */
    @Override
    public Long getTotal() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select count(*) from bus_customer";
            return runner.query(sql,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
