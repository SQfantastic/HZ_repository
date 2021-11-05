package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.SysNewsMapper;
import org.lhz.entity.SysNews;
import org.lhz.vo.SysNewsVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysNewsMapperImpl implements SysNewsMapper {

    /**
     * Infor: 查找所有的新闻信息，包括表头的模糊查询
     * @param sysNewsVo
     * @return : java.util.List<org.lhz.entity.SysNews>
     * @author : LHZ
     * @date : 2021/11/2 8:51
     */
    @Override
    public List<SysNews> findAllNewsList(SysNewsVo sysNewsVo) {
        Integer page = sysNewsVo.getPage();
        Integer limit = sysNewsVo.getLimit();
        Date endTime = sysNewsVo.getEndTime();
        Date startTime = sysNewsVo.getStartTime();
        String content = sysNewsVo.getContent();
        String title = sysNewsVo.getTitle();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> paramsList = new ArrayList<>();
        try {
            String sql = "select * from sys_news where 1=1";
            if (title!=null && !"".equals(title)) {
                sql+=" and title like  ? ";
                paramsList.add("%"+title+"%");
            }
            if (content !=null&& !"".equals(content)){
                sql+=" and content like ?";
                paramsList.add("%"+content+"%");
            }
            if (startTime !=null){
                sql+=" and createtime >= ?";
                paramsList.add(startTime);
            }
            if (endTime !=null){
                sql+=" and createtime <= ?";
                paramsList.add(endTime);
            }
            sql+=" limit "+(page-1)*limit+","+limit;
            return  runner.query(sql, new BeanListHandler<SysNews>(SysNews.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 根据新闻id查询到新闻信息
     * @param id
     * @return : org.lhz.entity.SysNews
     * @author : LHZ
     * @date : 2021/11/2 10:56
     */
    @Override
    public SysNews findNewsById(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="select * from sys_news where id=?";
            return runner.query(sql,new BeanHandler<SysNews>(SysNews.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 根据新闻的id删除该新闻
     * @param id
     * @return : int
     * @author : LHZ
     * @date : 2021/11/2 11:04
     */
    @Override
    public int deleteNewsById(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="delete from sys_news where id = ?";
            return runner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 添加新闻信息
     * @param sysNews
     * @return : int
     * @author : LHZ
     * @date : 2021/11/2 11:21
     */
    @Override
    public int addNews(SysNews sysNews) {
        //获取参数
        String content = sysNews.getContent();
        String title = sysNews.getTitle();
        Date createtime = sysNews.getCreatetime();
        String opername = sysNews.getOpername();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_news (content,title,opername,createtime) values (?,?,?,?)";
            return runner.update(sql,content,title,opername,createtime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int updateNews(SysNews sysNews) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_news set title=?,content=?  where id =?";
            return runner.update(sql,sysNews.getTitle(),sysNews.getContent(),sysNews.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Long getTotal() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="select count(*) from sys_news";
            return runner.query(sql,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
