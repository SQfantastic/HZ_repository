package org.lhz.dao;

import org.lhz.entity.SysNews;
import org.lhz.vo.SysNewsVo;

import java.util.List;

public interface SysNewsMapper {
    List<SysNews> findAllNewsList(SysNewsVo sysNewsVo);

    SysNews findNewsById(Integer id);

    int deleteNewsById(Integer id);

    int addNews(SysNews sysNews);

    int updateNews(SysNews sysNews);

    Long getTotal();
}
