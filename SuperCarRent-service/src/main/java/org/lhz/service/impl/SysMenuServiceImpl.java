package org.lhz.service.impl;

import org.lhz.dao.SysMenuMapper;
import org.lhz.dao.impl.SysMenuMapperImpl;
import org.lhz.entity.SysMenu;
import org.lhz.service.SysMenuService;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public class SysMenuServiceImpl implements SysMenuService {
    //注入dao层的对象
    private SysMenuMapper sysMenuMapper = new SysMenuMapperImpl();
    @Override
    public List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo) {
        return sysMenuMapper.findAllMenuList(sysMenuVo);
    }

    @Override
    public List<SysMenu> findMenuByUserId(Integer userid) {
        return sysMenuMapper.findMenuByUserId(userid);
    }
}
