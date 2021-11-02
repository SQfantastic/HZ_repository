package org.lhz.service;

import org.lhz.entity.SysNews;
import org.lhz.vo.SysNewsVo;

import java.util.List;

public interface SysNewsService {
    List<SysNews> findAllNewsList(SysNewsVo sysNewsVo);
}
