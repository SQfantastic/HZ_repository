package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lhz.entity.SysLogInfo;
import org.lhz.vo.SysLogInfoVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLogInfoMapperImpl implements SysLogInfoMapper {
    /**
     * Infor: 查询所有的用户登录信息
     * @param sysLogInfoVo
     * @return : java.util.List<org.lhz.entity.SysLogInfo>
     * @author : LHZ
     * @date : 2021/11/1 20:10
     */
    @Override
    public List<SysLogInfo> findAllSysLoInfoList(SysLogInfoVo sysLogInfoVo) {
        String loginname = sysLogInfoVo.getLoginname();
        String loginip = sysLogInfoVo.getLoginip();
        Date startTime = sysLogInfoVo.getStartTime();
        Date endTime = sysLogInfoVo.getEndTime();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> paramsList = new ArrayList<>();
        try {
            String sql = "select * from sys_log_login where 1=1";
            if (loginname!=null && !"".equals(loginname)) {
                sql+=" and loginname like  ? ";
                paramsList.add("%"+loginname+"%");
            }
            if (loginip !=null&& !"".equals(loginip)){
                sql+=" and loginip like ?";
                paramsList.add(loginip);
            }
            if (startTime !=null){
                sql+=" and logintime >= ?";
                paramsList.add(startTime);
            }
            if (endTime !=null){
                sql+=" and logintime <= ?";
                paramsList.add(endTime);
            }
            sql+=";";
            return  runner.query(sql, new BeanListHandler<SysLogInfo>(SysLogInfo.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 获取用户登录后的信息，并将信息写入数据库中
     * @param sysLogInfoVo
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 23:49
     */
    @Override
    public int addLogInfo(SysLogInfoVo sysLogInfoVo) {
        String loginname = sysLogInfoVo.getLoginname();
        String loginip = sysLogInfoVo.getLoginip();
        Date logintime = sysLogInfoVo.getLogintime();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_log_login (loginname,loginip,logintime) values (?,?,?)";
            return runner.update(sql,loginname,loginip,logintime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 删除用户登录信息
     * @param id
     * @return : int
     * @author : LHZ
     * @date : 2021/11/2 0:08
     */
    @Override
    public int deleteLogInfo(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="delete from sys_log_login where id = ?";
            return runner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
