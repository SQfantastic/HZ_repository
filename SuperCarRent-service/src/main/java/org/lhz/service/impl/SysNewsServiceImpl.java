package org.lhz.service.impl;

import org.lhz.dao.SysNewsMapper;
import org.lhz.dao.impl.SysNewsMapperImpl;
import org.lhz.entity.SysNews;
import org.lhz.service.SysNewsService;
import org.lhz.vo.SysNewsVo;

import java.util.List;

public class SysNewsServiceImpl implements SysNewsService {
    //注入dao层对象
    private SysNewsMapper sysNewsMapper = new SysNewsMapperImpl();
    @Override
    public List<SysNews> findAllNewsList(SysNewsVo sysNewsVo) {
        return sysNewsMapper.findAllNewsList(sysNewsVo);
    }

    @Override
    public SysNews findNewsById(Integer id) {
        return sysNewsMapper.findNewsById(id);
    }

    @Override
    public int deleteNewsById(Integer id) {
        return sysNewsMapper.deleteNewsById(id);
    }

    @Override
    public int addNews(SysNews sysNews) {
        return sysNewsMapper.addNews(sysNews);
    }

    @Override
    public int updateNews(SysNews sysNews) {
        return sysNewsMapper.updateNews(sysNews);
    }

    @Override
    public Long getTotal() {
        return sysNewsMapper.getTotal();
    }
}
