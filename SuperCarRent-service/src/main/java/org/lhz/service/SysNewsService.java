package org.lhz.service;

import org.lhz.entity.SysNews;
import org.lhz.vo.SysNewsVo;

import java.util.List;

public interface SysNewsService {
    List<SysNews> findAllNewsList(SysNewsVo sysNewsVo);

    SysNews findNewsById(Integer id);

    int deleteNewsById(Integer id);

    int addNews(SysNews sysNews);

    int updateNews(SysNews sysNews);

    Long getTotal();
}
