package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lhz.dao.SysNewsMapper;
import org.lhz.entity.SysLogInfo;
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
                paramsList.add(content);
            }
            if (startTime !=null){
                sql+=" and createtime >= ?";
                paramsList.add(startTime);
            }
            if (endTime !=null){
                sql+=" and createtime <= ?";
                paramsList.add(endTime);
            }
            sql+=";";
            return  runner.query(sql, new BeanListHandler<SysNews>(SysNews.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
