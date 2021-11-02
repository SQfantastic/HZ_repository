package org.lhz.dao;

import org.lhz.entity.SysNews;
import org.lhz.vo.SysNewsVo;

import java.util.List;

public interface SysNewsMapper {
    List<SysNews> findAllNewsList(SysNewsVo sysNewsVo);

}
